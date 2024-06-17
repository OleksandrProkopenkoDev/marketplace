-- V1.1__initial_setup.sql

CREATE TABLE user_role
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE value_type
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE contact_info
(
    id    SERIAL PRIMARY KEY,
    phone VARCHAR(255) NOT NULL
);

CREATE TABLE photo_metadata
(
    id        SERIAL PRIMARY KEY,
    width     INTEGER     NOT NULL,
    height    INTEGER     NOT NULL,
    extension VARCHAR(10) NOT NULL,
    size      FLOAT       NOT NULL
);

CREATE TABLE photo
(
    id          SERIAL PRIMARY KEY,
    path        VARCHAR(255) NOT NULL,
    metadata_id BIGINT,
    CONSTRAINT fk_metadata
        FOREIGN KEY (metadata_id)
            REFERENCES photo_metadata (id)
);

CREATE TABLE "user"
(
    id                 SERIAL PRIMARY KEY,
    email              VARCHAR(255) NOT NULL,
    password           VARCHAR(255) NOT NULL,
    user_role          VARCHAR(255) NOT NULL,
    first_name         VARCHAR(255) NOT NULL,
    last_name          VARCHAR(255),
    profile_picture_id BIGINT,
    contact_info_id    BIGINT,
    created_at         TIMESTAMPTZ,
    updated_at         TIMESTAMPTZ,
    CONSTRAINT fk_profile_picture
        FOREIGN KEY (profile_picture_id)
            REFERENCES photo (id),
    CONSTRAINT fk_contact_info
        FOREIGN KEY (contact_info_id)
            REFERENCES contact_info (id)
);

CREATE TABLE category
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE classification_attribute
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    value_type VARCHAR(255) NOT NULL
);

CREATE TABLE category_classification_attributes
(
    category_id                 BIGINT NOT NULL,
    classification_attribute_id BIGINT NOT NULL,
    PRIMARY KEY (category_id, classification_attribute_id),
    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
            REFERENCES category (id),
    CONSTRAINT fk_classification_attribute
        FOREIGN KEY (classification_attribute_id)
            REFERENCES classification_attribute (id)
);

CREATE TABLE ad
(
    id           SERIAL PRIMARY KEY,
    author_id    BIGINT         NOT NULL,
    title        VARCHAR(255)   NOT NULL,
    price        DECIMAL(19, 2) NOT NULL,
    thumbnail_id BIGINT,
    category_id  BIGINT,
    created_at   TIMESTAMPTZ,
    updated_at   TIMESTAMPTZ,
    CONSTRAINT fk_author
        FOREIGN KEY (author_id)
            REFERENCES "user" (id),
    CONSTRAINT fk_thumbnail
        FOREIGN KEY (thumbnail_id)
            REFERENCES photo (id),
    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
            REFERENCES category (id)
);

CREATE TABLE user_favorites
(
    user_id BIGINT NOT NULL,
    ad_id   BIGINT NOT NULL,
    PRIMARY KEY (user_id, ad_id),
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES "user" (id),
    CONSTRAINT fk_ad
        FOREIGN KEY (ad_id)
            REFERENCES ad (id)
);

ALTER TABLE photo
    ADD COLUMN ad_id BIGINT;

ALTER TABLE photo
    ADD CONSTRAINT fk_ad
        FOREIGN KEY (ad_id)
            REFERENCES ad(id);
