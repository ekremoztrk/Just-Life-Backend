package com.justlife.justlifebackend.repository;

import com.justlife.justlifebackend.model.Appointment;
import com.justlife.justlifebackend.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends BaseRepository<Appointment, Long> {
}
