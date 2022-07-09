package com.example.transportationbackend.excelReader.batch.rowMapper;

import com.example.transportationbackend.excelReader.models.LightPostInput;
import com.example.transportationbackend.excelReader.models.PathInputModel;
import org.springframework.batch.item.excel.support.rowset.RowSet;

import java.util.Arrays;

public class RowMappers {

    private final String[] pathIdTitle = {"شناسه", "مسیر"};
    private final String[] firstPointTitle = {"نقطه", "اول"};
    private final String[] secondPointTitle = {"نقطه", "دوم"};
    private final String[] pathWidthTitle = {"طول", "مسیر"};
    private final String[] cablePassTitle = {"نوع", "کابل"};
    private final String[] distanceTitle = {"فاصله", "چراغ"};
    private final String[] lightpostIdTitle = {"شناسه", "چراغ"};
    private final String[] lightpostSidesTitle = {"نوع", "چراغ"};
    private final String[] powerTitle = {"توان", "چراغ"};
    private final String[] heightTitle = {"ارتفاع", "چراغ"};
    private final String[] lightProductionTypeTitle = {"تولید", "نور"};

    private LightPostInput lp;
    private PathInputModel path;

    RowMappers() {
        lp = new LightPostInput();
        path = new PathInputModel();
    }

    LightPostInput RowMapperByTitles(RowSet rowSet) throws Exception {

        for (int index = 0; index < rowSet.getMetaData().getColumnCount(); index++) {

            String title = rowSet.getMetaData().getColumnName(index);
            String value = rowSet.getColumnValue(index);

            if (isTitleCorrect(title, pathIdTitle)) {
                path.setPathId(value);
            } else if (isTitleCorrect(title, firstPointTitle)) {
                path.setFirstPoint(value);
            } else if (isTitleCorrect(title, secondPointTitle)) {
                path.setSecondPoint(value);
            } else if (isTitleCorrect(title, pathWidthTitle)) {
                path.setWidth(value);
            } else if (isTitleCorrect(title, cablePassTitle)) {
                path.setCablePass(value);
            } else if (isTitleCorrect(title, distanceTitle)) {
                path.setDistanceEachLightPost(value);
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
        lp.setPath(path);
        return lp;
    }

    private boolean isTitleCorrect(String columnTitle, String[] correctTitle) {
        if (Arrays.stream(correctTitle).allMatch(columnTitle::contains)) {
            return true;
        }
        return false;
    }

    public LightPostInput defaultRowMapper(RowSet rowSet) throws Exception {

        path.setPathId(rowSet.getColumnValue(0));
        path.setFirstPoint(rowSet.getColumnValue(1));
        path.setSecondPoint(rowSet.getColumnValue(2));
        path.setWidth(rowSet.getColumnValue(3));
        path.setCablePass(rowSet.getColumnValue(4));
        path.setDistanceEachLightPost(rowSet.getColumnValue(5));

        lp.setLightPostId(rowSet.getColumnValue(6));
        lp.setSides(rowSet.getColumnValue(7));
        lp.setPower(rowSet.getColumnValue(8));
        lp.setHeight(rowSet.getColumnValue(9));
        lp.setLightProductionType(rowSet.getColumnValue(10));

        lp.setPath(path);
        return lp;
    }

}
