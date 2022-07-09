package com.example.transportationbackend.excelReader.batch;

import com.example.transportationbackend.excelReader.batch.rowMapper.DataExcelRowMapper;
import com.example.transportationbackend.excelReader.models.LightPostInput;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class DataItemReader extends PoiItemReader<LightPostInput> {

    private String filePath;

    public DataItemReader(){
        setRowMapper(excelRowMapper());
    }

    private RowMapper<LightPostInput> excelRowMapper() {
        return new DataExcelRowMapper();
    }

    public void readData(){
        if(filePath != null && !filePath.isEmpty()) {
            try {
                setResource(new FileSystemResource(filePath));
                this.setLinesToSkip(1);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }
}