package com.example.transportationbackend.models.road;

import com.example.transportationbackend.models.CustomPoint;
import com.example.transportationbackend.models.enums.CablePass;
import com.example.transportationbackend.models.lightpost.LightPost;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "road_tb")
public class RegisteredRoad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnId;

    @Column(name = "road_id")
    private Double roadId;

    @ElementCollection
    private List<CustomPoint> points;

    @Column(name = "width")
    private Double width;

    @Column(name = "distance_between_each_light_post")
    private Double distanceEachLightPost;

    @Column(name = "cable_pass")
    @Enumerated(EnumType.STRING)
    private CablePass cablePass;

    @Temporal(TemporalType.TIME)
    @Column(name = "registration_time")
    private Date registrationTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "registration_date")
    private Date registrationDate;

    @OneToMany(mappedBy = "registeredRoad")
    private List<LightPost> lightPosts;

    @PrePersist
    public void setDateTime() {
        registrationTime = new Date();
        registrationDate = new Date();
    }
}
