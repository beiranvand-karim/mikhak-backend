package com.example.transportationbackend.excelReader.batch.processor;

import com.example.transportationbackend.TransportationBackendApplication;
import com.example.transportationbackend.models.RegisteredRoad;
import com.example.transportationbackend.models.enums.CablePass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static java.lang.Double.parseDouble;

public class RoadSetters {

    private static final String CablePassString = "bottom";

    private static final Logger logger = LoggerFactory.getLogger(TransportationBackendApplication.class);
    private static final String marker = " Data Setter";

    static void setRoadIds(RegisteredRoad road , String roadId){
        try{
            road.setRoadId(parseDouble(roadId));
        }
        catch (Throwable t){
            logger.debug(marker,t.getMessage());
            road.setRoadId(new Random(500000).nextDouble());
        }
    }

    static void setFirstPoint(RegisteredRoad path, String firstP) {
        try {
            String[] firstPoints = firstP.trim().split(",");
            path.setLatitude_1(parseDouble(firstPoints[0]));
            path.setLongitude_1(parseDouble(firstPoints[1]));
        } catch (Throwable t) {
            logger.debug(marker, t.getMessage());
            logger.debug("extract first point", t.getMessage());
            path.setLatitude_1(0);
            path.setLongitude_1(0);
        }
    }

    static void setSecondPoint(RegisteredRoad path, String secondP) {
        try {
            String[] secondPoints = secondP.trim().split(",");
            path.setLatitude_2(parseDouble(secondPoints[0]));
            path.setLongitude_2(parseDouble(secondPoints[1]));
        } catch (Throwable t) {
            logger.debug(marker, t.getMessage());
            logger.debug("extract second point", t.getMessage());
            path.setLatitude_2(0);
            path.setLongitude_2(0);
        }
    }

    static void setRoadWidth(RegisteredRoad path, String width) {
        try {
            path.setWidth(parseDouble(width));
        } catch (Throwable t) {
            logger.debug(marker, t.getMessage());
            logger.debug("extract second point", t.getMessage());
            path.setWidth(0.0);
        }
    }

    static void setDistanceEachLightPost(RegisteredRoad path, String distance) {
        try {
            path.setDistanceEachLightPost(parseDouble(distance));
        } catch (Throwable t) {
            logger.debug(marker, t.getMessage());
            logger.debug("extract second point", t.getMessage());
            path.setDistanceEachLightPost(0.0);
        }
    }

    static void setCablePassType(RegisteredRoad path, String cablePass){
        if (equalStrings(CablePassString, cablePass))
            path.setCablePass(CablePass.BOTTOM);
        else
            path.setCablePass(CablePass.TOP);

    }

    private static boolean equalStrings(String src, String destination) {
        String srcTrim = src.trim();
        String destinationTrim = destination.trim();

        if (src.equals(destination)
                || srcTrim.equals(destinationTrim)
                || src.contains(destinationTrim)
                || src.endsWith(destinationTrim)) {
            return true;
        }
        return false;
    }
}
