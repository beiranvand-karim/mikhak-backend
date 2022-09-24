package com.example.transportationbackend.excelReader.batch.rowMapper.strategy;

import com.example.transportationbackend.excelReader.models.ExcelRowModel;
import org.springframework.batch.item.excel.support.rowset.RowSet;

import java.util.Arrays;

public class RowMapperByTitles extends RowMapperStrategy {

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

    public RowMapperByTitles() {
        rowModel = new ExcelRowModel();
    }

    @Override
    public ExcelRowModel rowMapper(RowSet rs) throws Exception {


        for (int index = 0; index < rs.getMetaData().getColumnCount(); index++) {

            String title = rs.getMetaData().getColumnName(index);
            String value = rs.getColumnValue(index);

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
            } else if (isTitleCorrect(title, costsTitle)) {
                rowModel.setCosts(value);
            } else if (isTitleCorrect(title, contractingCompanyTitle)) {
                rowModel.setContractingCompany(value);
            } else if (isTitleCorrect(title, causeOfFailureTitle)) {
                rowModel.setCauseOfFailure(value);
            } else if (isTitleCorrect(title, statusTitle)) {
                rowModel.setStatus(value);
            }
        }
        return rowModel;
    }

    private boolean isTitleCorrect(String columnTitle, String[] correctTitle) {
        return Arrays.stream(correctTitle).allMatch(columnTitle::contains);
    }
}
