# User CRUD operations

User may look like this
```json
{
  "id": 1,
  "email": "taras@shevchenko.ua",
  "password": "$2a$10$eyVIuZiP9ahPEzfcpQyMC.ka9DquNcpL/iaxkh7Sm6532G8p7j/tS",
  "userRole": "INDIVIDUAL",
  "firstName": "Taras",
  "lastName": "Shevchenko",
  "profilePicture": {
    "id": 61,
    "filename": "ea96cfde-d74c-4e7b-b277-7e6ca6389734.jpg",
    "metadata": {
      "id": 31,
      "width": 4000,
      "height": 5288,
      "extension": "jpg",
      "size": 834772.0
    }
  },
  "contactInfo": null,
  "favorites": null,
  "createdAt": "2024-07-26T16:29:14.702335287",
  "updatedAt": "2024-07-18T11:39:24.078458"
}
```

## 1. Get a User by id
GET to `/api/v1/user/{id}` id is of type Long (e.g. 1, 2, 3, ....)
request body - empty. Response will be as in example above.

## 2. Get All users
GET to `/api/v1/user/all` for now will return Page with all existing Users
without any filtering. Request body - empty.

**response**
```json
{
  "content": [
    {
      "id": 1,
      "email": "taras@shevchenko.ua",
      "password": "$2a$10$eyVIuZiP9ahPEzfcpQyMC.ka9DquNcpL/iaxkh7Sm6532G8p7j/tS",
      "userRole": "INDIVIDUAL",
      "firstName": "Taras",
      "lastName": null,
      "profilePicture": null,
      "contactInfo": null,
      "favorites": [],
      "createdAt": "2024-07-26T16:29:14.702335",
      "updatedAt": null
    }
  ],
  "page": {
    "size": 1,
    "number": 0,
    "totalElements": 1,
    "totalPages": 1
  }
}
```

## 3. Create new User
POST to `/api/v1/user` with request body.
Request params - `Raw`
```json
{
"email": "taras@shevchenko.ua",
"password": "password",
"userRole": "INDIVIDUAL",
"firstName": "Taras"
}
```

**response**
```json
{
  "id": 1,
  "email": "taras@shevchenko.ua",
  "password": "$2a$10$eyVIuZiP9ahPEzfcpQyMC.ka9DquNcpL/iaxkh7Sm6532G8p7j/tS",
  "userRole": "INDIVIDUAL",
  "firstName": "Taras",
  "lastName": null,
  "profilePicture": null,
  "contactInfo": null,
  "favorites": null,
  "createdAt": "2024-07-26T16:29:14.702335287",
  "updatedAt": null
}
```

## 4. Delete User by id
DELETE to `/api/v1/user/{id}` should delete a User

**response**
Status 200 OK

## 5. Update Ad fields
PUT to `/api/v1/user/` with request body
Request params - `Raw`
```json
{
  "id":"1",
  "email":"taras@shevchenko.ua",
  "password":"password",
  "userRole":"INDIVIDUAL",
  "firstName":"Taras",
  "lastName":"Shevchenko"
}
```
**response**
```json
{
  "id": 1,
  "email": "taras@shevchenko.ua",
  "password": "$2a$10$eyVIuZiP9ahPEzfcpQyMC.ka9DquNcpL/iaxkh7Sm6532G8p7j/tS",
  "userRole": "INDIVIDUAL",
  "firstName": "Taras",
  "lastName": "Shevchenko",
  "profilePicture": {
    "id": 61,
    "filename": "ea96cfde-d74c-4e7b-b277-7e6ca6389734.jpg",
    "metadata": {
      "id": 31,
      "width": 4000,
      "height": 5288,
      "extension": "jpg",
      "size": 834772.0
    }
  },
  "contactInfo": null,
  "favorites": null,
  "createdAt": "2024-07-26T16:29:14.702335287",
  "updatedAt": "2024-07-18T11:39:24.078458"
}
```