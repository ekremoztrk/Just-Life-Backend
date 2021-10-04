package com.justlife.justlifebackend.service_tests;

import com.justlife.justlifebackend.exception.BadRequestException;
import com.justlife.justlifebackend.model.Appointment;
import com.justlife.justlifebackend.model.Professional;
import com.justlife.justlifebackend.model.Vehicle;
import com.justlife.justlifebackend.payload.response.AvailableTimeAndDuration;
import com.justlife.justlifebackend.payload.response.ProfessionalWithAvailableTimes;
import com.justlife.justlifebackend.payload.response.VehicleResponseWithProfessional;
import com.justlife.justlifebackend.payload.response.VehicleResponseWithProfessionalAvailableTimes;
import com.justlife.justlifebackend.repository.ProfessionalRepository;
import com.justlife.justlifebackend.service.impl.AppointmentService;
import com.justlife.justlifebackend.service.impl.ProfessionalService;
import com.justlife.justlifebackend.service.impl.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProfessionalServiceTest {

    @Mock
    private ProfessionalRepository professionalRepository;
    @Mock
    private AppointmentService appointmentService;
    @Mock
    private VehicleService vehicleService;
    @InjectMocks
    private ProfessionalService professionalService;


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
        professional3 = new Professional();
        professional4 = new Professional();
        professional5 = new Professional();

        vehicle = new Vehicle();
        vehicle2 = new Vehicle();
        vehicle3 = new Vehicle();

        appointment = new Appointment(LocalDateTime.of(2021,10,3,8,0),2);
        appointment2 = new Appointment(LocalDateTime.of(2021,10,3,10,30),2);
        appointment3 = new Appointment(LocalDateTime.of(2021,10,3,14,0),2);

        appointment4 = new Appointment(LocalDateTime.of(2021,10,3,10,30),2);
        appointment5 = new Appointment(LocalDateTime.of(2021,10,3,15,30),2);
        appointment6 = new Appointment(LocalDateTime.of(2021,10,3,18,0),2);

        appointment7 = new Appointment(LocalDateTime.of(2021,10,3,8,30),4);
        appointment8 = new Appointment(LocalDateTime.of(2021,10,3,15,30),2);

        professional.setId(1L);
        professional2.setId(2L);
        professional3.setId(3L);
        professional4.setId(4L);
        professional5.setId(5L);

        vehicle.setId(1L);
        vehicle2.setId(2L);
        vehicle3.setId(3L);

        appointment.setId(1L);
        appointment2.setId(2L);
        appointment3.setId(3L);
        appointment4.setId(4L);
        appointment5.setId(5L);
        appointment6.setId(6L);
        appointment7.setId(7L);
        appointment8.setId(8L);

        vehicle.setProfessionalList(Arrays.asList(professional));
        vehicle2.setProfessionalList(Arrays.asList(professional4,professional5));


        professional.setVehicle(vehicle);
        professional4.setVehicle(vehicle2);
        professional5.setVehicle(vehicle2);

        appointment.addProfessionalToAppointment(professional);
        appointment2.addProfessionalToAppointment(professional);
        appointment3.addProfessionalToAppointment(professional);

        professional.addAppointmentToProfessional(appointment);
        professional.addAppointmentToProfessional(appointment2);
        professional.addAppointmentToProfessional(appointment3);

        appointment4.addProfessionalToAppointment(professional2);
        appointment5.addProfessionalToAppointment(professional2);
        appointment6.addProfessionalToAppointment(professional2);

        professional2.addAppointmentToProfessional(appointment4);
        professional2.addAppointmentToProfessional(appointment5);
        professional2.addAppointmentToProfessional(appointment6);

        appointment7.addProfessionalToAppointment(professional3);
        appointment8.addProfessionalToAppointment(professional3);

        professional3.addAppointmentToProfessional(appointment7);
        professional3.addAppointmentToProfessional(appointment8);


    }

    @Test
    public void shouldGetAvailableAppointmentsForDate(){
        given(vehicleService.findAll()).willReturn(Arrays.asList(this.vehicle));
        given(appointmentService.findAll(any(Specification.class))).willReturn(Arrays.asList(this.appointment,this.appointment2,this.appointment3));

        professionalService.getAvailableAppointmentsForDate(LocalDateTime.now());

        List<VehicleResponseWithProfessionalAvailableTimes> expectedResult = new ArrayList<>();
        ProfessionalWithAvailableTimes professional = new ProfessionalWithAvailableTimes(this.professional,Arrays.asList(new AvailableTimeAndDuration(LocalDateTime.of(2021,10,3,16,30),2),
                                                                                                        new AvailableTimeAndDuration(LocalDateTime.of(2021,10,3,16,30),4),
                new AvailableTimeAndDuration(LocalDateTime.of(2021,10,3,17,0),2),
                new AvailableTimeAndDuration(LocalDateTime.of(2021,10,3,17,0),4),
                new AvailableTimeAndDuration(LocalDateTime.of(2021,10,3,17,30),2),
                new AvailableTimeAndDuration(LocalDateTime.of(2021,10,3,17,30),4),
                new AvailableTimeAndDuration(LocalDateTime.of(2021,10,3,18,0),2),
                new AvailableTimeAndDuration(LocalDateTime.of(2021,10,3,18,0),4),
                new AvailableTimeAndDuration(LocalDateTime.of(2021,10,3,18,30),2),
                new AvailableTimeAndDuration(LocalDateTime.of(2021,10,3,19,0),2),
                new AvailableTimeAndDuration(LocalDateTime.of(2021,10,3,19,30),2),
                new AvailableTimeAndDuration(LocalDateTime.of(2021,10,3,20,0),2)));

        expectedResult.add(new VehicleResponseWithProfessionalAvailableTimes(this.vehicle,Arrays.asList(professional)));

        List<VehicleResponseWithProfessionalAvailableTimes> testResult = professionalService.getAvailableAppointmentsForDate(LocalDateTime.of(2021,10,3,17,0));

        assertThat(expectedResult.get(0).getProfessionals().get(0).getAvailableTimeAndDurations().size()).isEqualTo(testResult.get(0).getProfessionals().get(0).getAvailableTimeAndDurations().size());

        assertThat(expectedResult.get(0).getProfessionals().get(0).getAvailableTimeAndDurations().get(0).getStartTime()).isEqualTo(testResult.get(0).getProfessionals().get(0).getAvailableTimeAndDurations().get(0).getStartTime());

    }

    @Test
    public void shouldGetAvailableProfessionalsForGivenDateDurationAndStartTime(){
        VehicleResponseWithProfessional vehicleResponseWithProfessional2 = new VehicleResponseWithProfessional(vehicle2, Arrays.asList(professional4,professional5));
        List<VehicleResponseWithProfessional> expectedResult = Arrays.asList(vehicleResponseWithProfessional2);

        given(vehicleService.findAll()).willReturn(Arrays.asList(vehicle2));
        given(appointmentService.findAll(any(Specification.class))).willReturn(new ArrayList());

        List<VehicleResponseWithProfessional> testResult = professionalService.getAvailableAppointmentsForDateStartTimeAndDuration(LocalDateTime.of(2021,10,3,16,30,0,0),
                                                                                                                            2);
        assertThat(testResult.get(0).getProfessionals().get(0).getId()).isEqualTo(expectedResult.get(0).getProfessionals().get(0).getId());

    }

    @Test
    public void shouldNotGetAvailableProfessionalsForGivenDateDurationAndStartTimeIfDurationNotValid(){
        VehicleResponseWithProfessional vehicleResponseWithProfessional2 = new VehicleResponseWithProfessional(vehicle2, Arrays.asList(professional4,professional5));
        List<VehicleResponseWithProfessional> expectedResult = Arrays.asList(vehicleResponseWithProfessional2);

        assertThrows(BadRequestException.class, () -> {
            professionalService.getAvailableAppointmentsForDateStartTimeAndDuration(LocalDateTime.of(2021,10,3,16,30,0,0),
                    3);
        });
    }
    @Test
    public void shouldNotGetAvailableProfessionalsForGivenDateDurationAndStartTimeIfStartDateNotValid(){
        VehicleResponseWithProfessional vehicleResponseWithProfessional2 = new VehicleResponseWithProfessional(vehicle2, Arrays.asList(professional4,professional5));
        List<VehicleResponseWithProfessional> expectedResult = Arrays.asList(vehicleResponseWithProfessional2);

        assertThrows(BadRequestException.class, () -> {
            professionalService.getAvailableAppointmentsForDateStartTimeAndDuration(LocalDateTime.of(2021,10,3,16,32,0,0),
                    4);
        });
    }


}
