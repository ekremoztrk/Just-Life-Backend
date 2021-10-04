package com.justlife.justlifebackend.service.impl;

import com.justlife.justlifebackend.exception.BadRequestException;
import com.justlife.justlifebackend.model.Appointment;
import com.justlife.justlifebackend.model.Professional;
import com.justlife.justlifebackend.model.Vehicle;
import com.justlife.justlifebackend.payload.request.AppointmentRequest;
import com.justlife.justlifebackend.repository.AppointmentRepository;
import com.justlife.justlifebackend.service.IAppointmentService;
import com.justlife.justlifebackend.specification.AppointmentSpecification;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AppointmentService extends BaseService<Appointment, Long> implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ProfessionalService professionalService;
    private final VehicleService vehicleService;

    public AppointmentService(AppointmentRepository appointmentRepository, @Lazy ProfessionalService professionalService, VehicleService vehicleService) {
        super("Appointment Repository", appointmentRepository);
        this.appointmentRepository = appointmentRepository;
        this.professionalService = professionalService;
        this.vehicleService = vehicleService;
    }

    @Transactional
    public Appointment save(AppointmentRequest appointmentRequest){

        Appointment appointment = new Appointment();
        List<Professional> professionalList = new ArrayList<>();
        appointmentRequest.getProfessionalIds().forEach(pId -> {
            Professional professional = professionalService.findById(pId);
            if(professional!=null)
                professionalList.add(professional);
        });
        checkAppointmentDurationISValid(appointmentRequest.getDuration());
        checkNumberOfProfessionalsValid(professionalList);
        checkProfessionalIsInSameVehicle(professionalList);
        checkAppointmentTimeIsValid(appointmentRequest.getStartTime());

        for(Professional professional: professionalList){
            checkAppointmentConflict(professional, appointmentRequest.getStartTime(), appointmentRequest.getDuration());
            appointment.addProfessionalToAppointment(professional);
        }
        appointment.setDuration(appointmentRequest.getDuration());
        appointment.setStartTime(appointmentRequest.getStartTime());
        return save(appointment);
    }

    @Transactional
    public Appointment modify(AppointmentRequest appointmentRequest, Long appointmentId){

        Appointment appointment = findById(appointmentId);
        checkAppointmentTimeIsValid(appointmentRequest.getStartTime());
        checkAppointmentDurationISValid(appointmentRequest.getDuration());
        for(Professional professional: appointment.getProfessionalList()){
            checkAppointmentConflict(professional, appointmentRequest.getStartTime(), appointmentRequest.getDuration());
        }
        appointment.setDuration(appointmentRequest.getDuration());
        appointment.setStartTime(appointmentRequest.getStartTime());
        return save(appointment);
    }

    @Override
    public Appointment save(Appointment appointment){
        appointment.setEndTime(appointment.getStartTime().plusHours(appointment.getDuration()));
        return super.save(appointment);
    }

    @Override
    public <S extends Appointment> List<S> saveAll(Iterable<S> iterable) {
        for(Appointment appointment: iterable)
            save(appointment);
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    private boolean checkProfessionalIsInSameVehicle(List<Professional> professionalList){
        Vehicle vehicle = professionalList.get(0).getVehicle();
        professionalList.forEach(professional -> {
            if(!professional.getVehicle().equals(vehicle))
                throw new BadRequestException("Given professionals must be in the same vehicle.");
        });
        return true;
    }

    private boolean checkAppointmentTimeIsValid(LocalDateTime dateTime){
        if((dateTime.getMinute()!=0 && dateTime.getMinute()!=30) || dateTime.getSecond()!=0 || dateTime.getNano()!=0)
            throw new BadRequestException("Given date not valid. Example format must be -> yyyy-mm-ddThh:00:00.000 or yyyy-mm-ddThh:30:00.000.");
        return true;
    }

    private boolean checkAppointmentDurationISValid(int duration){
        if(duration != 2 && duration!=4)
            throw new BadRequestException("Duration must be 2 or 4.");
        return true;
    }

    private boolean checkAppointmentConflict(Professional professional, LocalDateTime startTime, int duration){
        Specification<Appointment> appointmentSpecification = Specification.where(null);
        appointmentSpecification = appointmentSpecification.and(AppointmentSpecification.getByProfessional(professional));
        appointmentSpecification = appointmentSpecification.and(AppointmentSpecification.getByStartTimeAndDuration(startTime,duration));
        List<Appointment> appointmentList = findAll(appointmentSpecification);
        if(appointmentList.isEmpty())
            return false;
        throw new BadRequestException("Appointment conflicts.");
    }

    private boolean checkNumberOfProfessionalsValid(List<Professional> professionalList) {
        if (professionalList.size() == 0 || professionalList.size() > 3)
            throw new BadRequestException("Number of professionals can be 1,2 or 3.");
        return true;
    }
}
