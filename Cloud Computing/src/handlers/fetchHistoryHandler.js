const { Firestore } = require('@google-cloud/firestore');

async function getHistory(req, h) {
    const userId = req.auth.credentials.user.id;
    const db = new Firestore();

    try {
        const pathToGetHistory = `users/${userId}/history/`;
        const histCollection = await db.collection(pathToGetHistory)
                                      .orderBy('createdAt', 'desc')
                                      .get();
        const historyData = histCollection.docs.map(doc => {
            const data = doc.data();
            return {
                id: doc.id,
                namaIlmiahPenyakit: data["nama ilmiah penyakit"],
                createdAt: data.createdAt,
                tanaman: data.tanaman
            };
        });

        if (historyData.length === 0) {
            return h.response('No file').code(404);
        } else {
            return h.response(historyData).code(200);
        }   

    } catch (error) {
        console.error('Error getting history data:', error);
        return h.response('Error getting document').code(500);
    }
}

// Handler to get specific history detail for the logged-in user
async function getHistoryDetailsId(req, h) {
    const userId = req.auth.credentials.user.id; // Get userId from authenticated session
    const db = new Firestore();
    const idParam = req.params.id;
    const pathToGetHistoryId = `users/${userId}/history/${idParam}`;

    try {
        const docRef = db.doc(pathToGetHistoryId);
        const doc = await docRef.get();

        if (!doc.exists) {
            return h.response('Document not found').code(404);
        } else {
            return h.response(doc.data()).code(200);
        }
    } catch (error) {
        console.error('Error getting history document:', error);
        return h.response('Error getting document').code(500);
    }
}

module.exports = { getHistory, getHistoryDetailsId };
