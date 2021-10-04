package com.justlife.justlifebackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.justlife.justlifebackend.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Professional extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JsonManagedReference
    private List<Appointment> appointmentList = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    private Vehicle vehicle;

    public void addAppointmentToProfessional(Appointment appointment){
        appointmentList.add(appointment);
    }



}

