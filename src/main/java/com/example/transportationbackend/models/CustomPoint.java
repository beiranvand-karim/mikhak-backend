package com.example.transportationbackend.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable
public class CustomPoint {

    @Column(name = "latitude")
    private double lat;

    @Column(name = "longitude")
    private double lng;

    public CustomPoint() {
        this.lat = 0;
        this.lng = 0;
    }

    public CustomPoint(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
