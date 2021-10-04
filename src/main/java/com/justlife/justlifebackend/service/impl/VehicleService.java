package com.justlife.justlifebackend.service.impl;

import com.justlife.justlifebackend.model.Vehicle;
import com.justlife.justlifebackend.repository.VehicleRepository;
import com.justlife.justlifebackend.service.IVehicleService;
import org.springframework.stereotype.Service;

@Service
public class VehicleService extends BaseService<Vehicle, Long> implements IVehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        super("Vehicle Repository", vehicleRepository);
        this.vehicleRepository = vehicleRepository;
    }
}
