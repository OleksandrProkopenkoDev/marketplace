# Ad CRUD operations V2 (with adAttributes) (ticket MAR-19)

Ad (advertisment) may look like this
```json
{
  "id": 17,
  "authorId": 2,
  "title": "New ad about dog",
  "description": "Bear is a mixed breed puppy who will be ready for adoption July 25th. He will be 9 weeks old and have 2 of his 3 required puppy shots. Mom is a pit/ lab mix. Dad is possibly a husky and or Dane but its unsure due to mom at large while in heat. All puppies are fostered in homes with families being socialized, learning house training and crate training. Adoption fee $300 w $50 refund upon proof of spay/ neuter. Location is near Utica New York 13502. Apply at www.rescuemek9.org",
  "price": 0.0,
  "photos": [
    {
      "id": 23,
      "filename": "a1e5f8be-929e-4cd4-a186-5123033d98fb.jpg",
      "metadata": {
        "id": 23,
        "width": 4032,
        "height": 3024,
        "extension": "jpg",
        "size": 1703382.0
      }
    },
    {
      "id": 24,
      "filename": "bad5e0c7-bbe8-432e-a227-9590798d6253.jpg",
      "metadata": {
        "id": 24,
        "width": 4032,
        "height": 3024,
        "extension": "jpg",
        "size": 1607209.0
      }
    }
  ],
  "thumbnail": {
    "id": 23,
    "filename": "a1e5f8be-929e-4cd4-a186-5123033d98fb.jpg",
    "metadata": {
      "id": 23,
      "width": 4032,
      "height": 3024,
      "extension": "jpg",
      "size": 1703382.0
    }
  },
  "categoryId": 1,
  "adAttributes": [
    {
      "name": "breed",
      "value": "Retriever"
    },
    {
      "name": "age",
      "value": "2 years"
    },
    {
      "name": "size",
      "value": "Big"
    },
    {
      "name": "gender",
      "value": "Male"
    },
    {
      "name": "coat length",
      "value": "Medium"
    },
    {
      "name": "color",
      "value": "Grey"
    },
    {
      "name": "health condition",
      "value": "Healthy"
    },
    {
      "name": "pet name",
      "value": "Rocky"
    }
  ],
  "createdAt": "2024-07-20T20:03:50.713345",
  "updatedAt": "2024-07-20T20:03:50.713345"
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
      "id": 1,
      "authorId": 1,
      "title": "Beautiful dog searching a kind family",
      "description": "This lovely dog is looking for a caring home. Friendly and playful.",
      "price": 100.00,
      "photos": [],
      "thumbnail": null,
      "categoryId": 1,
      "adAttributes": [
        {
          "name": "breed",
          "value": "Golden Retriever"
        },
        {
          "name": "age",
          "value": "3 years"
        },
        {
          "name": "size",
          "value": "Large"
        },
        {
          "name": "gender",
          "value": "Male"
        },
        {
          "name": "coat length",
          "value": "Medium"
        },
        {
          "name": "color",
          "value": "Golden"
        },
        {
          "name": "health condition",
          "value": "Healthy"
        },
        {
          "name": "pet name",
          "value": "Buddy"
        }
      ],
      "createdAt": "2024-07-20T10:03:23.448108",
      "updatedAt": "2024-07-20T10:03:23.448108"
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
      "adAttributes": [
        {
          "name": "breed",
          "value": "Lop"
        },
        {
          "name": "age",
          "value": "1 year"
        },
        {
          "name": "size",
          "value": "Small"
        },
        {
          "name": "gender",
          "value": "Female"
        },
        {
          "name": "coat length",
          "value": "Short"
        },
        {
          "name": "color",
          "value": "White"
        },
        {
          "name": "health condition",
          "value": "Healthy"
        },
        {
          "name": "pet name",
          "value": "Fluffy"
        }
      ],
      "createdAt": "2024-07-20T10:03:23.448108",
      "updatedAt": "2024-07-20T10:03:23.448108"
    },
    {
      "id": 4,
      "authorId": 4,
      "title": "Lovely bird looking for a home",
      "description": "Colorful bird looking for a home. Chirpy and cheerful.",
      "price": 400.00,
      "photos": [],
      "thumbnail": null,
      "categoryId": 4,
      "adAttributes": [
        {
          "name": "breed",
          "value": "Parakeet"
        },
        {
          "name": "age",
          "value": "6 months"
        },
        {
          "name": "size",
          "value": "Small"
        },
        {
          "name": "gender",
          "value": "Male"
        },
        {
          "name": "coat length",
          "value": "Short"
        },
        {
          "name": "color",
          "value": "Green"
        },
        {
          "name": "health condition",
          "value": "Healthy"
        },
        {
          "name": "pet name",
          "value": "Tweety"
        }
      ],
      "createdAt": "2024-07-20T10:03:23.448108",
      "updatedAt": "2024-07-20T10:03:23.448108"
    },
    {
      "id": 5,
      "authorId": 5,
      "title": "Charming fish needing a new tank",
      "description": "Beautiful fish ready for a new home. Low maintenance and vibrant.",
      "price": 500.00,
      "photos": [],
      "thumbnail": null,
      "categoryId": 5,
      "adAttributes": [
        {
          "name": "breed",
          "value": "Goldfish"
        },
        {
          "name": "age",
          "value": "1 year"
        },
        {
          "name": "size",
          "value": "Small"
        },
        {
          "name": "gender",
          "value": "Female"
        },
        {
          "name": "color",
          "value": "Gold"
        },
        {
          "name": "health condition",
          "value": "Healthy"
        },
        {
          "name": "pet name",
          "value": "Bubbles"
        }
      ],
      "createdAt": "2024-07-20T10:03:23.448108",
      "updatedAt": "2024-07-20T10:03:23.448108"
    },
    {
      "id": 6,
      "authorId": 2,
      "title": "New ad about dog",
      "description": "Bear is a mixed breed puppy who will be ready for adoption July 25th. He will be 9 weeks old and have 2 of his 3 required puppy shots. Mom is a pit/ lab mix. Dad is possibly a husky and or Dane but its unsure due to mom at large while in heat. All puppies are fostered in homes with families being socialized, learning house training and crate training. Adoption fee $300 w $50 refund upon proof of spay/ neuter. Location is near Utica New York 13502. Apply at www.rescuemek9.org",
      "price": 0.00,
      "photos": [
        {
          "id": 1,
          "filename": "3d017a26-5f21-4edd-a0dd-bf4cc19fc593.jpg",
          "metadata": {
            "id": 1,
            "width": 4032,
            "height": 3024,
            "extension": "jpg",
            "size": 1703382.0
          }
        },
        {
          "id": 2,
          "filename": "8506e52f-3020-4c70-8bdd-c711e122815b.jpg",
          "metadata": {
            "id": 2,
            "width": 4032,
            "height": 3024,
            "extension": "jpg",
            "size": 1607209.0
          }
        }
      ],
      "thumbnail": {
        "id": 1,
        "filename": "3d017a26-5f21-4edd-a0dd-bf4cc19fc593.jpg",
        "metadata": {
          "id": 1,
          "width": 4032,
          "height": 3024,
          "extension": "jpg",
          "size": 1703382.0
        }
      },
      "categoryId": 1,
      "adAttributes": [],
      "createdAt": "2024-07-20T11:32:17.929541",
      "updatedAt": "2024-07-20T11:32:18.085862"
    },
    {
      "id": 10,
      "authorId": 2,
      "title": "New ad about dog",
      "description": "Bear is a mixed breed puppy who will be ready for adoption July 25th. He will be 9 weeks old and have 2 of his 3 required puppy shots. Mom is a pit/ lab mix. Dad is possibly a husky and or Dane but its unsure due to mom at large while in heat. All puppies are fostered in homes with families being socialized, learning house training and crate training. Adoption fee $300 w $50 refund upon proof of spay/ neuter. Location is near Utica New York 13502. Apply at www.rescuemek9.org",
      "price": 0.00,
      "photos": [
        {
          "id": 9,
          "filename": "16abc9ce-56eb-487d-a38b-883656c6b615.jpg",
          "metadata": {
            "id": 9,
            "width": 4032,
            "height": 3024,
            "extension": "jpg",
            "size": 1703382.0
          }
        },
        {
          "id": 10,
          "filename": "be43085e-6268-479e-873f-c994a2650e75.jpg",
          "metadata": {
            "id": 10,
            "width": 4032,
            "height": 3024,
            "extension": "jpg",
            "size": 1607209.0
          }
        }
      ],
      "thumbnail": {
        "id": 9,
        "filename": "16abc9ce-56eb-487d-a38b-883656c6b615.jpg",
        "metadata": {
          "id": 9,
          "width": 4032,
          "height": 3024,
          "extension": "jpg",
          "size": 1703382.0
        }
      },
      "categoryId": 1,
      "adAttributes": [
        {
          "name": "breed",
          "value": "Retriever"
        },
        {
          "name": "age",
          "value": "2 years"
        },
        {
          "name": "size",
          "value": "Big"
        },
        {
          "name": "gender",
          "value": "Male"
        },
        {
          "name": "coat length",
          "value": "Medium"
        },
        {
          "name": "color",
          "value": "Grey"
        },
        {
          "name": "health condition",
          "value": "Healthy"
        },
        {
          "name": "pet name",
          "value": "Rocky"
        }
      ],
      "createdAt": "2024-07-20T13:31:57.036194",
      "updatedAt": "2024-07-20T13:31:57.186831"
    },
    {
      "id": 11,
      "authorId": 2,
      "title": "New ad about dog",
      "description": "Bear is a mixed breed puppy who will be ready for adoption July 25th. He will be 9 weeks old and have 2 of his 3 required puppy shots. Mom is a pit/ lab mix. Dad is possibly a husky and or Dane but its unsure due to mom at large while in heat. All puppies are fostered in homes with families being socialized, learning house training and crate training. Adoption fee $300 w $50 refund upon proof of spay/ neuter. Location is near Utica New York 13502. Apply at www.rescuemek9.org",
      "price": 0.00,
      "photos": [
        {
          "id": 11,
          "filename": "caf44015-c8f6-4fdb-80b5-188cc0765c29.jpg",
          "metadata": {
            "id": 11,
            "width": 4032,
            "height": 3024,
            "extension": "jpg",
            "size": 1703382.0
          }
        },
        {
          "id": 12,
          "filename": "b89dc869-e2fb-46e8-8ee4-3b702455e1c0.jpg",
          "metadata": {
            "id": 12,
            "width": 4032,
            "height": 3024,
            "extension": "jpg",
            "size": 1607209.0
          }
        }
      ],
      "thumbnail": {
        "id": 11,
        "filename": "caf44015-c8f6-4fdb-80b5-188cc0765c29.jpg",
        "metadata": {
          "id": 11,
          "width": 4032,
          "height": 3024,
          "extension": "jpg",
          "size": 1703382.0
        }
      },
      "categoryId": 1,
      "adAttributes": [
        {
          "name": "breed",
          "value": "Retriever"
        },
        {
          "name": "age",
          "value": "2 years"
        },
        {
          "name": "size",
          "value": "Big"
        },
        {
          "name": "gender",
          "value": "Male"
        },
        {
          "name": "coat length",
          "value": "Medium"
        },
        {
          "name": "color",
          "value": "Grey"
        },
        {
          "name": "health condition",
          "value": "Healthy"
        },
        {
          "name": "pet name",
          "value": "Rocky"
        }
      ],
      "createdAt": "2024-07-20T13:33:12.931793",
      "updatedAt": "2024-07-20T13:33:13.081723"
    },
    {
      "id": 14,
      "authorId": 2,
      "title": "New ad about dog",
      "description": "Bear is a mixed breed puppy who will be ready for adoption July 25th. He will be 9 weeks old and have 2 of his 3 required puppy shots. Mom is a pit/ lab mix. Dad is possibly a husky and or Dane but its unsure due to mom at large while in heat. All puppies are fostered in homes with families being socialized, learning house training and crate training. Adoption fee $300 w $50 refund upon proof of spay/ neuter. Location is near Utica New York 13502. Apply at www.rescuemek9.org",
      "price": 0.00,
      "photos": [
        {
          "id": 17,
          "filename": "79e367c2-74a4-44f0-b9ba-b47372b74f96.jpg",
          "metadata": {
            "id": 17,
            "width": 4032,
            "height": 3024,
            "extension": "jpg",
            "size": 1703382.0
          }
        },
        {
          "id": 18,
          "filename": "ec224894-e983-4883-9e25-adcf9c2d3763.jpg",
          "metadata": {
            "id": 18,
            "width": 4032,
            "height": 3024,
            "extension": "jpg",
            "size": 1607209.0
          }
        }
      ],
      "thumbnail": {
        "id": 17,
        "filename": "79e367c2-74a4-44f0-b9ba-b47372b74f96.jpg",
        "metadata": {
          "id": 17,
          "width": 4032,
          "height": 3024,
          "extension": "jpg",
          "size": 1703382.0
        }
      },
      "categoryId": 1,
      "adAttributes": [
        {
          "name": "breed",
          "value": "Retriever"
        },
        {
          "name": "age",
          "value": "2 years"
        },
        {
          "name": "size",
          "value": "Big"
        },
        {
          "name": "gender",
          "value": "Male"
        },
        {
          "name": "coat length",
          "value": "Medium"
        },
        {
          "name": "color",
          "value": "Grey"
        },
        {
          "name": "health condition",
          "value": "Healthy"
        },
        {
          "name": "pet name",
          "value": "Rocky"
        }
      ],
      "createdAt": "2024-07-20T13:44:31.878791",
      "updatedAt": "2024-07-20T13:44:31.922614"
    },
    {
      "id": 12,
      "authorId": 3,
      "title": "Updated title",
      "description": "new description",
      "price": 3.30,
      "photos": [
        {
          "id": 13,
          "filename": "1b78fb25-d52d-45d7-bfed-cd8894ea037c.jpg",
          "metadata": {
            "id": 13,
            "width": 4032,
            "height": 3024,
            "extension": "jpg",
            "size": 1703382.0
          }
        },
        {
          "id": 14,
          "filename": "c1867c14-8ea8-4861-a7b8-25964e16f5b1.jpg",
          "metadata": {
            "id": 14,
            "width": 4032,
            "height": 3024,
            "extension": "jpg",
            "size": 1607209.0
          }
        }
      ],
      "thumbnail": {
        "id": 13,
        "filename": "1b78fb25-d52d-45d7-bfed-cd8894ea037c.jpg",
        "metadata": {
          "id": 13,
          "width": 4032,
          "height": 3024,
          "extension": "jpg",
          "size": 1703382.0
        }
      },
      "categoryId": 2,
      "adAttributes": [
        {
          "name": "breed",
          "value": "Retriever"
        },
        {
          "name": "age",
          "value": "2 years"
        },
        {
          "name": "size",
          "value": "Big"
        },
        {
          "name": "gender",
          "value": "Male"
        },
        {
          "name": "coat length",
          "value": "Medium"
        },
        {
          "name": "color",
          "value": "Grey"
        },
        {
          "name": "health condition",
          "value": "Healthy"
        },
        {
          "name": "pet name",
          "value": "Rocky"
        }
      ],
      "createdAt": "2024-07-20T13:36:04.247063",
      "updatedAt": "2024-07-20T13:45:59.824919"
    },
    {
      "id": 2,
      "authorId": 3,
      "title": "Updated title",
      "description": "new description",
      "price": 3.30,
      "photos": [],
      "thumbnail": null,
      "categoryId": 2,
      "adAttributes": [
        {
          "name": "coat length",
          "value": "Short"
        },
        {
          "name": "color",
          "value": "Cream"
        },
        {
          "name": "health condition",
          "value": "Healthy"
        },
        {
          "name": "pet name",
          "value": "Whiskers"
        },
        {
          "name": "breed",
          "value": "Retriever Updated"
        },
        {
          "name": "age",
          "value": "2 years updated"
        },
        {
          "name": "size",
          "value": "Big updated"
        },
        {
          "name": "gender",
          "value": "Male updated"
        }
      ],
      "createdAt": "2024-07-20T10:03:23.448108",
      "updatedAt": "2024-07-20T14:34:47.286901"
    }
  ],
  "page": {
    "size": 10,
    "number": 0,
    "totalElements": 11,
    "totalPages": 2
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
adAttributes: [
{
"attributeId": 1,
"value": "Retriever"
},
{
"attributeId": 2,
"value": "2 years"
},
{
"attributeId": 3,
"value": "Big"
},
{
"attributeId": 4,
"value": "Male"
},
{
"attributeId": 5,
"value": "Medium"
},
{
"attributeId": 6,
"value": "Grey"
},
{
"attributeId": 7,
"value": "Healthy"
},
{
"attributeId": 8,
"value": "Rocky"
}
]
`
**response**
```json
{
  "id": 17,
  "authorId": 2,
  "title": "New ad about dog",
  "description": "Bear is a mixed breed puppy who will be ready for adoption July 25th. He will be 9 weeks old and have 2 of his 3 required puppy shots. Mom is a pit/ lab mix. Dad is possibly a husky and or Dane but its unsure due to mom at large while in heat. All puppies are fostered in homes with families being socialized, learning house training and crate training. Adoption fee $300 w $50 refund upon proof of spay/ neuter. Location is near Utica New York 13502. Apply at www.rescuemek9.org",
  "price": 0.0,
  "photos": [
    {
      "id": 23,
      "filename": "a1e5f8be-929e-4cd4-a186-5123033d98fb.jpg",
      "metadata": {
        "id": 23,
        "width": 4032,
        "height": 3024,
        "extension": "jpg",
        "size": 1703382.0
      }
    },
    {
      "id": 24,
      "filename": "bad5e0c7-bbe8-432e-a227-9590798d6253.jpg",
      "metadata": {
        "id": 24,
        "width": 4032,
        "height": 3024,
        "extension": "jpg",
        "size": 1607209.0
      }
    }
  ],
  "thumbnail": {
    "id": 23,
    "filename": "a1e5f8be-929e-4cd4-a186-5123033d98fb.jpg",
    "metadata": {
      "id": 23,
      "width": 4032,
      "height": 3024,
      "extension": "jpg",
      "size": 1703382.0
    }
  },
  "categoryId": 1,
  "adAttributes": [
    {
      "name": "breed",
      "value": "Retriever"
    },
    {
      "name": "age",
      "value": "2 years"
    },
    {
      "name": "size",
      "value": "Big"
    },
    {
      "name": "gender",
      "value": "Male"
    },
    {
      "name": "coat length",
      "value": "Medium"
    },
    {
      "name": "color",
      "value": "Grey"
    },
    {
      "name": "health condition",
      "value": "Healthy"
    },
    {
      "name": "pet name",
      "value": "Rocky"
    }
  ],
  "createdAt": "2024-07-20T20:03:50.713345",
  "updatedAt": "2024-07-20T20:03:50.713345"
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
  "categoryId": 2,
  "adAttributes": [
    {
      "attributeId": 1,
      "value": "Retriever Updated"
    },
    {
      "attributeId": 2,
      "value": "2 years updated"
    },
    {
      "attributeId": 3,
      "value": "Big updated"
    },
    {
      "attributeId": 4,
      "value": "Male updated"
    }
    // {
    //     "attributeId": 5,
    //     "value": "Medium"
    // },
    // {
    //     "attributeId": 6,
    //     "value": "Grey"
    // },
    // {
    //     "attributeId": 7,
    //     "value": "Healthy"
    // },
    // {
    //     "attributeId": 8,
    //     "value": "Rocky"
    // }
  ]
}
```
**response**
```json
{
  "id": 17,
  "authorId": 3,
  "title": "Updated title",
  "description": "new description",
  "price": 3.3,
  "photos": [
    {
      "id": 23,
      "filename": "a1e5f8be-929e-4cd4-a186-5123033d98fb.jpg",
      "metadata": {
        "id": 23,
        "width": 4032,
        "height": 3024,
        "extension": "jpg",
        "size": 1703382.0
      }
    },
    {
      "id": 24,
      "filename": "bad5e0c7-bbe8-432e-a227-9590798d6253.jpg",
      "metadata": {
        "id": 24,
        "width": 4032,
        "height": 3024,
        "extension": "jpg",
        "size": 1607209.0
      }
    }
  ],
  "thumbnail": {
    "id": 23,
    "filename": "a1e5f8be-929e-4cd4-a186-5123033d98fb.jpg",
    "metadata": {
      "id": 23,
      "width": 4032,
      "height": 3024,
      "extension": "jpg",
      "size": 1703382.0
    }
  },
  "categoryId": 2,
  "adAttributes": [
    {
      "name": "breed",
      "value": "Retriever Updated"
    },
    {
      "name": "age",
      "value": "2 years updated"
    },
    {
      "name": "size",
      "value": "Big updated"
    },
    {
      "name": "gender",
      "value": "Male updated"
    },
    {
      "name": "coat length",
      "value": "Medium"
    },
    {
      "name": "color",
      "value": "Grey"
    },
    {
      "name": "health condition",
      "value": "Healthy"
    },
    {
      "name": "pet name",
      "value": "Rocky"
    }
  ],
  "createdAt": "2024-07-20T20:03:50.713345",
  "updatedAt": "2024-07-20T20:03:50.753959"
}
```