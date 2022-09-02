package com.example.transportationbackend.models;

import com.example.transportationbackend.models.enums.LightPostSides;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "light_post_tb")
public class LightPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnId;

    @Column(name = "light_post_id",
            nullable = false,
            unique = true)
    private Double lightPostId;

    @Column(name = "sides")
    @Enumerated(EnumType.STRING)
    private LightPostSides sides;

    @Column(name = "height")
    private Double height;

    @Column(name = "power")
    private Double power;

    @Column(name = "light_production_type")
    private String lightProductionType;

    @ManyToOne
    @JoinColumn(name = "culomnId")
    @JsonIncludeProperties(value = "roadId")
    private RegisteredRoad registeredRoad;

}
