package com.justlife.justlifebackend.service;

import com.justlife.justlifebackend.model.Professional;
import com.justlife.justlifebackend.payload.response.VehicleResponseWithProfessional;
import com.justlife.justlifebackend.payload.response.VehicleResponseWithProfessionalAvailableTimes;

import java.time.LocalDateTime;
import java.util.List;


public interface IProfessionalService extends IBaseService<Professional, Long> {

    List<VehicleResponseWithProfessionalAvailableTimes> getAvailableAppointmentsForDate(LocalDateTime date);

    List<VehicleResponseWithProfessional> getAvailableAppointmentsForDateStartTimeAndDuration(LocalDateTime startTime, int duration);
}
