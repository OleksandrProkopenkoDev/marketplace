# User Profile Picture operations

## 1. Get User's profile picture
GET to `/api/v1/photo/user/{userId}`
**response**
```json
{
    "id": 61,
    "filename": "ea96cfde-d74c-4e7b-b277-7e6ca6389734.jpg",
    "metadata": {
        "id": 31,
        "width": 4000,
        "height": 5288,
        "extension": "jpg",
        "size": 834772.0
    }
}
```

## 2. Get User's profile picture file
GET to `/api/v1/file/user/{userId}`
**response**
```json
�k�͂0����W�������G����r�ׇ;����i�Ή3�����d�ʋ0�����c�ʍ1�����f�Ώ6����n�֑?����z��M��� �����_�ɖ4���
�u���L���$�����h�՛B��������d�Ҟ@��������i�ءG���&����v��V�ǥ8��������n��R�ĩ7�������u��\�ЭD���-������
```

## 3. Save User's profile picture
POST to `/api/v1/photo/user/{userId}`
`form-data`
`file:{insert your file here}`
If user already has profile picture it will be replaced (delete old picture and save new picture).
**response**
```json
{
    "id": 74,
    "filename": "823b190c-f46c-49ae-b723-878336f91106.jpg",
    "metadata": {
        "id": 44,
        "width": 4000,
        "height": 5288,
        "extension": "jpg",
        "size": 834772.0
    }
}
```

## 4. Delete User's profile picture
DELETE to `/api/v1/photo/user/{userId}`
**response**
`
E:\JavaProjects\team-challenge\photo\user\1\823b190c-f46c-49ae-b723-878336f91106.jpg
`
returns path of deleted file.