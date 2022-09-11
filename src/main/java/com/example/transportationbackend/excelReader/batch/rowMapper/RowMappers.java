package com.example.transportationbackend.excelReader.batch.rowMapper;

import com.example.transportationbackend.excelReader.models.ExcelRowModel;
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
    private final String[] statusTitle = {"چراغ", "وضعیت"};
    private final String[] causeOfFailureTitle = {"خاموشی", "علت"};
    private final String[] contractingCompanyTitle = {"شرکت", "پیمانکار"};
    private final String[] costsTitle = {"هزینه"};

    private final ExcelRowModel rowModel;

    RowMappers() {
        rowModel = new ExcelRowModel();
    }

    ExcelRowModel RowMapperByTitles(RowSet rowSet) throws Exception {

        for (int index = 0; index < rowSet.getMetaData().getColumnCount(); index++) {

            String title = rowSet.getMetaData().getColumnName(index);
            String value = rowSet.getColumnValue(index);

            if (isTitleCorrect(title, roadIdTitle)) {
                rowModel.setRoadId(value);
            } else if (isTitleCorrect(title, firstPointTitle)) {
                rowModel.setFirstPoint(value);
            } else if (isTitleCorrect(title, secondPointTitle)) {
                rowModel.setSecondPoint(value);
            } else if (isTitleCorrect(title, roadWidthTitle)) {
                rowModel.setWidth(value);
            } else if (isTitleCorrect(title, cablePassTitle)) {
                rowModel.setCablePass(value);
            } else if (isTitleCorrect(title, distanceTitle)) {
                rowModel.setDistanceEachLightPost(value);
            } else if (isTitleCorrect(title, lightpostIdTitle)) {
                rowModel.setLightPostId(value);
            } else if (isTitleCorrect(title, lightpostSidesTitle)) {
                rowModel.setSides(value);
            } else if (isTitleCorrect(title, powerTitle)) {
                rowModel.setPower(value);
            } else if (isTitleCorrect(title, heightTitle)) {
                rowModel.setHeight(value);
            } else if (isTitleCorrect(title, lightProductionTypeTitle)) {
                rowModel.setLightProductionType(value);
            }else if (isTitleCorrect(title, costsTitle)) {
                rowModel.setCosts(value);
            }else if (isTitleCorrect(title, contractingCompanyTitle)) {
                rowModel.setContractingCompany(value);
            }else if (isTitleCorrect(title, causeOfFailureTitle)) {
                rowModel.setCauseOfFailure(value);
            }else if (isTitleCorrect(title, statusTitle)) {
                rowModel.setStatus(value);
            }
        }
        return rowModel;
    }

    private boolean isTitleCorrect(String columnTitle, String[] correctTitle) {
        return Arrays.stream(correctTitle).allMatch(columnTitle::contains);
    }

    public ExcelRowModel defaultRowMapper(RowSet rowSet) throws Exception {
        rowModel.setRoadId(rowSet.getColumnValue(0));
        rowModel.setFirstPoint(rowSet.getColumnValue(1));
        rowModel.setSecondPoint(rowSet.getColumnValue(2));
        rowModel.setWidth(rowSet.getColumnValue(3));
        rowModel.setCablePass(rowSet.getColumnValue(4));
        rowModel.setDistanceEachLightPost(rowSet.getColumnValue(5));
        rowModel.setLightPostId(rowSet.getColumnValue(6));
        rowModel.setSides(rowSet.getColumnValue(7));
        rowModel.setPower(rowSet.getColumnValue(8));
        rowModel.setHeight(rowSet.getColumnValue(9));
        rowModel.setLightProductionType(rowSet.getColumnValue(10));
        rowModel.setStatus(rowSet.getColumnValue(11));
        rowModel.setCauseOfFailure(rowSet.getColumnValue(12));
        rowModel.setContractingCompany(rowSet.getColumnValue(13));
        rowModel.setCosts(rowSet.getColumnValue(14));
        return rowModel;
    }

}
