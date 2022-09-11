package com.example.transportationbackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

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
