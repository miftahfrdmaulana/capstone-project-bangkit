require('dotenv').config();
const Hapi = require('@hapi/hapi');
const HapiAuthCookie = require('@hapi/cookie');
const db = require('./services/db');
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

    await server.register(HapiAuthCookie);

    server.auth.strategy('session', 'cookie', {
        cookie: {
            name: 'sid',
            password: process.env.COOKIE_SECRET, // Ensure this is at least 32 characters long
            isSecure: false, // Should be set to true in production
            path: '/',
        },
        redirectTo: false,
        validate: async (request, session) => {
            try {
                const results = await db.query('SELECT * FROM users WHERE id = ?', [session.id]);
                const user = results[0];
                if (!user) {
                    return { isValid: false };
                }
                return { isValid: true, credentials: { user } };
            } catch (error) {
                return { isValid: false };
            }
        }
    });

    server.auth.default('session');

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
