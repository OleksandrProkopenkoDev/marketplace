-- Insert Categories
INSERT INTO category (name)
VALUES ('dog'),
       ('cat'),
       ('rodent'),
       ('bird'),
       ('fish'),
       ('other');

-- Insert Attributes
INSERT INTO attribute (name, value_type)
VALUES ('breed', 'STRING'),
       ('age', 'STRING'),
       ('size', 'STRING'),
       ('gender', 'STRING'),
       ('coat_length', 'STRING'),
       ('color', 'STRING'),
       ('health_condition', 'STRING'),
       ('pet_name', 'STRING');

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
VALUES ('user1@example.com', 'password1', 'INDIVIDUAL', 'John', 'Doe', null, 1, NOW(), NOW()),
       ('user2@example.com', 'password2', 'SHELTER', 'Jane', 'Smith', null, 2, NOW(), NOW()),
       ('user3@example.com', 'password3', 'INDIVIDUAL', 'Alice', 'Johnson', null, 3, NOW(), NOW()),
       ('user4@example.com', 'password4', 'SHELTER', 'Bob', 'Brown', null, 4, NOW(), NOW()),
       ('user5@example.com', 'password5', 'INDIVIDUAL', 'Charlie', 'Davis', null, 5, NOW(), NOW());

-- Insert Ads
INSERT INTO ad (author_id, title, description, price, thumbnail_id, category_id, created_at,
                updated_at)
VALUES (1, 'Beautiful dog searching a kind family',
        'This lovely dog is looking for a caring home. Friendly and playful.', 100.00, null, 1, NOW(),
        NOW()),
       (2, 'Adorable cat needing a loving home',
        'Charming cat seeks a cozy place to live. Affectionate and well-behaved.', 200.00, null, 2,
        NOW(), NOW()),
       (3, 'Friendly rabbit available for adoption',
        'Cute rabbit ready for adoption. Gentle and easy to care for.', 300.00, null, 3, NOW(), NOW()),
       (4, 'Lovely bird looking for a home',
        'Colorful bird looking for a home. Chirpy and cheerful.', 400.00, null, 4, NOW(), NOW()),
       (5, 'Charming fish needing a new tank',
        'Beautiful fish ready for a new home. Low maintenance and vibrant.', 500.00, null, 5, NOW(),
        NOW());

-- Insert User Favorites
INSERT INTO user_favorites (user_id, ad_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (4, 1),
       (5, 2);

-- Link Attributes to Categories
INSERT INTO category_attributes (category_id, attribute_id)
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


-- Cat
(2, 1),  -- breed
(2, 2),  -- age
(2, 3),  -- size
(2, 4),  -- gender
(2, 5),  -- good with
(2, 6),  -- coat length
(2, 7),  -- color
(2, 8),  -- care & behaviour


-- Rabbit
(3, 1),  -- breed
(3, 2),  -- age
(3, 3),  -- size
(3, 4),  -- gender
(3, 5),  -- good with
(3, 6),  -- coat length
(3, 7),  -- color
(3, 8),  -- care & behaviour


-- Small & Furry
(4, 1),  -- breed
(4, 2),  -- age
(4, 3),  -- size
(4, 4),  -- gender
(4, 5),  -- good with
(4, 6),  -- coat length
(4, 7),  -- color
(4, 8),  -- care & behaviour


-- Horse
(5, 1),  -- breed
(5, 2),  -- age
(5, 3),  -- size
(5, 4),  -- gender
(5, 5),  -- good with
(5, 6),  -- coat length
(5, 7),  -- color
(5, 8),  -- care & behaviour


-- Bird
(6, 1),  -- breed
(6, 2),  -- age
(6, 3),  -- size
(6, 4),  -- gender
(6, 5),  -- good with
(6, 6),  -- coat length
(6, 7),  -- color
(6, 8);  -- care & behaviour

-- Insert Ad Attributes
INSERT INTO ad_attributes (ad_id, attribute_id, value)
VALUES
    -- Ad 1 (Dog)
    (1, 1, 'Golden Retriever'), -- breed
    (1, 2, '3 years'),         -- age
    (1, 3, 'Large'),           -- size
    (1, 4, 'Male'),            -- gender
    (1, 5, 'Medium'),          -- coat length
    (1, 6, 'Golden'),          -- color
    (1, 7, 'Healthy'),         -- health condition
    (1, 8, 'Buddy'),           -- pet name

    -- Ad 2 (Cat)
    (2, 1, 'Siamese'),         -- breed
    (2, 2, '2 years'),         -- age
    (2, 3, 'Small'),           -- size
    (2, 4, 'Female'),          -- gender
    (2, 5, 'Short'),           -- coat length
    (2, 6, 'Cream'),           -- color
    (2, 7, 'Healthy'),         -- health condition
    (2, 8, 'Whiskers'),        -- pet name

    -- Ad 3 (Rabbit)
    (3, 1, 'Lop'),             -- breed
    (3, 2, '1 year'),          -- age
    (3, 3, 'Small'),           -- size
    (3, 4, 'Female'),          -- gender
    (3, 5, 'Short'),           -- coat length
    (3, 6, 'White'),           -- color
    (3, 7, 'Healthy'),         -- health condition
    (3, 8, 'Fluffy'),          -- pet name

    -- Ad 4 (Bird)
    (4, 1, 'Parakeet'),        -- breed
    (4, 2, '6 months'),        -- age
    (4, 3, 'Small'),           -- size
    (4, 4, 'Male'),            -- gender
    (4, 5, 'Short'),           -- coat length
    (4, 6, 'Green'),           -- color
    (4, 7, 'Healthy'),         -- health condition
    (4, 8, 'Tweety'),          -- pet name

    -- Ad 5 (Fish)
    (5, 1, 'Goldfish'),        -- breed
    (5, 2, '1 year'),          -- age
    (5, 3, 'Small'),           -- size
    (5, 4, 'Female'),          -- gender
    (5, 6, 'Gold'),            -- color
    (5, 7, 'Healthy'),         -- health condition
    (5, 8, 'Bubbles');         -- pet name

INSERT INTO article_tag (article_id, tag_id)
VALUES
  (1, 1),
  (1, 2);