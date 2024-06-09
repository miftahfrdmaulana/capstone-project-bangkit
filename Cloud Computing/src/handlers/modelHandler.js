const { Model } = require('../services/models');
const { getdata, uploadData } = require('../services/frs');
const crypto = require('crypto');

// Function to find model
const findModel = (plant, myModels) => {
  return myModels.find((model) => model.name.toLowerCase() === plant.trim().toLowerCase());
};

// Define the handler for the predict route
const predictHandler = async (request, h, myModels) => {
  const { plant } = request.params;
  const { image } = request.payload;

  const myModel = findModel(plant, myModels);
  if (!myModel) {
    return h.response({ message: 'Invalid plant' }).code(400);
  }

  if (!image) {
    return h.response({ message: 'Missing image' }).code(400);
  }

  if (typeof image !== 'object' || !image.hapi || !image.hapi.headers['content-type'].startsWith('image')) {
    return h.response({ message: 'Invalid image type' }).code(400);
  }
  if (image._data.length > 5 * 1024 * 1024) {
    return h.response({ message: 'Image size too large (Max 5MB)' }).code(400);
  }

  const { model, labels } = myModel;
  const imgBuffer = image._data;
  const id = crypto.randomUUID();
  const createdAt = new Date().toISOString();

  const result = await Model.predictImage(imgBuffer, model, plant, labels);

  // Log the result to debug
  console.log('Prediction result:', result);

  // Ensure that result.label is valid and one of the labels for the plant
  if (!result.label || !labels.includes(result.label)) {
    console.error('Prediction label is undefined or not valid');
    return h.response({ message: 'Prediction label is undefined or not valid' }).code(500);
  }

  const userId = request.auth.credentials.user.id;
  const pathToStore = `users/${userId}/history/${id}`;
  let pathto;
  if (['apple', 'mango', 'grape'].includes(plant.toLowerCase())) {
    pathto = `buah/${plant}/${result.label}`;
  } else {
    pathto = `sayur/${plant}/${result.label}`;
  }
  console.log('Firestore path:', pathto);

  // Retrieve data from Firestore
  const dataResponse = await getdata(pathto, h, result.confidence, id, createdAt);

  // Extract the data from the response to store it
  const responseData = dataResponse.source;

  // Upload the prediction result to Firestore
  await uploadData(pathToStore, responseData);

  return dataResponse;
};

module.exports = {
  predictHandler,
};
