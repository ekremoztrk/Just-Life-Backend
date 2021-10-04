package com.justlife.justlifebackend.payload.response;

import com.justlife.justlifebackend.model.Professional;
import lombok.Getter;


import java.time.LocalDateTime;
import java.util.*;

@Getter
public class ProfessionalWithAvailableTimes {

    SimpleProfessionalResponse professional;
    List<AvailableTimeAndDuration> availableTimeAndDurations;

    public ProfessionalWithAvailableTimes(Professional professional, List<AvailableTimeAndDuration> availableTimeAndDurations) {
        this.professional = new SimpleProfessionalResponse(professional);
        this.availableTimeAndDurations = availableTimeAndDurations;

    }



}
