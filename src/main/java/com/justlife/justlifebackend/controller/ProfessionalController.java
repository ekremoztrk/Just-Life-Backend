package com.justlife.justlifebackend.controller;

import com.justlife.justlifebackend.exception.BadRequestException;
import com.justlife.justlifebackend.model.Professional;
import com.justlife.justlifebackend.payload.response.VehicleResponseWithProfessional;
import com.justlife.justlifebackend.payload.response.VehicleResponseWithProfessionalAvailableTimes;
import com.justlife.justlifebackend.service.impl.ProfessionalService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/professional")
public class ProfessionalController {

    private final ProfessionalService professionalService;

    public ProfessionalController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    @GetMapping("/available-professionals-for-date")
    public List<VehicleResponseWithProfessionalAvailableTimes> getAvailableProfessionalForDate(@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date){

        List<VehicleResponseWithProfessionalAvailableTimes> vehicleResponsWithProfessionalAvailableTimes = professionalService.getAvailableAppointmentsForDate(date);
        return vehicleResponsWithProfessionalAvailableTimes;
    }

    @GetMapping("/available-professionals-for-date-and-duration")
    public ResponseEntity<?> getAvailableProfessionalForDateAndDuration(@RequestParam(name = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                         @RequestParam(name = "duration") int duration){

        try {
            List<VehicleResponseWithProfessional> vehicleResponseWithProfessionals = professionalService.getAvailableAppointmentsForDateStartTimeAndDuration(startDate,duration);
            return ResponseEntity.ok(vehicleResponseWithProfessionals);
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
