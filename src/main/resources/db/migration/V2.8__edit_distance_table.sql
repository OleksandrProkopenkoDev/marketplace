-- Drop the old columns that stored location IDs directly
ALTER TABLE distance DROP COLUMN location1_id;
ALTER TABLE distance DROP COLUMN location2_id;

-- Add new columns for foreign key references to the Location table
ALTER TABLE distance ADD COLUMN location1_id BIGINT NOT NULL default 0;
ALTER TABLE distance ADD COLUMN location2_id BIGINT NOT NULL default 0;

-- Add foreign key constraints
ALTER TABLE distance
    ADD CONSTRAINT fk_location1_id FOREIGN KEY (location1_id) REFERENCES location(id);

ALTER TABLE distance
    ADD CONSTRAINT fk_location2_id FOREIGN KEY (location2_id) REFERENCES location(id);
