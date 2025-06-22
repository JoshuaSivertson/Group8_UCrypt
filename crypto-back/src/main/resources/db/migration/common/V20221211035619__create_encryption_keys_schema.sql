CREATE TABLE encryption_keys (
                                 id BIGINT AUTO_INCREMENT NOT NULL,
                                 created_by_user_id BIGINT,
                                 last_modified_by_user_id BIGINT,
                                 created_at DATETIME,
                                 updated_at DATETIME,
                                 value VARCHAR(60) NOT NULL,
                                 user_id BIGINT NOT NULL,
                                 PRIMARY KEY (id)
);

ALTER TABLE encryption_keys ADD CONSTRAINT FK_ENCRYPTION_KEYS_ON_CREATED_BY_USER FOREIGN KEY (created_by_user_id) REFERENCES users (id);

ALTER TABLE encryption_keys ADD CONSTRAINT FK_ENCRYPTION_KEYS_ON_LAST_MODIFIED_BY_USER FOREIGN KEY (last_modified_by_user_id) REFERENCES users (id);

ALTER TABLE encryption_keys ADD CONSTRAINT FK_ENCRYPTION_KEYS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE INDEX idx_encryption_keys_user_id ON encryption_keys(user_id);