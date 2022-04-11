package com.example.transportationbackend.repositories;

import com.example.transportationbackend.models.LightPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LightPostRepository extends JpaRepository<LightPost, Long> {
}
