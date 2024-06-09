const { predictHandler } = require('../handlers/modelHandler');
const { signin, signup, signout, getUserProfile } = require('../handlers/authHandler');
const { getHistory, getHistoryDetailsId } = require('../handlers/fetchHistoryHandler');

const routes = (server, myModels) => {
    server.route({
        method: 'GET',
        path: '/home',
        handler: () => {
            return 'Hello';
        },
        options: {
            auth: false
        }
    });

    // SignIn and SignUp Routes:
    server.route({
        method: 'POST',
        path: '/signin',
        handler: signin,
        options: {
            auth: false
        }
    });

    server.route({
        method: 'POST',
        path: '/signup',
        handler: signup,
        options: {
            auth: false
        }
    });

    server.route({
        method: 'POST',
        path:'/signout',
        handler: signout,
    });

    server.route({
        method: 'GET',
        path:'/profile',
        handler: getUserProfile,
        options: {
            auth: 'session'  // Require authentication for this route
        }
    });

    // Fetching History Data from Firestore Route:
    server.route({
        method: 'GET',
        path: '/history',
        handler: getHistory,
        options: {
            auth: 'session'
        }
    });

    server.route({
        method: 'GET',
        path: '/history/{id}',
        handler: getHistoryDetailsId,
        options: {
            auth: 'session'
        }
    });

    // Define route for prediction
    server.route({
        method: 'POST',
        path: '/predict/{plant}',
        handler: (request, h) => predictHandler(request, h, myModels),
        options: {
            auth: 'session', // Ensure authentication
            payload: {
                output: 'stream',
                parse: true,
                allow: 'multipart/form-data',
                maxBytes: 5 * 1024 * 1024, // 5 MB
                multipart: true,
            },
        },
    });
};

module.exports = routes;
