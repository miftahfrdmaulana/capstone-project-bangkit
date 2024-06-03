const Hapi = require('@hapi/hapi');
const routes = require('./routes/routes');
const fs = require('fs');
const path = require('path');

// Load models from services/models.json
const myModelsPath = path.resolve(__dirname, 'services/models.json');
const myModels = JSON.parse(fs.readFileSync(myModelsPath, 'utf-8'));

const init = async () => {
    const server = Hapi.server({
        port: 3000,
        host: 'localhost',
        routes: {
            cors: {
                origin: ['*'],
            },
        },
    });

    // Register routes
    routes(server, myModels);

    await server.start();
    console.log('Server running on %s', server.info.uri);
};

process.on('unhandledRejection', (err) => {
    console.log(err);
    process.exit(1);
});

init();
