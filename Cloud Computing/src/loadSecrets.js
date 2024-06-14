const { SecretManagerServiceClient } = require('@google-cloud/secret-manager');

// Function to get secret value from Google Cloud Secret Manager
async function getSecretValue(secretName) {
    const client = new SecretManagerServiceClient();
    const [version] = await client.accessSecretVersion({
        name: secretName,
    });
    return version.payload.data.toString('utf8');
}

// Load secrets and set environment variables
async function loadSecrets() {
    const secretNames = [
        'projects/970790275360/secrets/COOKIE_SECRET/versions/1',
        'projects/970790275360/secrets/JWT_SECRET/versions/latest',
        'projects/970790275360/secrets/DB_HOST/versions/latest',
        'projects/970790275360/secrets/DB_USER/versions/latest',
        'projects/970790275360/secrets/DB_PASS/versions/latest',
        'projects/970790275360/secrets/DB_NAME/versions/latest',
        'projects/970790275360/secrets/DB_PASSWORD/versions/latest'
    ];
    const secrets = await Promise.all(secretNames.map(secretName => getSecretValue(secretName)));

    process.env.COOKIE_SECRET = secrets[0];
    process.env.JWT_SECRET = secrets[1];
    process.env.DB_HOST = secrets[2];
    process.env.DB_USER = secrets[3];
    process.env.DB_PASS = secrets[4];
    process.env.DB_NAME = secrets[5];
    process.env.DB_PASSWORD = secrets[6];
}

module.exports = loadSecrets;
