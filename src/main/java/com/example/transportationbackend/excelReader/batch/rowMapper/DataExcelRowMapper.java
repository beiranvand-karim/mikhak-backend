package com.example.transportationbackend.excelReader.batch.rowMapper;

import com.example.transportationbackend.TransportationBackendApplication;
import com.example.transportationbackend.excelReader.models.LightPostInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

public class DataExcelRowMapper implements RowMapper<LightPostInput> {

    private static final Logger logger = LoggerFactory.getLogger(TransportationBackendApplication.class);
    private final String marker = "Data Excel Row Mapper";

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
            logger.error(marker,e.getMessage());
            logger.info("Use the default row mapper");

            return rowMappers.defaultRowMapper(rowSet);
        }
    }



}
