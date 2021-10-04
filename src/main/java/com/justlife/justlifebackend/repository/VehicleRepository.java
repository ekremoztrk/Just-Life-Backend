package com.justlife.justlifebackend.repository;

import com.justlife.justlifebackend.model.Vehicle;
import com.justlife.justlifebackend.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends BaseRepository<Vehicle, Long> {
}
