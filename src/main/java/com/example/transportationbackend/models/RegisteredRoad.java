package com.example.transportationbackend.models;

import com.example.transportationbackend.models.enums.CablePass;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Entity
@Setter
@Getter
@Table(name = "paths_tb")
public class RegisteredRoad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnId;
    @Column(name = "road_id",
            nullable = false,
            unique = true)
    private Double roadId;

    @Column(name = "latitude_1")
    private double latitude_1;

    @Column(name = "longitude_1")
    private double longitude_1;

    @Column(name = "latitude_2")
    private double latitude_2;

    @Column(name = "longitude_2")
    private double longitude_2;

    @Column(name = "width")
    private Double width;

    @Column(name = "distance_between_each_light_post")
    private Double distanceEachLightPost;

    @Column(name = "cable_pass")
    @Enumerated(EnumType.STRING)
    private CablePass cablePass;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_time_stamp")
    private Date registrationTimeStamp;

    @OneToMany(mappedBy = "registeredRoad")
    private List<LightPost> lightPosts;

    public RegisteredRoad() {
        registrationTimeStamp = new Date();
    }
}
