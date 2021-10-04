package com.justlife.justlifebackend.payload.response;

import com.justlife.justlifebackend.model.Vehicle;
import lombok.Getter;

import java.util.List;

@Getter
public class VehicleResponseWithProfessionalAvailableTimes {

    private Long vehicleId;

    private List<ProfessionalWithAvailableTimes> professionals;

    public VehicleResponseWithProfessionalAvailableTimes(Vehicle vehicle, List<ProfessionalWithAvailableTimes> professionals){

        this.vehicleId = vehicle.getId();
        this.professionals = professionals;

    }


}
