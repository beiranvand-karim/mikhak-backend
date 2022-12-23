CREATE TABLE user_tb
(
    id            VARCHAR(255) NOT NULL,
    password      VARCHAR(255),
    email_address VARCHAR(255),
    phone         VARCHAR(255),
    role          VARCHAR(255),
    CONSTRAINT pk_user_tb PRIMARY KEY (id)
);

ALTER TABLE user_tb
    ADD CONSTRAINT uc_user_tb_email_address UNIQUE (email_address);

ALTER TABLE user_tb
    ADD CONSTRAINT uc_user_tb_phone UNIQUE (phone);

ALTER TABLE user_tb
    ADD CONSTRAINT uc_user_tb_id UNIQUE (id);