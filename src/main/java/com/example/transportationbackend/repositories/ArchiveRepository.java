package com.example.transportationbackend.repositories;

import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
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
        jdbc.execute(CREATE_TABLE + "archive_lightpost(LIKE lightpost_tb INCLUDING ALL)");
    }

    @Transactional
    public void insertExpiredState(double roadId, double lpId) throws DataAccessException {
        jdbc.update("insert into archive_lightpost select * from lightpost_tb where fk_road=? and light_post_id=?",
                (long) roadId,
                (long) lpId);
    }

}
