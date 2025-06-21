CREATE TABLE files (
                       id BIGINT AUTO_INCREMENT NOT NULL,
                       created_by_user_id BIGINT,
                       last_modified_by_user_id BIGINT,
                       created_at DATETIME,
                       updated_at DATETIME,
                       name VARCHAR(255) NOT NULL,
                       path VARCHAR(255) NOT NULL,
                       content_type VARCHAR(255) NOT NULL,
                       user_id BIGINT NOT NULL,
                       PRIMARY KEY (id)
);

ALTER TABLE files ADD CONSTRAINT uc_files_name UNIQUE (name);

ALTER TABLE files ADD CONSTRAINT uc_files_path UNIQUE (path);

ALTER TABLE files ADD CONSTRAINT FK_FILES_ON_CREATED_BY_USER FOREIGN KEY (created_by_user_id) REFERENCES users (id);

ALTER TABLE files ADD CONSTRAINT FK_FILES_ON_LAST_MODIFIED_BY_USER FOREIGN KEY (last_modified_by_user_id) REFERENCES users (id);

ALTER TABLE files ADD CONSTRAINT FK_FILES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);