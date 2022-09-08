package com.example.transportationbackend.models.lightpost;

import com.example.transportationbackend.models.enums.LightPostSides;
import com.example.transportationbackend.models.enums.LightPostStatus;
import com.example.transportationbackend.models.road.RegisteredRoad;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "lightpost_tb")
public class LightPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, insertable = false, updatable = false)
    private Long columnId;

    @Column(name = "light_post_id")
    private Double lightPostId;

    @Column(name = "sides")
    @Enumerated(EnumType.STRING)
    private LightPostSides sides;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LightPostStatus status;

    @Column(name = "cause_of_failure")
    private String causeOfFailure;

    @Column(name = "contracting_company")
    private String contractingCompany;

    @Column(name = "costs")
    private long costs;

    @Column(name = "height")
    private Double height;

    @Column(name = "power")
    private Double power;

    @Column(name = "light_production_type")
    private String lightProductionType;

    @Temporal(TemporalType.TIME)
    @Column(name = "registeration_time")
    private Date registrationTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "registeration_date")
    private Date registrationDate;

    @JoinColumn(referencedColumnName = "road_id",
            name = "FK_road")
    @JsonIncludeProperties(value = "roadId")
    @ManyToOne
    private RegisteredRoad registeredRoad;

    @PrePersist
    public void setDateTime() {
        registrationTime = new Date();
        registrationDate = new Date();
    }
}
