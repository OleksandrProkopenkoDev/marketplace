-- V3__create_ad_attributes_table.sql

-- Create table `ad_attributes`
CREATE TABLE ad_attributes
(
    id           BIGSERIAL PRIMARY KEY,
    ad_id        BIGINT NOT NULL,
    attribute_id BIGINT NOT NULL,
    value        VARCHAR(255),
    CONSTRAINT fk_ad FOREIGN KEY (ad_id) REFERENCES ad (id),
    CONSTRAINT fk_attribute FOREIGN KEY (attribute_id) REFERENCES attribute (id)
);
