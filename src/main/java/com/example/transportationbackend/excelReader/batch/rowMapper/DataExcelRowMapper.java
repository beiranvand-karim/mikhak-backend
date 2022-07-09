package com.example.transportationbackend.excelReader.batch.rowMapper;

import com.example.transportationbackend.excelReader.models.LightPostInput;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

public class DataExcelRowMapper implements RowMapper<LightPostInput> {

    @Override
    public LightPostInput mapRow(RowSet rowSet) throws Exception {

        if (rowSet.getCurrentRow() == null) {
            if (rowSet.next())
                return new LightPostInput();
            else
                return null;
        }

        RowMappers rowMappers = new RowMappers();

        try {
            return rowMappers.RowMapperByTitles(rowSet);
        } catch (Exception e) {
            System.out.println("An error occurred in row mapper by columns  *************************");
            System.out.println(rowSet.getCurrentRowIndex());
            System.out.println(e.getMessage());
            return rowMappers.defaultRowMapper(rowSet);
        }
    }



}
