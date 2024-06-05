const Firestore = require('@google-cloud/firestore');

const db = new Firestore({
  projectId: 'testing-capstone-2',
  keyFilename: 'src/services/keyfile.json',
});

// Get data from Firestore
async function getdata(pathto, h, confidence, id, createdAt) {
  try {
    const corpsRef = db.collection('crops-predict').doc(pathto);
    const doc = await corpsRef.get();
    if (!doc.exists) {
      return h.response({ message: 'No such document!' }).code(404);
    } else {
      const data = doc.data();
      data.confidence = confidence; 
      data.id = id;
      data.createdAt = createdAt;
      return h.response(data).code(200);
    }
  } catch (error) {
    console.error('Error getting document:', error);
    return h.response({ message: 'Error getting document' }).code(500);
  }
};

// Upload data to Firestore
async function uploadData(pathToStore, data) {
  try {
    const docRef = db.doc(pathToStore);
    await docRef.set(data);
    console.log('Document written');
  } catch (error) {
    console.error('Error adding document: ', error);
  }
}

module.exports = { getdata, uploadData };
