package com.example.transportationbackend.excelReader.batch.rowMapper;

import com.example.transportationbackend.TransportationBackendApplication;
import com.example.transportationbackend.excelReader.batch.rowMapper.strategy.DefaultRowMapper;
import com.example.transportationbackend.excelReader.batch.rowMapper.strategy.RowMapperByTitles;
import com.example.transportationbackend.excelReader.batch.rowMapper.strategy.RowMapperStrategy;
import com.example.transportationbackend.excelReader.models.EmptyRowModel;
import com.example.transportationbackend.excelReader.models.ExcelRowModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

public class DataExcelRowMapper implements RowMapper<ExcelRowModel> {

    private static final Logger logger = LoggerFactory.getLogger(TransportationBackendApplication.class);
    private static int emptyRowsCounts = 0;
    private final String marker = "Data Excel Row Mapper";
    private RowMapperStrategy rowMapper;

    @Override
    public ExcelRowModel mapRow(RowSet rowSet) throws Exception {
        if (rowSet.getCurrentRow() == null) {
            emptyRowsCounts++;
            if (rowSet.next() && emptyRowsCounts <= 3)
                return new EmptyRowModel();
            else
                return null;
        }
        emptyRowsCounts = 0;
        rowMapper = new RowMapperByTitles();

        try {
            return rowMapper.rowMapper(rowSet);
        } catch (Exception e) {
            logger.error(marker, e.getMessage());
            logger.info("Use the default row mapper");
            rowMapper = new DefaultRowMapper();
            return rowMapper.rowMapper(rowSet);
        }
    }


}
