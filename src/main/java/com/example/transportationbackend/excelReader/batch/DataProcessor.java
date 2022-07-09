package com.example.transportationbackend.excelReader.batch;

import com.example.transportationbackend.excelReader.models.LightPostInput;
import com.example.transportationbackend.excelReader.models.PathInputModel;
import com.example.transportationbackend.models.LightPost;
import com.example.transportationbackend.models.PathEntity;
import com.example.transportationbackend.models.enums.CablePass;
import com.example.transportationbackend.models.enums.LightPostSides;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;


@Service
public class DataProcessor implements ItemProcessor<LightPostInput, LightPost> {

    private final String lightPostSidesString = "دوطرفه";
    private final String CablePassString = "bottom";

    @Override
    public LightPost process(LightPostInput lp) throws Exception {
        if (lp == null || lp.equals(new LightPostInput()))
            return null;

        PathEntity pathEntity = new PathEntity();
        LightPost lpEntity = new LightPost();
        List<LightPost> lpList = new ArrayList<>();

        try {
            lpEntity.setLightPostId(parseDouble(lp.getLightPostId()));
            lpEntity.setHeight(parseDouble(lp.getHeight()));
            lpEntity.setPower(parseDouble(lp.getPower()));
            lpEntity.setLightProductionType(lp.getLightProductionType());
            lpEntity.setPath(pathEntity);

            if (equalStrings(lightPostSidesString, lp.getSides()))
                lpEntity.setSides(LightPostSides.TWO_SIDES);
            else
                lpEntity.setSides(LightPostSides.ONE_SIDE);

            lpList.add(lpEntity);
        } catch (Exception e) {
            System.out.println("an error occurred in processor read light post");
            System.out.println(e.getMessage());
        }

        try {
            PathInputModel path = lp.getPath();
            pathEntity.setPathId(parseDouble(path.getPathId()));

            setCoordinates(pathEntity, path.getFirstPoint(), path.getSecondPoint());

            pathEntity.setWidth(parseDouble(path.getWidth()));
            pathEntity.setDistanceEachLightPost(parseDouble(path.getDistanceEachLightPost()));

            if (equalStrings(CablePassString, path.getCablePass()))
                pathEntity.setCablePass(CablePass.BOTTOM);
            else
                pathEntity.setCablePass(CablePass.TOP);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("an error occurred in processor read path");
        }

        pathEntity.setLightPosts(lpList);
        lpEntity.setPath(pathEntity);

        return lpEntity;
    }

    private boolean equalStrings(String src, String destination) {
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

    private void setCoordinates(PathEntity path, String firstP, String secondP) {
        try {
            String[] firstPoints = firstP.trim().split(",");
            path.setLatitude_1(parseDouble(firstPoints[0]));
            path.setLongitude_1(parseDouble(firstPoints[1]));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            path.setLatitude_1(0);
            path.setLongitude_1(0);
        }
        try {
            String[] secondPoints = secondP.trim().split(",");
            path.setLatitude_2(parseDouble(secondPoints[0]));
            path.setLongitude_2(parseDouble(secondPoints[1]));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            path.setLatitude_2(0);
            path.setLongitude_2(0);
        }
    }

}
