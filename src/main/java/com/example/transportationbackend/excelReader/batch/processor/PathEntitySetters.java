package com.example.transportationbackend.excelReader.batch.processor;

import com.example.transportationbackend.TransportationBackendApplication;
import com.example.transportationbackend.models.PathEntity;
import com.example.transportationbackend.models.enums.CablePass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static java.lang.Double.parseDouble;

public class PathEntitySetters {

    private static final String CablePassString = "bottom";

    private static final Logger logger = LoggerFactory.getLogger(TransportationBackendApplication.class);
    private static final String marker = " Data Setter";

    static void setPathId(PathEntity path , String pathId){
        try{
            path.setPathId(parseDouble(pathId));
        }
        catch (Throwable t){
            logger.debug(marker,t.getMessage());
            path.setPathId(new Random(500000).nextDouble());
        }
    }

    static void setFirstPoint(PathEntity path, String firstP) {
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

    static void setSecondPoint(PathEntity path, String secondP) {
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

    static void setPathWidth(PathEntity path, String width) {
        try {
            path.setWidth(parseDouble(width));
        } catch (Throwable t) {
            logger.debug(marker, t.getMessage());
            logger.debug("extract second point", t.getMessage());
            path.setWidth(0.0);
        }
    }

    static void setDistanceEachLightPost(PathEntity path, String distance) {
        try {
            path.setDistanceEachLightPost(parseDouble(distance));
        } catch (Throwable t) {
            logger.debug(marker, t.getMessage());
            logger.debug("extract second point", t.getMessage());
            path.setDistanceEachLightPost(0.0);
        }
    }

    static void setCablePassType(PathEntity path, String cablePass){
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
