package com.justlife.justlifebackend.service;

import com.justlife.justlifebackend.model.Appointment;
import com.justlife.justlifebackend.payload.request.AppointmentRequest;

public interface IAppointmentService extends IBaseService<Appointment, Long> {

    Appointment save(AppointmentRequest appointmentRequest);
    Appointment modify(AppointmentRequest appointmentRequest, Long appointmentId);
}
