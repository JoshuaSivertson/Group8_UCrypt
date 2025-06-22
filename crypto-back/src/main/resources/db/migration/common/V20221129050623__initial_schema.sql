CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT NOT NULL,
                       created_by_user_id BIGINT,
                       last_modified_by_user_id BIGINT,
                       created_at DATETIME,
                       updated_at DATETIME,
                       name VARCHAR(60) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       status VARCHAR(255) NOT NULL,
                       role_id BIGINT NOT NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT NOT NULL,
                       created_by_user_id BIGINT,
                       last_modified_by_user_id BIGINT,
                       created_at DATETIME,
                       updated_at DATETIME,
                       name VARCHAR(255) NOT NULL,
                       display_name VARCHAR(60) NOT NULL,
                       description VARCHAR(255),
                       PRIMARY KEY (id)
);

-- Constraints for users table
ALTER TABLE users ADD CONSTRAINT uc_users_email UNIQUE (email);
ALTER TABLE users ADD CONSTRAINT FK_USERS_ON_CREATED_BY_USER FOREIGN KEY (created_by_user_id) REFERENCES users (id);
ALTER TABLE users ADD CONSTRAINT FK_USERS_ON_LAST_MODIFIED_BY_USER FOREIGN KEY (last_modified_by_user_id) REFERENCES users (id);
ALTER TABLE users ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

-- Constraints for roles table
ALTER TABLE roles ADD CONSTRAINT uc_roles_display_name UNIQUE (display_name);
ALTER TABLE roles ADD CONSTRAINT uc_roles_name UNIQUE (name);
ALTER TABLE roles ADD CONSTRAINT FK_ROLES_ON_CREATED_BY_USER FOREIGN KEY (created_by_user_id) REFERENCES users (id);
ALTER TABLE roles ADD CONSTRAINT FK_ROLES_ON_LAST_MODIFIED_BY_USER FOREIGN KEY (last_modified_by_user_id) REFERENCES users (id);