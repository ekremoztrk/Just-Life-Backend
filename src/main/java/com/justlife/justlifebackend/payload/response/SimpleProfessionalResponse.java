package com.justlife.justlifebackend.payload.response;

import com.justlife.justlifebackend.model.Professional;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SimpleProfessionalResponse {

    private Long id;

    private String name;


    public SimpleProfessionalResponse(Professional professional){
        this.id = professional.getId();
        this.name = professional.getName();
    }
}
