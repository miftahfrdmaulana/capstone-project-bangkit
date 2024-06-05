const Firestore = require('@google-cloud/firestore');

const db = new Firestore({
    projectId: 'testing-capstone-2',
    keyFilename: 'src/services/keyfile.json',
  });

//get data from firestore
async function getdata(pathto, h, confidence) {
    try {
      const corpsRef = db.collection('crops-predict').doc(pathto);
      const doc = await corpsRef.get();
      if (!doc.exists) {
        return h.response({ message: 'No such document!' }).code(404);
      } else {
        const data = doc.data();
        data.confidence = confidence; 
        return h.response(data).code(200);
      }
    } catch (error) {
      console.error('Error getting document:', error);
      return h.response({ message: 'Error getting document' }).code(500);
    }
  };
  

  
    module.exports = getdata;