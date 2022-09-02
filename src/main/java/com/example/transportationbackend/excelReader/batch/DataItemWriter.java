package com.example.transportationbackend.excelReader.batch;

import com.example.transportationbackend.models.LightPost;
import com.example.transportationbackend.models.RegisteredRoad;
import com.example.transportationbackend.repositories.LightPostRepository;
import com.example.transportationbackend.repositories.CurrentRoadRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DataItemWriter implements ItemWriter<LightPost> {

    @Autowired
    private CurrentRoadRepository currentRoadRepository;

    @Autowired
    private LightPostRepository lpRepository;

    private RegisteredRoad savedRoad;

    @Override
    public void write(List<? extends LightPost> lpLists) throws Exception {
        for (LightPost lp : lpLists) {

            if (!isLightPostExists(lp.getLightPostId())) {

                Double pathId = lp.getRegisteredRoad().getRoadId();
                if (isRoadRegistered(pathId)) {
                    savedRoad = currentRoadRepository.findRegisteredRoadsByRoadId(pathId);
                } else {
                    savedRoad = lp.getRegisteredRoad();
                    currentRoadRepository.save(savedRoad);
                }
                lp.setRegisteredRoad(savedRoad);
                lpRepository.save(lp);
            }
        }
    }

    public boolean isLightPostExists(Double id) {
        return lpRepository.existsByLightPostId(id);
    }

    public boolean isRoadRegistered(Double id) {
        return currentRoadRepository.existsRegisteredRoadByRoadId(id);
    }
}
