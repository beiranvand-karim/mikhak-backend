package com.example.transportationbackend.models;

import com.example.transportationbackend.models.enums.CablePass;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Table(name = "paths_tb")
@Data
public class PathEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnId;

    @Column(name = "path_id",
            nullable = false,
            unique = true)
    private Double pathId;

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

    @OneToMany(mappedBy = "path")
    private List<LightPost> lightPosts;
}
