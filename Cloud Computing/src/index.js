require('dotenv').config();
const Hapi = require('@hapi/hapi');
const HapiAuthCookie = require('@hapi/cookie');
const db = require('./services/db');
const routes = require('./routes/routes');
const fs = require('fs');
const path = require('path');
const jwt = require('jsonwebtoken');

// Load models from services/models.json
const myModelsPath = path.resolve(__dirname, 'services/models.json');
const myModels = JSON.parse(fs.readFileSync(myModelsPath, 'utf-8'));

const validateUser = async (artifacts, request, h) => {
    try {
        const results = await db.query('SELECT * FROM users WHERE id = ?', [artifacts.decoded.payload.id]);
        const user = results[0];
        if (!user) {
            return { isValid: false };
        }
        return { isValid: true, credentials: { user } };
    } catch (error) {
        return { isValid: false };
    }
};

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

    await server.register(require('@hapi/jwt'));

    server.auth.strategy('jwt', 'jwt', {
        keys: process.env.JWT_SECRET,
        verify: {
            aud: false,
            iss: false,
            sub: false,
            nbf: true,
            exp: true,
            maxAgeSec: 14400, // optional
            timeSkewSec: 15 // optional
        },
        validate: validateUser
    });

    server.auth.default('jwt');

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
