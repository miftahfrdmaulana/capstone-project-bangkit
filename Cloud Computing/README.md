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
## Predict
1. URL : /predict/{plant}
2. Methods : POST
3. Request params
   - image (file): The image of the plant to be analyzed.
4. Request Headers: Authorization - jwtToken
5. Response
```json
{
  "type": "object",
  "properties": {
    "pengobatan": {
      "type": "string"
    },
    "penyakit": {
      "type": "string"
    },
    "nama ilmiah penyakit": {
      "type": "string"
    },
    "tanaman": {
      "type": "string"
    },
    "confidence": {
      "type": "string"
    },
    "id": {
      "type": "string"
    },
    "createdAt": {
      "type": "string"
    }
  }
}
```
-----------------------------------------------------------------------------------------------------
### Get Disease Info
1. URL : /home/{plant}/${types}/${disease}
2. Method : GET
3. Request Headers: Authorization - jwtToken
4. Response
```json
{
    "type": "object",
    "properties": {
        "pengobatan": {
            "type": "string"
        },
        "penyakit": {
            "type": "string"
        },
        "nama ilmiah penyakit": {
            "type": "string"
        },
        "tanaman": {
            "type": "string"
        }
    }
}
```
------------------------------------------------------------------------------------

### DELETE NOTE
1. URL : /history
2. Method : GET
3. Request Headers: Authorization - jwtToken
4. Response
  * Status: 200
  * Content-Type: application/json





