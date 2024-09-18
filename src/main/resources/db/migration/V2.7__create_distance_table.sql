-- Migration script to create the 'distance' table with foreign keys

CREATE TABLE distance
(
    id                 BIGSERIAL PRIMARY KEY,
    location1_id       BIGINT NOT NULL REFERENCES location (id) ON DELETE CASCADE,
    location2_id       BIGINT NOT NULL REFERENCES location (id) ON DELETE CASCADE,
    distance_in_meters DOUBLE PRECISION
);
