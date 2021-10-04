package com.justlife.justlifebackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.justlife.justlifebackend.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int duration;

    @ManyToMany
    @JsonBackReference
    private List<Professional> professionalList = new ArrayList<>();

    public Appointment() {
    }

    public Appointment(LocalDateTime startTime, int duration) {
        setStartTime(startTime);
        setDuration(duration);
    }

    public void addProfessionalToAppointment(Professional professional){
        professionalList.add(professional);
    }


    public LocalDateTime getEndTime(){
        return startTime.plusHours(duration);
    }

    public Appointment save(Appointment appointment){

        appointment.setEndTime(appointment.getStartTime().plusHours(duration));
        return save(appointment);

    }


}
