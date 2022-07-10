package com.example.transportationbackend.excelReader.batch.processor;

import com.example.transportationbackend.TransportationBackendApplication;
import com.example.transportationbackend.excelReader.models.LightPostInput;
import com.example.transportationbackend.excelReader.models.PathInputModel;
import com.example.transportationbackend.models.LightPost;
import com.example.transportationbackend.models.PathEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataProcessor implements ItemProcessor<LightPostInput, LightPost> {

    private final static Logger logger = LoggerFactory.getLogger(TransportationBackendApplication.class);
    private final String marker = "Data Processor";

    @Override
    public LightPost process(LightPostInput lp) throws Exception {
        if (lp == null || lp.equals(new LightPostInput()))
            return null;

        PathEntity pathEntity = new PathEntity();
        List<LightPost> lpList = new ArrayList<>();

        LightPost lpEntity = new LightPost();

        // lightpost setters
        try {
        LightPostSetters.setId(lpEntity,lp.getLightPostId());
        LightPostSetters.setHeight(lpEntity,lp.getHeight());
        LightPostSetters.setPower(lpEntity,lp.getPower());
        LightPostSetters.setLightProductionType(lpEntity,lp.getLightProductionType());
        LightPostSetters.setSides(lpEntity,lp.getSides());

        } catch (Throwable t) {
            logger.error(marker, t.getMessage());
            t.printStackTrace();
        }

        lpList.add(lpEntity);


        // path setters
        try {
            PathInputModel path = lp.getPath();
            PathEntitySetters.setPathId(pathEntity, path.getPathId());
            PathEntitySetters.setFirstPoint(pathEntity, path.getFirstPoint());
            PathEntitySetters.setSecondPoint(pathEntity, path.getSecondPoint());
            PathEntitySetters.setPathWidth(pathEntity, path.getWidth());
            PathEntitySetters.setDistanceEachLightPost(pathEntity, path.getDistanceEachLightPost());
            PathEntitySetters.setCablePassType(pathEntity, path.getCablePass());
            pathEntity.setLightPosts(lpList);
        }
        catch (Throwable t){
            logger.debug(marker);
            logger.debug("path setters", t.getMessage());
        }
        LightPostSetters.setPath(lpEntity,pathEntity);

        return lpEntity;
    }
}
