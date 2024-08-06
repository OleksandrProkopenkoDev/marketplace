-- Insert Categories
INSERT INTO category (name)
VALUES ('dog'),
       ('cat'),
       ('rabbit'),
       ('small & furry'),
       ('horse'),
       ('bird'),
       ('fish'),
       ('barnyard');

-- Insert Classification Attributes
INSERT INTO classification_attribute (name, value_type)
VALUES ('breed', 'STRING'),
       ('age', 'STRING'),
       ('size', 'STRING'),
       ('gender', 'STRING'),
       ('good with', 'STRING'),
       ('coat length', 'STRING'),
       ('color', 'STRING'),
       ('care & behaviour', 'STRING'),
       ('days on site', 'STRING'),
       ('pet name', 'STRING');

-- Insert ContactInfo
INSERT INTO contact_info (phone)
VALUES ('+380 63 123 45 67'),
       ('+380 63 234 56 78'),
       ('+380 63 345 67 89'),
       ('+380 63 456 78 90'),
       ('+380 63 567 89 01');


-- Insert Users
INSERT INTO app_user (email, password, user_role, first_name, last_name, profile_picture_id,
                  contact_info_id, created_at, updated_at)
VALUES ('user1@example.com', 'password1', 'INDIVIDUAL', 'John', 'Doe', 16, 1, NOW(), NOW()),
       ('user2@example.com', 'password2', 'SHELTER', 'Jane', 'Smith', 17, 2, NOW(), NOW()),
       ('user3@example.com', 'password3', 'INDIVIDUAL', 'Alice', 'Johnson', 18, 3, NOW(), NOW()),
       ('user4@example.com', 'password4', 'SHELTER', 'Bob', 'Brown', 19, 4, NOW(), NOW()),
       ('user5@example.com', 'password5', 'INDIVIDUAL', 'Charlie', 'Davis', 20, 5, NOW(), NOW());

-- Insert Ads
INSERT INTO ad (author_id, title, description, price, thumbnail_id, category_id, created_at,
                updated_at)
VALUES (1, 'Beautiful dog searching a kind family',
        'This lovely dog is looking for a caring home. Friendly and playful.', 100.00, 1, 1, NOW(),
        NOW()),
       (2, 'Adorable cat needing a loving home',
        'Charming cat seeks a cozy place to live. Affectionate and well-behaved.', 200.00, 4, 2,
        NOW(), NOW()),
       (3, 'Friendly rabbit available for adoption',
        'Cute rabbit ready for adoption. Gentle and easy to care for.', 300.00, 7, 3, NOW(), NOW()),
       (4, 'Lovely bird looking for a home',
        'Colorful bird looking for a home. Chirpy and cheerful.', 400.00, 10, 6, NOW(), NOW()),
       (5, 'Charming fish needing a new tank',
        'Beautiful fish ready for a new home. Low maintenance and vibrant.', 500.00, 13, 7, NOW(),
        NOW());

-- Update Photos to link with Ads
UPDATE photo
SET ad_id = 1
WHERE id IN (1, 2, 3);
UPDATE photo
SET ad_id = 2
WHERE id IN (4, 5, 6);
UPDATE photo
SET ad_id = 3
WHERE id IN (7, 8, 9);
UPDATE photo
SET ad_id = 4
WHERE id IN (10, 11, 12);
UPDATE photo
SET ad_id = 5
WHERE id IN (13, 14, 15);

-- Insert User Favorites
INSERT INTO user_favorites (user_id, ad_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (4, 1),
       (5, 2);

-- Link Classification Attributes to Categories
INSERT INTO category_classification_attributes (category_id, classification_attribute_id)
VALUES
-- Dog
(1, 1),  -- breed
(1, 2),  -- age
(1, 3),  -- size
(1, 4),  -- gender
(1, 5),  -- good with
(1, 6),  -- coat length
(1, 7),  -- color
(1, 8),  -- care & behaviour
(1, 9),  -- days on site
(1, 10), -- pet name

-- Cat
(2, 1),  -- breed
(2, 2),  -- age
(2, 3),  -- size
(2, 4),  -- gender
(2, 5),  -- good with
(2, 6),  -- coat length
(2, 7),  -- color
(2, 8),  -- care & behaviour
(2, 9),  -- days on site
(2, 10), -- pet name

-- Rabbit
(3, 1),  -- breed
(3, 2),  -- age
(3, 3),  -- size
(3, 4),  -- gender
(3, 5),  -- good with
(3, 6),  -- coat length
(3, 7),  -- color
(3, 8),  -- care & behaviour
(3, 9),  -- days on site
(3, 10), -- pet name

-- Small & Furry
(4, 1),  -- breed
(4, 2),  -- age
(4, 3),  -- size
(4, 4),  -- gender
(4, 5),  -- good with
(4, 6),  -- coat length
(4, 7),  -- color
(4, 8),  -- care & behaviour
(4, 9),  -- days on site
(4, 10), -- pet name

-- Horse
(5, 1),  -- breed
(5, 2),  -- age
(5, 3),  -- size
(5, 4),  -- gender
(5, 5),  -- good with
(5, 6),  -- coat length
(5, 7),  -- color
(5, 8),  -- care & behaviour
(5, 9),  -- days on site
(5, 10), -- pet name

-- Bird
(6, 1),  -- breed
(6, 2),  -- age
(6, 3),  -- size
(6, 4),  -- gender
(6, 5),  -- good with
(6, 6),  -- coat length
(6, 7),  -- color
(6, 8),  -- care & behaviour
(6, 9),  -- days on site
(6, 10), -- pet name

-- Fish
(7, 1),  -- breed
(7, 2),  -- age
(7, 3),  -- size
(7, 4),  -- gender
(7, 5),  -- good with
(7, 6),  -- coat length
(7, 7),  -- color
(7, 8),  -- care & behaviour
(7, 9),  -- days on site
(7, 10), -- pet name

-- Barnyard
(8, 1),  -- breed
(8, 2),  -- age
(8, 3),  -- size
(8, 4),  -- gender
(8, 5),  -- good with
(8, 6),  -- coat length
(8, 7),  -- color
(8, 8),  -- care & behaviour
(8, 9),  -- days on site
(8, 10); -- pet name
