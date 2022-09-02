package com.example.transportationbackend.excelReader.batch.rowMapper;

import com.example.transportationbackend.excelReader.models.LightPostInput;
import com.example.transportationbackend.excelReader.models.RoadInputModel;
import org.springframework.batch.item.excel.support.rowset.RowSet;

import java.util.Arrays;

public class RowMappers {

    private final String[] roadIdTitle = {"شناسه", "مسیر"};
    private final String[] firstPointTitle = {"نقطه", "اول"};
    private final String[] secondPointTitle = {"نقطه", "دوم"};
    private final String[] roadWidthTitle = {"طول", "مسیر"};
    private final String[] cablePassTitle = {"نوع", "کابل"};
    private final String[] distanceTitle = {"فاصله", "چراغ"};
    private final String[] lightpostIdTitle = {"شناسه", "چراغ"};
    private final String[] lightpostSidesTitle = {"نوع", "چراغ"};
    private final String[] powerTitle = {"توان", "چراغ"};
    private final String[] heightTitle = {"ارتفاع", "چراغ"};
    private final String[] lightProductionTypeTitle = {"تولید", "نور"};

    private LightPostInput lp;
    private RoadInputModel roadInputModel;

    RowMappers() {
        lp = new LightPostInput();
        roadInputModel = new RoadInputModel();
    }

    LightPostInput RowMapperByTitles(RowSet rowSet) throws Exception {

        for (int index = 0; index < rowSet.getMetaData().getColumnCount(); index++) {

            String title = rowSet.getMetaData().getColumnName(index);
            String value = rowSet.getColumnValue(index);

            if (isTitleCorrect(title, roadIdTitle)) {
                roadInputModel.setRoadId(value);
            } else if (isTitleCorrect(title, firstPointTitle)) {
                roadInputModel.setFirstPoint(value);
            } else if (isTitleCorrect(title, secondPointTitle)) {
                roadInputModel.setSecondPoint(value);
            } else if (isTitleCorrect(title, roadWidthTitle)) {
                roadInputModel.setWidth(value);
            } else if (isTitleCorrect(title, cablePassTitle)) {
                roadInputModel.setCablePass(value);
            } else if (isTitleCorrect(title, distanceTitle)) {
                roadInputModel.setDistanceEachLightPost(value);
            } else if (isTitleCorrect(title, lightpostIdTitle)) {
                lp.setLightPostId(value);
            } else if (isTitleCorrect(title, lightpostSidesTitle)) {
                lp.setSides(value);
            } else if (isTitleCorrect(title, powerTitle)) {
                lp.setPower(value);
            } else if (isTitleCorrect(title, heightTitle)) {
                lp.setHeight(value);
            } else if (isTitleCorrect(title, lightProductionTypeTitle)) {
                lp.setLightProductionType(value);
            }
        }
        lp.setRoadInputModel(roadInputModel);
        return lp;
    }

    private boolean isTitleCorrect(String columnTitle, String[] correctTitle) {
        if (Arrays.stream(correctTitle).allMatch(columnTitle::contains)) {
            return true;
        }
        return false;
    }

    public LightPostInput defaultRowMapper(RowSet rowSet) throws Exception {

        roadInputModel.setRoadId(rowSet.getColumnValue(0));
        roadInputModel.setFirstPoint(rowSet.getColumnValue(1));
        roadInputModel.setSecondPoint(rowSet.getColumnValue(2));
        roadInputModel.setWidth(rowSet.getColumnValue(3));
        roadInputModel.setCablePass(rowSet.getColumnValue(4));
        roadInputModel.setDistanceEachLightPost(rowSet.getColumnValue(5));

        lp.setLightPostId(rowSet.getColumnValue(6));
        lp.setSides(rowSet.getColumnValue(7));
        lp.setPower(rowSet.getColumnValue(8));
        lp.setHeight(rowSet.getColumnValue(9));
        lp.setLightProductionType(rowSet.getColumnValue(10));

        lp.setRoadInputModel(roadInputModel);
        return lp;
    }

}
