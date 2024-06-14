require('dotenv').config();
const Hapi = require('@hapi/hapi');
const HapiAuthCookie = require('@hapi/cookie');
const db = require('./services/db');
const routes = require('./routes/routes');
const { SecretManagerServiceClient } = require('@google-cloud/secret-manager');

// Function to get secret value from Google Cloud Secret Manager
async function getSecretValue(secretName) {
    const client = new SecretManagerServiceClient();
    const [version] = await client.accessSecretVersion({
        name: secretName,
    });
    const secretPayload = version.payload.data.toString('utf8');
    return JSON.parse(secretPayload);
}

const init = async () => {
    try {
        const secretName = process.env.GCLOUD_SECRET_NAME;
        const myModels = await getSecretValue(secretName);

        const server = Hapi.server({
            port: 8080,
            host: '0.0.0.0',
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
                isSecure: process.env.NODE_ENV === 'production', // Set to true in production
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
    } catch (error) {
        console.error('Failed to initialize server:', error);
        process.exit(1);
    }
};

process.on('unhandledRejection', (err) => {
    console.log(err);
    process.exit(1);
});

init();
