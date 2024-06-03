const { Model } = require('../services/models');

const findModel = (plant, myModels) => {
    return myModels.find((model) => model.name.toLowerCase() === plant.trim().toLowerCase());
};

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

    const result = await Model.predictImage(imgBuffer, model, plant, labels);

    //narik data suggestion and solution dri fireestore:
    //

    return h.response(result);
};

const rootHandler = (request, h) => {
    return 'Hello, I\'m Zain!';
};

module.exports = {
    predictHandler,
    rootHandler,
};
