package com.justlife.justlifebackend.controller;

import com.justlife.justlifebackend.exception.BadRequestException;
import com.justlife.justlifebackend.payload.request.AppointmentRequest;
import com.justlife.justlifebackend.payload.response.VehicleResponseWithProfessional;
import com.justlife.justlifebackend.payload.response.VehicleResponseWithProfessionalAvailableTimes;
import com.justlife.justlifebackend.service.impl.AppointmentService;
import com.justlife.justlifebackend.service.impl.ProfessionalService;
import io.swagger.models.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ProfessionalService professionalService;

    public AppointmentController(AppointmentService appointmentService, ProfessionalService professionalService) {
        this.appointmentService = appointmentService;
        this.professionalService = professionalService;
    }


    @PostMapping("")
    public ResponseEntity saveBooking(@RequestBody AppointmentRequest appointmentRequest){
        try {
            appointmentService.save(appointmentRequest);
            return ResponseEntity.ok("Booking saved successfully");
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity modifyBooking(@PathVariable(name = "id") Long id, @RequestBody AppointmentRequest appointmentRequest){
        try {
            appointmentService.modify(appointmentRequest,id);
            return ResponseEntity.ok("Booking modified successfully");
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
