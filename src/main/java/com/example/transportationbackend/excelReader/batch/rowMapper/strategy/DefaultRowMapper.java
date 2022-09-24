package com.example.transportationbackend.excelReader.batch.rowMapper.strategy;

import com.example.transportationbackend.excelReader.models.ExcelRowModel;
import org.springframework.batch.item.excel.support.rowset.RowSet;

public class DefaultRowMapper extends RowMapperStrategy {
    public DefaultRowMapper() {
        super();
        rowModel = new ExcelRowModel();
    }

    @Override
    public ExcelRowModel rowMapper(RowSet rs) throws Exception {
        rowModel.setRoadId(rs.getColumnValue(0));
        rowModel.setFirstPoint(rs.getColumnValue(1));
        rowModel.setSecondPoint(rs.getColumnValue(2));
        rowModel.setWidth(rs.getColumnValue(3));
        rowModel.setCablePass(rs.getColumnValue(4));
        rowModel.setDistanceEachLightPost(rs.getColumnValue(5));
        rowModel.setLightPostId(rs.getColumnValue(6));
        rowModel.setSides(rs.getColumnValue(7));
        rowModel.setPower(rs.getColumnValue(8));
        rowModel.setHeight(rs.getColumnValue(9));
        rowModel.setLightProductionType(rs.getColumnValue(10));
        rowModel.setStatus(rs.getColumnValue(11));
        rowModel.setCauseOfFailure(rs.getColumnValue(12));
        rowModel.setContractingCompany(rs.getColumnValue(13));
        rowModel.setCosts(rs.getColumnValue(14));
        return rowModel;
    }
}
