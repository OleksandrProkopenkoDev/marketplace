ALTER TABLE comment
    ADD COLUMN shelter_id BIGINT,
    ADD CONSTRAINT fk_shelter_comment FOREIGN KEY (shelter_id) REFERENCES app_user (id);