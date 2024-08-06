-- Rename table `classification_attribute` to `attribute`
ALTER TABLE classification_attribute RENAME TO attribute;

-- Rename table `category_classification_attribute` to `category_attribute`
ALTER TABLE category_classification_attributes RENAME TO category_attributes;

-- Rename column `classification_attribute_id` to `attribute_id` in table `category_attribute`
ALTER TABLE category_attributes RENAME COLUMN classification_attribute_id TO attribute_id;
