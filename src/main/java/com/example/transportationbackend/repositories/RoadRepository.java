package com.example.transportationbackend.repositories;

import com.example.transportationbackend.models.road.RegisteredRoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadRepository extends JpaRepository<RegisteredRoad, Long> {
    boolean existsRoadByRoadId(Double id);

    RegisteredRoad findRoadsByRoadId(Double id);

    void deleteByRoadId(double id);
}
