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

### Register User Account (Sign Up)
1. URL: /signup
2. Method: POST
3. Request Body :
   - email (string, required): The email address of the user.
   - username (string, required): The username for the new account.
   - password (string, required): The password for the new account.
4. Response
```json
{
    "type": "object",
    "properties": {
        "status": {
            "type": "string"
        },
        "message": {
            "type": "string"
        }
    }
}
```

--------------------------------------------------------------------------------
## LOGIN (Sign In)
1. URL: /signin
2. Method: POST
3. Request Body:
   - email (string, required): The username for signing in.
   - password (string, required): The password for signing in.
4. Response
```json
{
    "type": "object",
    "properties": {
        "status": {
            "type": "string"
        },
        "message": {
            "type": "string"
        },
	{
	"token": {
            "type": "string"
	}
    }
}
```


-------------------------------------------------------------------------------------
## LOGOUT (Sign Out)
1. URL : /signout
2. Methods : POST
3. Request Headers: Authorization - jwtToken
4. Response
```json
{
    "type": "object",
    "properties": {
        "status": {
            "type": "string"
        },
        "message": {
            "type": "string"
        }
    }
}

```

------------------------------------------------------------------------------------------------
### Get Profile
This endpoint retrieves the profile information for a user.
1. URL : /profile
2. Methods : GET
3. Request Headers: Authorization - jwtToken
4. Response
```json
{
    "type": "object",
    "properties": {
        "status": {
            "type": "string"
        },
        "user": {
            "type": "object",
            "properties": {
                "id": {
                    "type": "string"
                },
                "username": {
                    "type": "string"
                },
                "email": {
                    "type": "string"
                },
                "password": {
                    "type": "string"
                }
            }
        }
    }
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




