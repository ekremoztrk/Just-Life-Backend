package com.justlife.justlifebackend.payload.response;

import com.justlife.justlifebackend.model.Appointment;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class AppointmentResponse {

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int duration;


    public AppointmentResponse(Appointment appointment) {

        this.id = appointment.getId();
        this.startTime = appointment.getStartTime();
        this.endTime = appointment.getEndTime();
        this.duration = appointment.getDuration();
    }

}
