package com.sandy.ip_location_demo.repo;

import com.sandy.ip_location_demo.entity.VisitorLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorLocationRepository
        extends JpaRepository<VisitorLocation, Long> {
}