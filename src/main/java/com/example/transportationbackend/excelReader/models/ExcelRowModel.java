package com.example.transportationbackend.excelReader.models;

import com.example.transportationbackend.models.enums.LightPostSides;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ExcelRowModel {
    private String lightPostId;
    private String sides;
    private String height;
    private String power;
    private String lightProductionType;
    private String roadId;
    private String firstPoint;
    private String secondPoint;
    private String width;
    private String distanceEachLightPost;
    private String cablePass;
    private String status;
    private String causeOfFailure;
    private String contractingCompany;
    private String costs;
}
