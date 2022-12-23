package com.example.transportationbackend.excelReader.batch.rowMapper.strategy;

import com.example.transportationbackend.excelReader.models.ExcelRowModel;
import org.springframework.batch.item.excel.support.rowset.RowSet;

public abstract class RowMapperStrategy {
    protected ExcelRowModel rowModel;
    public abstract ExcelRowModel rowMapper(RowSet rs) throws Exception;
}
