###url: 
- (...)
# Rest API for Garden Guard
-  this is the whole APIs for our applications, it includes APIs for authentication, fetching or storing data from or to Cloud SQL and Firestore, and also predict handlers.
-  Please read carefully the methods in the routes.js
-   Handlers folders contain all handlers files needed for Garden Guard.

## Install Requirements:
- npm init --y (to create package.json file)
- npm install / npm i

## How to deploy it?
1. You can deploy it with Cloud Run
2. Make sure to change all developing stage variables and replace them with production variables. For instance, replace localhost to 0.0.0.0/0 in index.js as the host.
3. Note all needed ENV Variables. Change the variable's values based on your needs.
4. Create a container image of the app using Docker.
5. Deploy it to GCP's Cloud Run.
6. Configure Cloud Run service by adding SQL Connection and all the ENV Variables for the app.
```bash
npm cache clean --force
```
```bash
rm -rf node_modules
npm install
```
```bash
sudo apt-get install build-essential
```

--------------------------------------------------------
## Methods:

### Register Accout
1. URL: /register
2. Method: POST
3. Request Body :
   - username as varchar
   - email as varchar
   - password as varchar, must be at least 8 characters, Uppercase, number and special characters
Response
```json
{
	“error” : false,
	“message” : “Account registered successfully”
}
```


--------------------------------------------------------------------------------
## LOGIN
1. URL: /login
2. Method: post
3. Request Body:
   - usernameORemail as String
   - password as String
4. Response
```json
{
	“error” : “false”,
	“message” : “Login Succeed”,
    	“token” : “randomgeneratenumbersandalphabet”
}
```


-------------------------------------------------------------------------------------
##CREATE NOTE
1. URL : /notes
2. Methods : post
3. Headers.authorization
   - authToken
4. Request body
   - image as file ,  pakai form data
   - title as string

5. Response
```json
{
    "error": false,
    "message": "Note Created!",
    "note": {
        "noteId": "id",
        "userId": "id",
        "title": "test dengan postman",
        "description": "iniadalahtextcobacoba",
        "imageUrl": "url",
        "updated": "2023-06-13T06:29:54.418Z"
    }
```

    
------------------------------------------------------------------------------------------------
### GetAllNotes
1. URL : /notes
2. Methods : get
3. Headers.authorization
   - authToken
4. Response berhasil:
```json
{
    "error": false,
    "message": "All Notes retrieved",
    "listnote": []
        {
            "noteId": "id",
            "userId": "id",
            "title": "cobacoba",
            "description": "iniadalahtextcobacoba",
            "imageUrl": "url",
            "updated": "2023-06-12 16:13:04.836"
        },
        {
            "noteId": "id",
            "userId": "id",
            "title": "cobacoba",
            "description": "iniadalahtextcobacoba",
            "imageUrl": "url",
            "updated": "2023-06-12 16:13:15.330"
        },
        {
            "noteId": "id",
            "userId": "id",
            "title": "cobacoba",
            "description": "iniadalahtextcobacoba",
            "imageUrl": "url",
            "updated": "2023-06-12 16:13:21.632"
        }
    ]
}
```
-----------------------------------------------------------------------------------------------------------
## GetNotesbyId
1. URL : /notes/:noteId
2. Methods : get
3. Request params
   - noteId
4. Request headers authorization
   - authToken
5. Response
```json
{
    "error": false,
    "message": "Note retrieved",
    "note": {
        "noteId": "feaf01db-e644-4038-be94-ed8a77efcf8a",
        "userId": "1",
        "title": "cobacoba",
        "description": "iniadalahtextcobacoba",
        "imageUrl": "url",
        "updated": "2023-06-12 16:13:04.836"
    }
}
```
-----------------------------------------------------------------------------------------------------
### EditNote
1. URL : /notes/edit/:noteId
2. Method : post
3. Request
   - const { noteId } = req.params
   - const { title, description } = req.body
   - const authToken = req.headers.authorization
4. Response
```json
{
    "error": false,
    "message": "Note updated!",
    "updatedNote": {
        "noteId": "id",
        "userId": "id",
        "title": "sudahdiubah",
        "description": "ini adalah catatan yang sudah diubah",
        "imageUrl": "url",
        "updated": "2023-06-13T12:30:44.042Z"
    }
}
```
------------------------------------------------------------------------------------

### DELETE NOTE
1. URL : /notes/delete/:noteId
2. Method : delete
3. Request:
   - const { noteId } = req.params;
   - const authToken = req.headers.authorization;
4. Response
```json
{
    "success": true,
    "message": "Note deleted successfully."
}
```




