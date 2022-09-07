package com.example.transportationbackend.services;

import com.example.transportationbackend.models.lightpost.LightPost;
import com.example.transportationbackend.models.road.RegisteredRoad;
import com.example.transportationbackend.repositories.LightPostRepository;
import com.example.transportationbackend.repositories.ManualRepository;
import com.example.transportationbackend.repositories.RoadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    private final RoadRepository roadRepository;

    private final LightPostRepository lpRepository;

    private final ManualRepository manualRepository;

    private RegisteredRoad savedRoadVersion;

    public DataService(RoadRepository roadRepository, LightPostRepository lpRepository, ManualRepository manualRepository) {
        this.roadRepository = roadRepository;
        this.lpRepository = lpRepository;
        this.manualRepository = manualRepository;
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

    private void moveCurrentRoadByIdInArchives(double roadId, double lpId) {
        try {
            manualRepository.insertLPOldVersion(roadId, lpId);
            lpRepository.deleteByLightPostId(lpId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RegisteredRoad> getAllRoads() {
        return roadRepository.findAll();
    }

    public List<LightPost> getLightPostsByRoadId(double id) {
        return manualRepository.getAllLightPostsByRoadId(id);
    }

    public List<LightPost> getAllLightPosts() {
        return lpRepository.findAll();
    }
}
