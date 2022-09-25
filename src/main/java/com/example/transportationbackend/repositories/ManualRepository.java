package com.example.transportationbackend.repositories;

import com.example.transportationbackend.models.lightpost.LightPost;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ManualRepository {

    private final JdbcTemplate jdbc;

    public ManualRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Transactional
    public void insertLPOldVersion(double roadId, double lpId) throws DataAccessException {
        jdbc.update("insert into archive_lightpost select * from lightpost_tb where fk_road=? and light_post_id=?",
                (long) roadId,
                (long) lpId);
    }

    @Transactional
    public List<LightPost> getAllLightPostsByRoadId(double roadId) throws DataAccessException {
        return jdbc.queryForList("select * from lighpost_tb where fk_road=?",
                LightPost.class,
                (long) roadId);
    }

}
