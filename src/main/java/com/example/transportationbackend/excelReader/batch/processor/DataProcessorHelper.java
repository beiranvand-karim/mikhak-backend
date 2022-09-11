package com.example.transportationbackend.excelReader.batch.processor;

import com.example.transportationbackend.TransportationBackendApplication;
import com.example.transportationbackend.models.CustomPoint;
import com.example.transportationbackend.models.enums.CablePass;
import com.example.transportationbackend.models.enums.LightPostSides;
import com.example.transportationbackend.models.enums.LightPostStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static java.lang.Double.parseDouble;

public class DataProcessorHelper {

    private static final Logger logger = LoggerFactory.getLogger(TransportationBackendApplication.class);
    private static final String marker = "Data Setter";
    private static final String ONE_SIDES_STRING = "یک طرفه";
    private static final String on = "on",
            Off = "Off",
            Maintenance = "Maintenance";
    private static LightPostSides defaultSide = LightPostSides.TWO_SIDES;
    private static double lat = 0.0;
    private static double lng = 0.0;
    private static double value = 0.0;

    static double parseIdToDouble(String src) {
        double id = 0;
        try {
            id = parseDouble(src);
        } catch (Throwable t) {
            logger.debug(marker, t);
            id = new Random(500000).nextDouble();
        }
        return id;
    }

    static CustomPoint extractPoint(String pointsString) {
        try {
            String[] points = pointsString.trim().split(",");
            lat = parseDouble(points[0]);
            lng = parseDouble(points[1]);
        } catch (Throwable t) {
            logger.debug("extract point", t);
        }
        return new CustomPoint(lat, lng);
    }

    static LightPostSides extractLightPostSides(String sides) {
        try {
            if (equalStrings(ONE_SIDES_STRING, sides))
                defaultSide = LightPostSides.ONE_SIDE;
        } catch (Throwable t) {
            logger.debug("light post sides", t);
        }
        return defaultSide;
    }

    static CablePass extractRoadCablePass(String src) {
        if (equalStrings(src, "bottom"))
            return CablePass.BOTTOM;
        else
            return CablePass.TOP;
    }

    private static boolean equalStrings(String src, String destination) {
        String srcTrim = src.trim();
        String destinationTrim = destination.trim();
        return src.equals(destination)
                || srcTrim.equals(destinationTrim)
                || src.contains(destinationTrim)
                || src.endsWith(destinationTrim);
    }

    static double parseToDouble(String src) {
        try {
            value = parseDouble(src);
        } catch (Throwable t) {
            logger.debug("parse to double", t);
        }
        return value;
    }

    public static LightPostStatus extractLPStatus(String str) {
        LightPostStatus lpStatus = LightPostStatus.On;
        try {
            if (str.equalsIgnoreCase(Off))
                lpStatus = LightPostStatus.Off;
            else if (str.equalsIgnoreCase(Maintenance))
                lpStatus = LightPostStatus.Maintenance;
        } catch (Throwable t) {
            logger.debug("parse to double", t);
        }
        return lpStatus;
    }
}
