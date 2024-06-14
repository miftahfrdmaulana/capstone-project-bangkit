const dbConfig = {
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME,
    socketPath: process.env.INSTANCE_UNIX_SOCKET
};

// const dbConfig = {
//     host: '127.0.0.1',
//     user: 'root',
//     password: '',
//     database: 'auth_db'
// };
module.exports = { dbConfig }