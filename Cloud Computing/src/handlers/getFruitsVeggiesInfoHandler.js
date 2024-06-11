const { Firestore } = require('@google-cloud/firestore');

async function getInfoBuah(req, res) {
    const {item, penyakit} = req.params;
    const db = new Firestore({
        projectId: 'testing-capstone-2',
    });

    try {
        const dirPath = db.collection('Crops').doc('Buah').collection(item).doc(penyakit);
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
    const {item, penyakit} = req.params;
    const db = new Firestore({
        projectId: 'testing-capstone-2',
    });

    try {
        const dirPath = db.collection('Crops').doc('Sayur').collection(item).doc(penyakit);
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