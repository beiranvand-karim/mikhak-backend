package com.example.transportationbackend.repositories;

import com.example.transportationbackend.models.RegisteredRoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentRoadRepository extends JpaRepository<RegisteredRoad,Long> {
    boolean existsRegisteredRoadByRoadId(Double id);
    RegisteredRoad findRegisteredRoadsByRoadId(Double id);
}
