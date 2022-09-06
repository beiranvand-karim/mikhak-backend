package com.example.transportationbackend.repositories;

import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArchiveRepository {

    private static final String CREATE_TABLE = "create table if not exists ";
    private final JdbcTemplate jdbc;

    public ArchiveRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Transactional
    public void createTableIfNotExists() {
        jdbc.execute(CREATE_TABLE + "archive_road(LIKE road_tb INCLUDING ALL)");
        jdbc.execute(CREATE_TABLE + "archive_lightpost(LIKE lightpost_tb INCLUDING ALL)");
    }

    @Transactional
    public void copyCurrentStateInArchive() {
        jdbc.execute("insert into archive_road select * from road_tb");
        jdbc.execute("insert into archive_lightpost select * from lightpost_tb");
    }

    @Transactional
    public void insertUpdatedRoad(double id) {
        jdbc.update("insert into archive_road select * from user_tb where road_id=?", id);
        jdbc.update("insert into archive_lightpost select * from email_tb where road_id=?", id);
    }

}
