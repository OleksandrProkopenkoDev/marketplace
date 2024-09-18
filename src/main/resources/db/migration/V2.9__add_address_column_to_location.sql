-- Step 1: Add a new column for address
ALTER TABLE location
    ADD COLUMN address VARCHAR(255);

-- Step 2: Update the new address column with concatenated values
UPDATE location
SET address = CONCAT_WS(', ', COALESCE(street, ''), COALESCE(city, ''), COALESCE(country, ''),
                        COALESCE(zipcode, ''));

-- Step 3: Optionally drop the old columns if not needed
ALTER TABLE location
    DROP COLUMN street;
ALTER TABLE location
    DROP COLUMN city;
ALTER TABLE location
    DROP COLUMN country;
ALTER TABLE location
    DROP COLUMN zipcode;
