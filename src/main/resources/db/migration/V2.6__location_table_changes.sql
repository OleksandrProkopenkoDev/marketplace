ALTER TABLE location
    DROP COLUMN latitude,
    DROP COLUMN longitude;

ALTER TABLE location
    ADD COLUMN street VARCHAR(255);