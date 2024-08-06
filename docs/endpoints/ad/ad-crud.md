# Ad CRUD operations

Ad (advertisment) may look like this
```json
{
    "id": 10,
    "authorId": 3,
    "title": "Updated title",
    "description": "new description",
    "price": 3.30,
    "photos": [
        {
            "id": 64,
            "filename": "f9db33a0-5b5e-40a9-b9b5-72127f82a116.jpg",
            "metadata": {
                "id": 34,
                "width": 4032,
                "height": 3024,
                "extension": "jpg",
                "size": 1703382.0
            }
        },
        {
            "id": 65,
            "filename": "5f82d5b6-9e8f-47a1-9832-ef0049c44ff9.jpg",
            "metadata": {
                "id": 35,
                "width": 4032,
                "height": 3024,
                "extension": "jpg",
                "size": 1607209.0
            }
        }
    ],
    "thumbnail": {
        "id": 64,
        "filename": "f9db33a0-5b5e-40a9-b9b5-72127f82a116.jpg",
        "metadata": {
            "id": 34,
            "width": 4032,
            "height": 3024,
            "extension": "jpg",
            "size": 1703382.0
        }
    },
    "categoryId": 2,
    "createdAt": "2024-07-18T11:06:32.558497",
    "updatedAt": "2024-07-18T11:39:24.078458"
}
```

## 1. Get one Ad by id
GET to `/api/v1/ad/{adId}` adId is Long type (e.g. 1, 2, 3, ....)
request body - empty. Response will be as in example above.

## 2. Get All Ads
GET to `/api/v1/ad` for now will return Page with all existing Ads
without any filtering. Request body - empty.

**response**
```json
{
    "content": [
        {
            "id": 10,
            "authorId": 3,
            "title": "Updated title",
            "description": "new description",
            "price": 3.30,
            "photos": [
                {
                    "id": 64,
                    "filename": "f9db33a0-5b5e-40a9-b9b5-72127f82a116.jpg",
                    "metadata": {
                        "id": 34,
                        "width": 4032,
                        "height": 3024,
                        "extension": "jpg",
                        "size": 1703382.0
                    }
                },
                {
                    "id": 65,
                    "filename": "5f82d5b6-9e8f-47a1-9832-ef0049c44ff9.jpg",
                    "metadata": {
                        "id": 35,
                        "width": 4032,
                        "height": 3024,
                        "extension": "jpg",
                        "size": 1607209.0
                    }
                }
            ],
            "thumbnail": {
                "id": 64,
                "filename": "f9db33a0-5b5e-40a9-b9b5-72127f82a116.jpg",
                "metadata": {
                    "id": 34,
                    "width": 4032,
                    "height": 3024,
                    "extension": "jpg",
                    "size": 1703382.0
                }
            },
            "categoryId": 2,
            "createdAt": "2024-07-18T11:06:32.558497",
            "updatedAt": "2024-07-18T11:39:24.078458"
        },
        {
            "id": 2,
            "authorId": 2,
            "title": "Adorable cat needing a loving home",
            "description": "Charming cat seeks a cozy place to live. Affectionate and well-behaved.",
            "price": 200.00,
            "photos": [
                {
                    "id": 53,
                    "filename": "c6a0b060-fb43-4fef-8dbe-c6bc17c6f60a.jpg",
                    "metadata": {
                        "id": 23,
                        "width": 606,
                        "height": 606,
                        "extension": "jpg",
                        "size": 54780.0
                    }
                },
                {
                    "id": 54,
                    "filename": "61c5f804-ce54-4d2a-8a53-3f5d6984e64a.jpg",
                    "metadata": {
                        "id": 24,
                        "width": 746,
                        "height": 746,
                        "extension": "jpg",
                        "size": 120636.0
                    }
                }
            ],
            "thumbnail": null,
            "categoryId": 2,
            "createdAt": "2024-06-23T20:26:08.627536",
            "updatedAt": "2024-06-23T20:26:08.627536"
        },
        {
            "id": 4,
            "authorId": 4,
            "title": "Lovely bird looking for a home",
            "description": "Colorful bird looking for a home. Chirpy and cheerful.",
            "price": 400.00,
            "photos": [],
            "thumbnail": null,
            "categoryId": 6,
            "createdAt": "2024-06-23T20:26:08.627536",
            "updatedAt": "2024-06-23T20:26:08.627536"
        },
        {
            "id": 5,
            "authorId": 5,
            "title": "Charming fish needing a new tank",
            "description": "Beautiful fish ready for a new home. Low maintenance and vibrant.",
            "price": 500.00,
            "photos": [],
            "thumbnail": null,
            "categoryId": 7,
            "createdAt": "2024-06-23T20:26:08.627536",
            "updatedAt": "2024-06-23T20:26:08.627536"
        },
        {
            "id": 3,
            "authorId": 3,
            "title": "Friendly rabbit available for adoption",
            "description": "Cute rabbit ready for adoption. Gentle and easy to care for.",
            "price": 300.00,
            "photos": [],
            "thumbnail": null,
            "categoryId": 3,
            "createdAt": "2024-06-23T20:26:08.627536",
            "updatedAt": "2024-06-23T20:26:08.627536"
        },
        {
            "id": 1,
            "authorId": 1,
            "title": "Beautiful dog searching a kind family",
            "description": "This lovely dog is looking for a caring home. Friendly and playful.",
            "price": 100.00,
            "photos": [
                {
                    "id": 50,
                    "filename": "b1c85b65-7ec9-4061-80b4-7bb320438920.jpg",
                    "metadata": {
                        "id": 20,
                        "width": 606,
                        "height": 606,
                        "extension": "jpg",
                        "size": 54780.0
                    }
                },
                {
                    "id": 51,
                    "filename": "25321e6b-66e5-4077-b82d-17cdf307b580.jpg",
                    "metadata": {
                        "id": 21,
                        "width": 746,
                        "height": 746,
                        "extension": "jpg",
                        "size": 120636.0
                    }
                }
            ],
            "thumbnail": {
                "id": 50,
                "filename": "b1c85b65-7ec9-4061-80b4-7bb320438920.jpg",
                "metadata": {
                    "id": 20,
                    "width": 606,
                    "height": 606,
                    "extension": "jpg",
                    "size": 54780.0
                }
            },
            "categoryId": 1,
            "createdAt": "2024-06-23T20:26:08.627536",
            "updatedAt": "2024-06-23T20:26:08.627536"
        }
    ],
    "page": {
        "size": 10,
        "number": 0,
        "totalElements": 6,
        "totalPages": 1
    }
}
```

## 3. Create new Ad
POST to `/api/v1/ad`
Request params - `form-data`
`authorId:2↵
title:New ad about dog
description:Bear is a mixed breed puppy who will be ready for adoption July 25th. 
price:0.0↵
categoryId:1
photoFiles:{insert file}
photoFiles:{insert file}
`
**response**
```json
{
    "id": 12,
    "authorId": 2,
    "title": "New ad about dog",
    "description": "Bear is a mixed breed puppy who will be ready for adoption July 25th.",
    "price": 0.0,
    "photos": [
        {
            "id": 68,
            "filename": "88964aa8-0381-42f1-822d-6da91aeb651e.jpg",
            "metadata": {
                "id": 38,
                "width": 4032,
                "height": 3024,
                "extension": "jpg",
                "size": 1703382.0
            }
        },
        {
            "id": 69,
            "filename": "77d95777-f012-4f54-8190-5556ff3e5711.jpg",
            "metadata": {
                "id": 39,
                "width": 4032,
                "height": 3024,
                "extension": "jpg",
                "size": 1607209.0
            }
        }
    ],
    "thumbnail": {
        "id": 68,
        "filename": "88964aa8-0381-42f1-822d-6da91aeb651e.jpg",
        "metadata": {
            "id": 38,
            "width": 4032,
            "height": 3024,
            "extension": "jpg",
            "size": 1703382.0
        }
    },
    "categoryId": 1,
    "createdAt": "2024-07-19T09:44:44.712706",
    "updatedAt": "2024-07-19T09:44:44.712706"
}
```

## 4. Delete Ad by id
DELETE to `/api/v1/ad/{adId}` should delete ad and related photos

**response**
Status 200 OK

## 5. Update Ad fields
PUT to `/api/v1/ad/{adId}` with request body
```json
{
    "authorId": 3,
    "title": "Updated title",
    "description": "new description",
    "price": 3.3,
    "categoryId": 2
}
```
**response**
```json
{
    "id": 2,
    "authorId": 3,
    "title": "Updated title",
    "description": "new description",
    "price": 3.3,
    "photos": [
        {
            "id": 53,
            "filename": "c6a0b060-fb43-4fef-8dbe-c6bc17c6f60a.jpg",
            "metadata": {
                "id": 23,
                "width": 606,
                "height": 606,
                "extension": "jpg",
                "size": 54780.0
            }
        },
        {
            "id": 54,
            "filename": "61c5f804-ce54-4d2a-8a53-3f5d6984e64a.jpg",
            "metadata": {
                "id": 24,
                "width": 746,
                "height": 746,
                "extension": "jpg",
                "size": 120636.0
            }
        }
    ],
    "thumbnail": null,
    "categoryId": 2,
    "createdAt": "2024-06-23T20:26:08.627536",
    "updatedAt": "2024-06-23T20:26:08.627536"
}
```