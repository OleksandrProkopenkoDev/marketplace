# Photo CRUD
We have Photo entity in our database (just text fields that describe photo), and also we have photo files, stored on
file system neat the project folder (bytes of file).

## 1. Get all Photos By Ad Id
Returns a list of Photo related to Ad with provided id
GET to `/api/v1/photo/ad/{adId}`
**response**
```json
[
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
]
```
So here we have only describing text without actual file.

## 2. Get All Photo Files by Ad id
returns a list of bytes (actual files)
GET to `/api/v1/file/ad/{adId}`
**response**
```json
[
    "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"
]
```

## 3. Get One Photo File by its id
returns one byte array (actual file) by its PhotoId and AdId
GET to `/api/v1/file/ad/{adId}/photo/{photoId}`
**response**
```json
(�.�6ev\�+�q\i��9�׮���X���Y�j_R�����f�g��o:�"��9���^��l�Ϊ��Yz�r����U� Q�VN�N�p�N�ǫ֬���f*\Ҵ6SSSr]�g=�?*��w]'_���G?���.�qמ�z��n������ƀ!�w�ӕ����[YZ�d:�r�8��~j� �H-���X�z��zX��\�Mf��N��=�9yrz��9����9�������e�͋ٙ���g��v�8�i���z`_m
}��Co���/�w;ƽnZ���^S�u2�$w����R���Z%�D(����]'7`־n��jZ\�"N}
```

## 4. Save Ad photos
POST to `/api/v1/photo/ad/{adId}`
`form-data`
`files:{insert your file here}
files:{insert your file here}`

**response**
```json
[
    {
        "id": 72,
        "filename": "d6f30bd2-eb06-4721-9902-560b9b382854.jpg",
        "metadata": {
            "id": 42,
            "width": 606,
            "height": 606,
            "extension": "jpg",
            "size": 54780.0
        }
    },
    {
        "id": 73,
        "filename": "c8c73715-6937-47a6-85a9-f04a3863cf6e.jpg",
        "metadata": {
            "id": 43,
            "width": 746,
            "height": 746,
            "extension": "jpg",
            "size": 120636.0
        }
    }
]
```

## 5. Delete Ad Photos
deletes Photo entities and related files.
DELETE to `/api/v1/photo/ad/{adId}`
request body is an array of photo ids:
`[72,73]`
**response**
```json
[
    "E:\\JavaProjects\\team-challenge\\photo\\ad\\3\\d6f30bd2-eb06-4721-9902-560b9b382854.jpg",
    "E:\\JavaProjects\\team-challenge\\photo\\ad\\3\\c8c73715-6937-47a6-85a9-f04a3863cf6e.jpg"
]
```
it returns paths of deleted files (it will be different on server)