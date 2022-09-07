package com.example.transportationbackend.excelReader.batch;

import com.example.transportationbackend.models.lightpost.LightPost;
import com.example.transportationbackend.models.road.EmptyRoad;
import com.example.transportationbackend.models.road.RegisteredRoad;
import com.example.transportationbackend.services.DataService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DataItemWriter implements ItemWriter<RegisteredRoad> {

    @Autowired
    private DataService service;
    private RegisteredRoad savedRoadVersion;

    @Override
    public void write(List<? extends RegisteredRoad> roads) throws Exception {
        for (RegisteredRoad road : roads) {
            List<LightPost> lpList = road.getLightPosts();
            if (road.getClass() != EmptyRoad.class) {
                if (service.isRoadRegistered(road.getRoadId()))
                    savedRoadVersion = service.findRoadById(road.getRoadId());
                else {
                    savedRoadVersion = road;
                    service.saveRoad(road);
                }
                if (!lpList.isEmpty()) {
                    LightPost lp = lpList.get(0);
                    if (service.isLightPostSaved(lp.getLightPostId()))
                        service.moveCurrentRoadByIdInArchives(road.getRoadId(), lp.getLightPostId());
                    service.saveLightPost(lp, savedRoadVersion);
                }
            }
        }
    }
}