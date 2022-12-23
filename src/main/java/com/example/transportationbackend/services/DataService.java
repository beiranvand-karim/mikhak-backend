package com.example.transportationbackend.services;

import com.example.transportationbackend.models.lightpost.LightPost;
import com.example.transportationbackend.models.road.RegisteredRoad;
import com.example.transportationbackend.repositories.LightPostRepository;
import com.example.transportationbackend.repositories.JdbcRepository;
import com.example.transportationbackend.repositories.RoadRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    private final RoadRepository roadRepository;

    private final LightPostRepository lpRepository;

    private final JdbcRepository jdbcRepository;

    private RegisteredRoad savedRoadVersion;

    public DataService(RoadRepository roadRepository, LightPostRepository lpRepository, JdbcRepository jdbcRepository) {
        this.roadRepository = roadRepository;
        this.lpRepository = lpRepository;
        this.jdbcRepository = jdbcRepository;
    }

    public void registerRoad(RegisteredRoad road) {
        if (isRoadRegistered(road.getRoadId()))
            savedRoadVersion = findRoadById(road.getRoadId());
        else {
            savedRoadVersion = road;
            roadRepository.save(road);
        }
        saveLightPosts(savedRoadVersion,
                road.getLightPosts());
    }

    private RegisteredRoad findRoadById(double id) {
        return roadRepository.findRoadsByRoadId(id);
    }

    private boolean isRoadRegistered(double id) {
        return roadRepository.existsRoadByRoadId(id);
    }

    private boolean isLightPostSaved(double id) {
        return lpRepository.existsByLightPostId(id);
    }

    public void submitLightPost(LightPost lp, double roadId) {
        RegisteredRoad savedRoad = roadRepository.findRoadsByRoadId(roadId);
        List<LightPost> lpList = new ArrayList<>();
        lpList.add(lp);
        saveLightPosts(savedRoad, lpList);
    }

    private void saveLightPosts(RegisteredRoad road, List<LightPost> lpList) {
        if (!lpList.isEmpty()) {
            for (LightPost lp : lpList) {
                if (isLightPostSaved(lp.getLightPostId()))
                    moveCurrentRoadByIdInArchives(road.getRoadId(), lp.getLightPostId());
                lp.setRegisteredRoad(road);
                lpRepository.save(lp);
            }
        }
    }

    @Transactional
    protected void moveCurrentRoadByIdInArchives(double roadId, double lpId) {
        try {
            jdbcRepository.insertLPOldVersion(roadId, lpId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            lpRepository.deleteByLightPostId(lpId);
        } catch (Exception e) {
            e.printStackTrace();
            LightPost lp = lpRepository.getLightPostByLightPostId(lpId);
            List<Long> ids = new ArrayList<Long>();
            ids.add(lp.getColumnId());
            lpRepository.deleteAllByIdInBatch(ids);
        }
    }

    public List<RegisteredRoad> getAllRoads() {
        return roadRepository.findAll();
    }

    public List<LightPost> getLightPostsByRoadId(double id) {
        return jdbcRepository.getAllLightPostsByRoadId(id);
    }

    public List<LightPost> getAllLightPosts() {
        return lpRepository.findAll();
    }

}
