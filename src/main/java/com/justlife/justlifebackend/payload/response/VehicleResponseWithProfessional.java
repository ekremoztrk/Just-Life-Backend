package com.justlife.justlifebackend.payload.response;

import com.justlife.justlifebackend.model.Professional;
import com.justlife.justlifebackend.model.Vehicle;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class VehicleResponseWithProfessional {

    private Long vehicleId;

    private List<SimpleProfessionalResponse> professionals;

    public VehicleResponseWithProfessional(Vehicle vehicle, List<Professional> professionals){

        this.vehicleId = vehicle.getId();
        this.professionals = professionals.stream().map(SimpleProfessionalResponse::new).collect(Collectors.toList());

    }
}
