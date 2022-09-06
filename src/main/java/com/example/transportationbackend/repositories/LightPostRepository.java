package com.example.transportationbackend.repositories;

import com.example.transportationbackend.models.lightpost.LightPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LightPostRepository extends JpaRepository<LightPost, Long> {
    boolean existsByLightPostId(double id);
}
