-- Creating the new tables

CREATE TABLE article
(
    id               BIGSERIAL PRIMARY KEY,
    title            VARCHAR(255)  NOT NULL,
    content          TEXT          NOT NULL,
    author_id        BIGINT        NOT NULL,
    created_at       TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    meta_description VARCHAR(255),
    structured_data  TEXT,
    status           VARCHAR(20) NOT NULL, -- Enum ArticleStatus not stored in DB
    is_featured      BOOLEAN     DEFAULT FALSE,
    slug             VARCHAR(255),
    likes            INTEGER     DEFAULT 0,
    FOREIGN KEY (author_id) REFERENCES app_user (id)
);

CREATE TABLE comment
(
    id         BIGSERIAL PRIMARY KEY,
    author_id  BIGINT NOT NULL,
    text       TEXT   NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES app_user (id)
);

CREATE TABLE location
(
    id        BIGSERIAL PRIMARY KEY,
    latitude  DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    country   VARCHAR(255),
    city      VARCHAR(255),
    zipcode   VARCHAR(20)
);

CREATE TABLE rating
(
    id         BIGSERIAL PRIMARY KEY,
    value      INTEGER NOT NULL,
    shelter_id BIGINT  NOT NULL,
    FOREIGN KEY (shelter_id) REFERENCES app_user (id)
);

CREATE TABLE tag
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Creating join tables for many-to-many relationships

CREATE TABLE article_photo
(
    article_id BIGINT NOT NULL,
    photo_id   BIGINT NOT NULL,
    PRIMARY KEY (article_id, photo_id),
    FOREIGN KEY (article_id) REFERENCES article (id),
    FOREIGN KEY (photo_id) REFERENCES photo (id)
);

CREATE TABLE article_tag
(
    article_id BIGINT NOT NULL,
    tag_id     BIGINT NOT NULL,
    PRIMARY KEY (article_id, tag_id),
    FOREIGN KEY (article_id) REFERENCES article (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id)
);

-- Updating existing tables with new fields

ALTER TABLE ad
    ADD COLUMN location_id BIGINT,
    ADD CONSTRAINT fk_location_ad FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE photo
    ADD COLUMN alt_text VARCHAR(255);

ALTER TABLE app_user
    ADD COLUMN location_id BIGINT,
    ADD CONSTRAINT fk_location_user FOREIGN KEY (location_id) REFERENCES location (id);

-- Adding indexes for performance improvements (optional)

CREATE INDEX idx_article_author ON article (author_id);
CREATE INDEX idx_comment_author ON comment (author_id);
CREATE INDEX idx_rating_shelter ON rating (shelter_id);
CREATE INDEX idx_ad_location ON ad (location_id);
