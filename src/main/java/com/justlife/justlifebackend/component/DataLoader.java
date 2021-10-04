package com.justlife.justlifebackend.component;

import com.justlife.justlifebackend.model.Appointment;
import com.justlife.justlifebackend.model.Professional;
import com.justlife.justlifebackend.model.Vehicle;
import com.justlife.justlifebackend.service.impl.AppointmentService;
import com.justlife.justlifebackend.service.impl.ProfessionalService;
import com.justlife.justlifebackend.service.impl.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataLoader implements ApplicationRunner {

    private ProfessionalService professionalService;
    private AppointmentService appointmentService;
    private VehicleService vehicleService;

    @Autowired
    public DataLoader(ProfessionalService professionalService, AppointmentService appointmentService,
                      VehicleService vehicleService) {
        this.professionalService = professionalService;
        this. appointmentService = appointmentService;
        this.vehicleService = vehicleService;
    }

    public void run(ApplicationArguments args) {
        Professional professional = new Professional();
        Professional professional2 = new Professional();
        Professional professional3 = new Professional();
        Professional professional4 = new Professional();
        Professional professional5 = new Professional();
        Professional professional6 = new Professional();
        Professional professional7 = new Professional();
        Professional professional8 = new Professional();
        Professional professional9 = new Professional();
        Professional professional10 = new Professional();
        Professional professional11 = new Professional();
        Professional professional12 = new Professional();
        Professional professional13 = new Professional();
        Professional professional14 = new Professional();
        Professional professional15 = new Professional();
        Professional professional16 = new Professional();
        Professional professional17 = new Professional();
        Professional professional18 = new Professional();
        Professional professional19 = new Professional();
        Professional professional20 = new Professional();
        Professional professional21 = new Professional();
        Professional professional22 = new Professional();
        Professional professional23 = new Professional();
        Professional professional24 = new Professional();
        Professional professional25 = new Professional();

        Appointment appointment = new Appointment(LocalDateTime.of(2021,10,3,8,0),2);
        Appointment appointment2 = new Appointment(LocalDateTime.of(2021,10,3,10,30),2);
        Appointment appointment3 = new Appointment(LocalDateTime.of(2021,10,3,14,0),2);

        Appointment appointment4 = new Appointment(LocalDateTime.of(2021,10,3,10,30),2);
        Appointment appointment5 = new Appointment(LocalDateTime.of(2021,10,3,15,30),2);
        Appointment appointment6 = new Appointment(LocalDateTime.of(2021,10,3,18,0),2);

        Appointment appointment7 = new Appointment(LocalDateTime.of(2021,10,3,8,30),4);
        Appointment appointment8 = new Appointment(LocalDateTime.of(2021,10,3,15,30),2);

        professionalService.saveAll(Arrays.asList(professional,professional2,professional3,professional4,professional5,professional6,professional7,professional8,
                                                    professional9,professional10,professional11,professional12,professional13,professional14,professional15,professional16,
                                                    professional17,professional18,professional19,professional20,professional21,professional22,professional23,professional24,professional25));

        appointmentService.saveAll(Arrays.asList(appointment,appointment2,appointment3,appointment4,appointment5,appointment6,appointment7,appointment8));


        Vehicle vehicle = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();
        Vehicle vehicle5 = new Vehicle();

        vehicle.setProfessionalList(Arrays.asList(professional,professional2,professional3,professional4,professional5));
        vehicle2.setProfessionalList(Arrays.asList(professional6,professional7,professional8,professional9,professional10));
        vehicle3.setProfessionalList(Arrays.asList(professional11,professional12,professional13,professional14,professional15));
        vehicle4.setProfessionalList(Arrays.asList(professional16,professional17,professional18,professional19,professional20));
        vehicle5.setProfessionalList(Arrays.asList(professional21,professional22,professional23,professional24,professional25));

        vehicleService.saveAll(Arrays.asList(vehicle,vehicle2,vehicle3,vehicle4,vehicle5));


        professional.setVehicle(vehicle);
        professional2.setVehicle(vehicle);
        professional3.setVehicle(vehicle);
        professional4.setVehicle(vehicle);
        professional5.setVehicle(vehicle);

        professional6.setVehicle(vehicle2);
        professional7.setVehicle(vehicle2);
        professional8.setVehicle(vehicle2);
        professional9.setVehicle(vehicle2);
        professional10.setVehicle(vehicle2);

        professional11.setVehicle(vehicle3);
        professional12.setVehicle(vehicle3);
        professional13.setVehicle(vehicle3);
        professional14.setVehicle(vehicle3);
        professional15.setVehicle(vehicle3);

        professional16.setVehicle(vehicle4);
        professional17.setVehicle(vehicle4);
        professional18.setVehicle(vehicle4);
        professional19.setVehicle(vehicle4);
        professional20.setVehicle(vehicle4);

        professional21.setVehicle(vehicle5);
        professional22.setVehicle(vehicle5);
        professional23.setVehicle(vehicle5);
        professional24.setVehicle(vehicle5);
        professional25.setVehicle(vehicle5);



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

        professionalService.saveAll(Arrays.asList(professional,professional2,professional3,professional4,professional5,professional6,professional7,professional8,
                professional9,professional10,professional11,professional12,professional13,professional14,professional15,professional16,
                professional17,professional18,professional19,professional20,professional21,professional22,professional23,professional24,professional25));

        appointmentService.saveAll(Arrays.asList(appointment,appointment2,appointment3,appointment4,appointment5,appointment6,appointment7,appointment8));

    }
}
