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

    @Override
    public void write(List<? extends RegisteredRoad> roads) throws Exception {
        for (RegisteredRoad road : roads) {
            if (road.getClass() != EmptyRoad.class)
                service.registerRoad(road);
        }
    }
}