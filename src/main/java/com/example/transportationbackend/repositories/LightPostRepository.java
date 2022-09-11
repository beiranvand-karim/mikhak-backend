package com.example.transportationbackend.repositories;

import com.example.transportationbackend.models.lightpost.LightPost;
import com.example.transportationbackend.models.road.RegisteredRoad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LightPostRepository extends JpaRepository<LightPost, Long> {
    boolean existsByLightPostId(double id);
    void deleteByLightPostId(double id);
}
