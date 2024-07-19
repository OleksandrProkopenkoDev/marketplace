-- Create a new column with the desired name
ALTER TABLE photo
    ADD COLUMN filename VARCHAR(255);

-- Update the new column with the data from the old column
UPDATE photo
SET filename = path;

-- Drop the old column
ALTER TABLE photo
    DROP COLUMN path;