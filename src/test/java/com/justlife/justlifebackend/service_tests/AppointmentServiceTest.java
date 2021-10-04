package com.justlife.justlifebackend.service_tests;


import com.justlife.justlifebackend.exception.BadRequestException;
import com.justlife.justlifebackend.model.Appointment;
import com.justlife.justlifebackend.model.Professional;
import com.justlife.justlifebackend.model.Vehicle;
import com.justlife.justlifebackend.payload.request.AppointmentRequest;
import com.justlife.justlifebackend.repository.AppointmentRepository;
import com.justlife.justlifebackend.service.impl.AppointmentService;
import com.justlife.justlifebackend.service.impl.ProfessionalService;
import com.justlife.justlifebackend.service.impl.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ProfessionalService professionalService;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private AppointmentService appointmentService;


    Professional professional = new Professional();
    Professional professional2 = new Professional();
    Professional professional3 = new Professional();
    Professional professional4 = new Professional();
    Professional professional5 = new Professional();

    Appointment appointment;
    Appointment appointment2;
    Appointment appointment3;

    Appointment appointment4;
    Appointment appointment5;
    Appointment appointment6;

    Appointment appointment7;
    Appointment appointment8;

    Vehicle vehicle;
    Vehicle vehicle2;
    Vehicle vehicle3;

    @BeforeEach
    public void setTestData(){
        professional = new Professional();
        professional2 = new Professional();

        vehicle = new Vehicle();
        vehicle2 = new Vehicle();

        appointment = new Appointment(LocalDateTime.of(2021,10,3,8,0),2);
        appointment2 = new Appointment(LocalDateTime.of(2021,10,3,10,30),2);
        appointment3 = new Appointment(LocalDateTime.of(2021,10,3,14,0),2);

        professional.setId(1L);
        professional2.setId(2L);
        vehicle.setId(1L);
        vehicle2.setId(2L);

        appointment.setId(1L);
        appointment2.setId(2L);
        appointment3.setId(3L);


        vehicle.setProfessionalList(Arrays.asList(professional,professional2));
        vehicle2.setProfessionalList(Arrays.asList(professional3));

        professional.setVehicle(vehicle);
        professional2.setVehicle(vehicle);
        professional3.setVehicle(vehicle2);

        appointment.addProfessionalToAppointment(professional);
        appointment2.addProfessionalToAppointment(professional);
        appointment3.addProfessionalToAppointment(professional);

        professional.addAppointmentToProfessional(appointment);
        professional.addAppointmentToProfessional(appointment2);
        professional.addAppointmentToProfessional(appointment3);


    }

    @Test
    public void shouldSaveAppointment(){

        LocalDateTime startTime = LocalDateTime.of(2021,10,3,16,30);
        Date startDate = java.sql.Timestamp.valueOf(startTime);

        AppointmentRequest appointmentRequest = new AppointmentRequest(startDate,2, Arrays.asList(1L,2L));
        given(professionalService.findById(1L)).willReturn(professional);
        given(professionalService.findById(2L)).willReturn(professional2);

        Appointment expectedAppointment = new Appointment();
        expectedAppointment.setDuration(2);
        expectedAppointment.setStartTime(startTime);
        expectedAppointment.setEndTime(startTime.plusHours(2));
        expectedAppointment.setProfessionalList(Arrays.asList(professional,professional2));

        given(appointmentRepository.save(any(Appointment.class))).willReturn(expectedAppointment);
        Appointment testResult = appointmentService.save(appointmentRequest);

        assertThat(testResult).isEqualTo(expectedAppointment);

    }

    @Test
    public void shouldNotSaveIfDurationNotValidNumber(){
        LocalDateTime startTime = LocalDateTime.of(2021,10,3,16,30);
        Date startDate = java.sql.Timestamp.valueOf(startTime);

        AppointmentRequest appointmentRequest = new AppointmentRequest(startDate,5, Arrays.asList(1L,2L));
        given(professionalService.findById(1L)).willReturn(professional);
        given(professionalService.findById(2L)).willReturn(professional2);

        assertThrows(BadRequestException.class, () -> {
            appointmentService.save(appointmentRequest);
        });
    }

    @Test
    public void shouldNotSaveIfProfessionalsNotValidNumber(){
        LocalDateTime startTime = LocalDateTime.of(2021,10,3,16,30);
        Date startDate = java.sql.Timestamp.valueOf(startTime);

        AppointmentRequest appointmentRequest = new AppointmentRequest(startDate,2, Arrays.asList(1L,2L,3L,4L));
        given(professionalService.findById(1L)).willReturn(professional);
        given(professionalService.findById(2L)).willReturn(professional2);
        given(professionalService.findById(3L)).willReturn(professional3);
        given(professionalService.findById(4L)).willReturn(professional4);

        assertThrows(BadRequestException.class, () -> {
            appointmentService.save(appointmentRequest);
        });
    }

    @Test
    public void shouldNotSaveIfProfessionalsNoInSameVehicle(){
        LocalDateTime startTime = LocalDateTime.of(2021,10,3,16,30);
        Date startDate = java.sql.Timestamp.valueOf(startTime);

        AppointmentRequest appointmentRequest = new AppointmentRequest(startDate,2, Arrays.asList(1L,3L));
        given(professionalService.findById(1L)).willReturn(professional);
        given(professionalService.findById(3L)).willReturn(professional3);

        assertThrows(BadRequestException.class, () -> {
            appointmentService.save(appointmentRequest);
        });
    }

    @Test
    public void shouldNotSaveIfStartTimeNotValid(){
        LocalDateTime startTime = LocalDateTime.of(2021,10,3,16,01);
        Date startDate = java.sql.Timestamp.valueOf(startTime);

        AppointmentRequest appointmentRequest = new AppointmentRequest(startDate,2, Arrays.asList(1L,2L));
        given(professionalService.findById(1L)).willReturn(professional2);
        given(professionalService.findById(2L)).willReturn(professional2);

        assertThrows(BadRequestException.class, () -> {
            appointmentService.save(appointmentRequest);
        });
    }

    @Test
    public void shouldNotSaveIfAppointmentConflictsWithAnotherAppointment(){
        LocalDateTime startTime = LocalDateTime.of(2021,10,3,11,00);
        Date startDate = java.sql.Timestamp.valueOf(startTime);

        AppointmentRequest appointmentRequest = new AppointmentRequest(startDate,2, Arrays.asList(1L,2L));
        given(professionalService.findById(1L)).willReturn(professional2);
        given(professionalService.findById(2L)).willReturn(professional2);
        given(appointmentService.findAll(any(Specification.class))).willReturn(Arrays.asList(appointment,appointment2));
        assertThrows(BadRequestException.class, () -> {
            appointmentService.save(appointmentRequest);
        });
    }

    @Test
    public void shouldModifyAppointment(){

        LocalDateTime startTime = LocalDateTime.of(2021,10,3,17,30);
        Date startDate = java.sql.Timestamp.valueOf(startTime);

        AppointmentRequest appointmentRequest = new AppointmentRequest(startDate,2, Arrays.asList(1L,2L));
        given(appointmentRepository.findById(1L)).willReturn(java.util.Optional.ofNullable(appointment));

        Appointment expectedAppointment = new Appointment();
        expectedAppointment.setDuration(2);
        expectedAppointment.setStartTime(startTime);
        expectedAppointment.setEndTime(startTime.plusHours(2));
        expectedAppointment.setProfessionalList(Arrays.asList(professional,professional2));

        given(appointmentRepository.save(any(Appointment.class))).willReturn(expectedAppointment);
        Appointment testResult = appointmentService.modify(appointmentRequest, 1L);

        assertThat(testResult).isEqualTo(expectedAppointment);

    }

    @Test
    public void shouldNotModifyIfDurationNotValidNumber(){
        LocalDateTime startTime = LocalDateTime.of(2021,10,3,16,30);
        Date startDate = java.sql.Timestamp.valueOf(startTime);

        AppointmentRequest appointmentRequest = new AppointmentRequest(startDate,5, Arrays.asList(1L,2L));
        given(appointmentRepository.findById(1L)).willReturn(java.util.Optional.ofNullable(appointment));

        assertThrows(BadRequestException.class, () -> {
            appointmentService.modify(appointmentRequest, 1L);
        });
    }

    @Test
    public void shouldNotModifyIfStartTimeNotValid(){
        LocalDateTime startTime = LocalDateTime.of(2021,10,3,16,01);
        Date startDate = java.sql.Timestamp.valueOf(startTime);

        AppointmentRequest appointmentRequest = new AppointmentRequest(startDate,2, Arrays.asList(1L,2L));
        given(appointmentRepository.findById(1L)).willReturn(java.util.Optional.ofNullable(appointment));
        assertThrows(BadRequestException.class, () -> {
            appointmentService.modify(appointmentRequest,1L);
        });
    }

    @Test
    public void shouldNotModifyIfAppointmentConflictsWithAnotherAppointment(){
        LocalDateTime startTime = LocalDateTime.of(2021,10,3,11,00);
        Date startDate = java.sql.Timestamp.valueOf(startTime);

        AppointmentRequest appointmentRequest = new AppointmentRequest(startDate,2, Arrays.asList(1L,2L));
        given(appointmentRepository.findById(2L)).willReturn(java.util.Optional.ofNullable(appointment2));
        given(appointmentService.findAll(any(Specification.class))).willReturn(Arrays.asList(appointment,appointment2));
        assertThrows(BadRequestException.class, () -> {
            appointmentService.modify(appointmentRequest,2L);
        });
    }

    @Test
    public void shouldSetEndTimeWhileSaving(){
        LocalDateTime startTime = LocalDateTime.of(2021,10,3,11,00);
        Date startDate = java.sql.Timestamp.valueOf(startTime);

        AppointmentRequest appointmentRequest = new AppointmentRequest(startDate,2, Arrays.asList(1L,2L));
        Appointment expectedResult = new Appointment();
        expectedResult.setProfessionalList(Arrays.asList(professional,professional2));
        expectedResult.setStartTime(startTime);
        expectedResult.setEndTime(startTime.plusHours(2));

        Appointment appointment = new Appointment();
        appointment.setProfessionalList(Arrays.asList(professional,professional2));
        appointment.setStartTime(startTime);

        given(appointmentRepository.save(any(Appointment.class))).willReturn(expectedResult);


        Appointment testResult = appointmentService.save(appointment);

        assertThat(expectedResult).isEqualTo(testResult);
    }

}
