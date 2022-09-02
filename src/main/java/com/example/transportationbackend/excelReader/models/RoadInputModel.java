package com.example.transportationbackend.excelReader.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RoadInputModel {
    private String roadId;
    private String firstPoint;
    private String secondPoint;
    private String width;
    private String distanceEachLightPost;
    private String cablePass;
}
