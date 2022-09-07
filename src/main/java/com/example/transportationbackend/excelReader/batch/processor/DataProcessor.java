package com.example.transportationbackend.excelReader.batch.processor;

import com.example.transportationbackend.TransportationBackendApplication;
import com.example.transportationbackend.excelReader.models.EmptyRowModel;
import com.example.transportationbackend.excelReader.models.ExcelRowModel;
import com.example.transportationbackend.models.CustomPoint;
import com.example.transportationbackend.models.enums.CablePass;
import com.example.transportationbackend.models.enums.LightPostSides;
import com.example.transportationbackend.models.lightpost.LightPost;
import com.example.transportationbackend.models.road.EmptyRoad;
import com.example.transportationbackend.models.road.RegisteredRoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataProcessor implements ItemProcessor<ExcelRowModel, RegisteredRoad> {

    private final static Logger logger = LoggerFactory.getLogger(TransportationBackendApplication.class);
    private final String marker = "Data Processor";
    private static int emptyRoadsCount = 0;

    @Override
    public RegisteredRoad process(ExcelRowModel rowModel) throws Exception {
        if (rowModel == null || emptyRoadsCount >= 4) {
            return null;
        } else if (rowModel.getClass() == EmptyRowModel.class) {
            emptyRoadsCount++;
            return new EmptyRoad();
        }
        emptyRoadsCount = 0;
        List<LightPost> lpList = new ArrayList<>();
        CablePass cablePass = CablePass.TOP;
        CustomPoint firstP = new CustomPoint(0, 0),
                secondP = new CustomPoint(0, 0);
        double roadId = -1,
                roadWidth = 0,
                distanceLps = 0;
        LightPostSides lpSides = LightPostSides.TWO_SIDES;
        double lpId = 0,
                lpHeight = 0,
                lpPower = 0;
        try {
            roadId = DataProcessorHelper.parseIdToDouble(rowModel.getRoadId());
            roadWidth = DataProcessorHelper.parseToDouble(rowModel.getWidth());
            distanceLps = DataProcessorHelper.parseToDouble(rowModel.getDistanceEachLightPost());
            firstP = DataProcessorHelper.extractPoint(rowModel.getFirstPoint());
            secondP = DataProcessorHelper.extractPoint(rowModel.getSecondPoint());
            cablePass = DataProcessorHelper.extractRoadCablePass(rowModel.getCablePass());
            lpId = DataProcessorHelper.parseIdToDouble(rowModel.getLightPostId());
            lpHeight = DataProcessorHelper.parseToDouble(rowModel.getHeight());
            lpPower = DataProcessorHelper.parseToDouble(rowModel.getPower());
            lpSides = DataProcessorHelper.extractLightPostSides(rowModel.getSides());
        } catch (Exception e) {
            logger.debug(marker, e);
        }
        ArrayList<CustomPoint> points = new ArrayList<>();
        points.add(firstP);
        points.add(secondP);
        RegisteredRoad registeredRoad = RegisteredRoad
                .builder()
                .roadId(roadId)
                .width(roadWidth)
                .distanceEachLightPost(distanceLps)
                .points(points)
                .cablePass(cablePass)
                .build();

        LightPost lpEntity = LightPost
                .builder()
                .power(lpPower)
                .height(lpHeight)
                .lightPostId(lpId)
                .lightProductionType(rowModel.getLightProductionType())
                .sides(lpSides)
                .build();
        lpEntity.setRegisteredRoad(registeredRoad);
        lpList.add(lpEntity);
        registeredRoad.setLightPosts(lpList);
        return registeredRoad;
    }
}
