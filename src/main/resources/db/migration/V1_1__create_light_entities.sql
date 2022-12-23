CREATE TABLE IF NOT EXISTS archive_lightpost
(
    row_id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    light_post_id         FLOAT8,
    sides                 VARCHAR(255),
    status                VARCHAR(255),
    cause_of_failure      VARCHAR(255),
    contracting_company   VARCHAR(255),
    costs                 BIGINT,
    height                FLOAT8,
    power                 FLOAT8,
    light_production_type VARCHAR(255),
    registeration_time    TIME WITHOUT TIME ZONE,
    registeration_date    date,
    fk_road               BIGINT,
    CONSTRAINT archive_lightpost_pkey PRIMARY KEY (row_id)
);

CREATE TABLE IF NOT EXISTS registered_road_points
(
    registered_road_row_id BIGINT NOT NULL,
    latitude               DOUBLE PRECISION,
    longitude              DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS lightpost_tb
(
    row_id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    light_post_id         FLOAT8,
    sides                 VARCHAR(255),
    status                VARCHAR(255),
    cause_of_failure      VARCHAR(255),
    contracting_company   VARCHAR(255),
    costs                 BIGINT,
    height                FLOAT8,
    power                 FLOAT8,
    light_production_type VARCHAR(255),
    registeration_time    TIME WITHOUT TIME ZONE,
    registeration_date    date,
    fk_road               BIGINT,
    CONSTRAINT pk_lightpost_tb PRIMARY KEY (row_id)
);

CREATE TABLE IF NOT EXISTS road_tb
(
    row_id                        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    road_id                          FLOAT8,
    width                            FLOAT8,
    distance_between_each_light_post FLOAT8,
    cable_pass                       VARCHAR(255),
    registration_time                TIME WITHOUT TIME ZONE,
    registration_date                date,
    CONSTRAINT pk_road_tb PRIMARY KEY (row_id)
);

ALTER TABLE road_tb
    ADD CONSTRAINT uc_road_tb_column UNIQUE (road_id);

ALTER TABLE lightpost_tb
    ADD CONSTRAINT fk_lightpost_tb_on_fk_road FOREIGN KEY (fk_road) REFERENCES road_tb (road_id);

ALTER TABLE registered_road_points
    ADD CONSTRAINT fk_registeredroad_points_on_registered_road FOREIGN KEY (registered_road_row_id) REFERENCES road_tb;
