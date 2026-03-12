package com.assignment.zoneservice.repository;


import com.assignment.zoneservice.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, String> {
}