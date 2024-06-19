const { Firestore } = require('@google-cloud/firestore');

async function getInfoBuah(req, res) {
    const { item, penyakit } = req.params;
    const db = new Firestore();

    try {
        const decodedPenyakit = decodeURIComponent(penyakit);
        const dirPath = db.collection('crops-predict').doc(`buah/${item}/${decodedPenyakit}`);
        
        // Log the Firestore path for debugging
        console.log(`Fetching document from path: crops-predict/buah/${item}/${decodedPenyakit}`);
        
        const dirData = await dirPath.get();

        if (!dirData.exists) {
            return res.response('Document not found').code(404);
        } else {
            return res.response(dirData.data()).code(200);
        }
    } catch (error) {
        console.error('Error getting history document:', error);
        return res.response('Error getting document').code(500);
    }
}

async function getInfoSayur(req, res) {
    const { item, penyakit } = req.params;
    const db = new Firestore();

    try {
        const decodedPenyakit = decodeURIComponent(penyakit);
        const dirPath = db.collection('crops-predict').doc(`sayur/${item}/${decodedPenyakit}`);
        
        // Log the Firestore path for debugging
        console.log(`Fetching document from path: crops-predict/sayur/${item}/${decodedPenyakit}`);
        
        const dirData = await dirPath.get();

        if (!dirData.exists) {
            return res.response('Document not found').code(404);
        } else {
            return res.response(dirData.data()).code(200);
        }
    } catch (error) {
        console.error('Error getting history document:', error);
        return res.response('Error getting document').code(500);
    }
}

module.exports = { getInfoBuah, getInfoSayur };