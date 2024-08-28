ALTER TABLE article
    ADD COLUMN category_id BIGINT,
    ADD CONSTRAINT fk_category_article FOREIGN KEY (category_id) REFERENCES category (id);