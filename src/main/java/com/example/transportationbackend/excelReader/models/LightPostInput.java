package com.example.transportationbackend.excelReader.models;

import lombok.Data;

@Data
public class LightPostInput {
    private String lightPostId;
    private String sides;
    private String height;
    private String power;
    private String lightProductionType;
    private RoadInputModel roadInputModel;
}
