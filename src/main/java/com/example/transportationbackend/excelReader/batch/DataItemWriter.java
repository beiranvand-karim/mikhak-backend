package com.example.transportationbackend.excelReader.batch;

import com.example.transportationbackend.models.road.EmptyRoad;
import com.example.transportationbackend.models.road.RegisteredRoad;
import com.example.transportationbackend.repositories.ArchiveRepository;
import com.example.transportationbackend.repositories.LightPostRepository;
import com.example.transportationbackend.repositories.RoadRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DataItemWriter implements ItemWriter<RegisteredRoad> {

    @Autowired
    private RoadRepository roadRepository;

    @Autowired
    private LightPostRepository lpRepository;

    @Autowired
    private ArchiveRepository archiveRepository;

    private RegisteredRoad savedRoadVersion;

    @Override
    public void write(List<? extends RegisteredRoad> roads) throws Exception {
        if (!roadRepository.findAll().isEmpty()) {
            // TODO: 05.09.22 save current into archive
        }
        saveRoads(roads);
    }

    private void saveRoads(List<? extends RegisteredRoad> roads) {
        for (RegisteredRoad road : roads) {
            if (road.getClass() != EmptyRoad.class) {
                if (isRoadRegistered(road.getRoadId()))
                    savedRoadVersion = roadRepository.findRoadsByRoadId(road.getRoadId());
                else {
                    savedRoadVersion = road;
                    roadRepository.save(savedRoadVersion);
                }
                savedRoadVersion.getLightPosts().forEach(lightPost -> {
                    if (isLightPostSaved(lightPost.getLightPostId()))
                        moveCurrentRoadByIdInArchives(road.getRoadId());
                    lightPost.setRegisteredRoad(savedRoadVersion);
                    lpRepository.save(lightPost);
                });
            }
        }
    }

    private void saveCurrentInArchive() {
        try {
            archiveRepository.copyCurrentStateInArchive();
            lpRepository.deleteAll();
            roadRepository.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRoadRegistered(double id) {
        return roadRepository.existsRoadByRoadId(id);
    }

    public boolean isLightPostSaved(double id) {
        return lpRepository.existsByLightPostId(id);
    }

    private void moveCurrentRoadByIdInArchives(Double roadId) {
        try {
            archiveRepository.insertUpdatedRoad(roadId);
            roadRepository.deleteByRoadId(roadId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}