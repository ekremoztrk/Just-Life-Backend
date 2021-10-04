package com.justlife.justlifebackend.payload.request;

import com.justlife.justlifebackend.model.Appointment;
import com.sun.istack.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Getter
public class AppointmentRequest {

    @NotNull
    private Date startTime;

    @NotNull
    private int duration;

    @NotNull
    private List<Long> professionalIds;


    public AppointmentRequest() {
    }

    public AppointmentRequest(Date startTime, int duration, List<Long> professionalIds) {
        this.startTime = startTime;
        this.duration = duration;
        this.professionalIds = professionalIds;
    }

    public LocalDateTime getStartTime() {
        if(startTime!=null)
            return startTime.toInstant().atZone(ZoneId.of("Asia/Istanbul")).toLocalDateTime();
        return null;
    }

}
