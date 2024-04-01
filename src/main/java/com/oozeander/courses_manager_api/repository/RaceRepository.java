package com.oozeander.courses_manager_api.repository;

import com.oozeander.courses_manager_api.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RaceRepository extends JpaRepository<Race, UUID> {
}
