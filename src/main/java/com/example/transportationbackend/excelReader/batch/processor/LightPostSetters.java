package com.example.transportationbackend.excelReader.batch.processor;

import com.example.transportationbackend.TransportationBackendApplication;
import com.example.transportationbackend.excelReader.models.PathInputModel;
import com.example.transportationbackend.models.LightPost;
import com.example.transportationbackend.models.PathEntity;
import com.example.transportationbackend.models.enums.LightPostSides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static java.lang.Double.parseDouble;

public class LightPostSetters {

    private static final String lightPostSidesString = "دوطرفه";
    private static final Logger logger = LoggerFactory.getLogger(TransportationBackendApplication.class);
    private static final String marker = " LightPost setters";

    static void setId(LightPost lp,String id){
        try {
            lp.setLightPostId(parseDouble(id));
        }catch (Throwable t){
            logger.debug(marker,t.getMessage());
            logger.debug("lightpost id",t.getMessage());
            lp.setLightPostId(new Random(500000).nextDouble());
        }
    }

    static void setHeight(LightPost lp,String height){
        try {
            lp.setHeight(parseDouble(height));
        }catch (Throwable t){
            logger.debug(marker,t.getMessage());
            logger.debug("height",t.getMessage());
            lp.setHeight(0.0);
        }
    }

    static void setPower(LightPost lp,String power){
        try {
            lp.setPower(parseDouble(power));
        }catch (Throwable t){
            logger.debug(marker,t.getMessage());
            logger.debug("power",t.getMessage());
            lp.setPower(0.0);
        }
    }

    static void setLightProductionType(LightPost lp,String productionType ){
        try {
            lp.setLightProductionType(productionType);
        }catch (Throwable t){
            logger.debug(marker,t.getMessage());
            logger.debug("light production type",t.getMessage());
            lp.setLightProductionType("");
        }
    }

    static void setPath(LightPost lp, PathEntity path){
        try {
            lp.setPath(path);
        }catch (Throwable t){
            logger.debug(marker,t.getMessage());
            logger.debug("path",t.getMessage());
        }
    }

    static void setSides(LightPost lp,String sides){
        try {
            if (equalStrings(lightPostSidesString, sides))
                lp.setSides(LightPostSides.TWO_SIDES);
            else
                lp.setSides(LightPostSides.ONE_SIDE);
        }catch (Throwable t){
            logger.debug(marker,t.getMessage());
            logger.debug("sides",t.getMessage());
        }
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
