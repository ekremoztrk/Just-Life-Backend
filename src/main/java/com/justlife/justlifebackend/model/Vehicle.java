package com.justlife.justlifebackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.justlife.justlifebackend.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Vehicle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JsonManagedReference
    private List<Professional> professionalList;


}
