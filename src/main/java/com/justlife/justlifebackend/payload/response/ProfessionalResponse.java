package com.justlife.justlifebackend.payload.response;


import com.justlife.justlifebackend.model.Professional;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProfessionalResponse {

    private Long id;

    private String name;

    private List<AppointmentResponse> appointmentList;

    public ProfessionalResponse(Professional professional){
        this.id = professional.getId();
        this.name = professional.getName();
        this.appointmentList = professional.getAppointmentList().stream().map(AppointmentResponse::new).collect(Collectors.toList());
    }
}
