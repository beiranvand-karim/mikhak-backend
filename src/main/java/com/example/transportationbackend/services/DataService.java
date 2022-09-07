package com.example.transportationbackend.services;

import com.example.transportationbackend.models.lightpost.LightPost;
import com.example.transportationbackend.models.road.RegisteredRoad;
import com.example.transportationbackend.repositories.ArchiveRepository;
import com.example.transportationbackend.repositories.LightPostRepository;
import com.example.transportationbackend.repositories.RoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    @Autowired
    private RoadRepository roadRepository;

    @Autowired
    private LightPostRepository lpRepository;

    @Autowired
    private ArchiveRepository archiveRepository;

    public boolean isCurrentStateEmpty() {
        return roadRepository.findAll().isEmpty();
    }

    public RegisteredRoad findRoadById(double id) {
        return roadRepository.findRoadsByRoadId(id);
    }

    public boolean isRoadRegistered(double id) {
        return roadRepository.existsRoadByRoadId(id);
    }

    public boolean isLightPostSaved(double id) {
        return lpRepository.existsByLightPostId(id);
    }

    public void saveRoad(RegisteredRoad road) {
        roadRepository.save(road);
    }

    public void saveLightPost(LightPost lp, RegisteredRoad road) {
        lp.setRegisteredRoad(road);
        lpRepository.save(lp);
    }

    public void moveCurrentRoadByIdInArchives(double roadId,double lpId) {
        try {
            archiveRepository.insertExpiredState(roadId,lpId);
            lpRepository.deleteByLightPostId(lpId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
