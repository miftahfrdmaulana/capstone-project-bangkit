const { predictHandler, rootHandler } = require('../handlers/modelHandler');

const routes = (server, myModels) => {
    // Define route for prediction
    server.route({
        method: 'POST',
        path: '/predict/{plant}',
        handler: (request, h) => predictHandler(request, h, myModels),
        options: {
            payload: {
                output: 'stream',
                parse: true,
                allow: 'multipart/form-data',
                maxBytes: 5 * 1024 * 1024, // 5 MB
                multipart: true,
            },
        },
    });

    // Define a simple GET route
    server.route({
        method: 'GET',
        path: '/',
        handler: rootHandler,
    });

    
};

module.exports = routes;
