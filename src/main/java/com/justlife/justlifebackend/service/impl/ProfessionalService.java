package com.justlife.justlifebackend.service.impl;

import com.justlife.justlifebackend.exception.BadRequestException;
import com.justlife.justlifebackend.model.Appointment;
import com.justlife.justlifebackend.model.Professional;
import com.justlife.justlifebackend.model.Vehicle;
import com.justlife.justlifebackend.payload.response.AvailableTimeAndDuration;
import com.justlife.justlifebackend.payload.response.ProfessionalWithAvailableTimes;
import com.justlife.justlifebackend.payload.response.VehicleResponseWithProfessional;
import com.justlife.justlifebackend.payload.response.VehicleResponseWithProfessionalAvailableTimes;
import com.justlife.justlifebackend.repository.ProfessionalRepository;
import com.justlife.justlifebackend.service.IProfessionalService;
import com.justlife.justlifebackend.specification.AppointmentSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProfessionalService extends BaseService<Professional, Long> implements IProfessionalService {


    private final ProfessionalRepository professionalRepository;
    private final AppointmentService appointmentService;
    private final VehicleService vehicleService;

    public ProfessionalService(ProfessionalRepository professionalRepository, AppointmentService appointmentService,
                               VehicleService vehicleService) {
        super("Professional Repository", professionalRepository);
        this.professionalRepository = professionalRepository;
        this.appointmentService = appointmentService;
        this.vehicleService = vehicleService;
    }

    public List<VehicleResponseWithProfessionalAvailableTimes> getAvailableAppointmentsForDate(LocalDateTime date){

        List<Vehicle> vehicles = vehicleService.findAll();
        List<VehicleResponseWithProfessionalAvailableTimes> responses = new ArrayList<>();
        for(Vehicle vehicle: vehicles){
            List<ProfessionalWithAvailableTimes> professionalsWithAvailableTimes = new ArrayList<>();
            if(!vehicle.getProfessionalList().isEmpty()){
                for(Professional professional: vehicle.getProfessionalList()){

                    Map<LocalDateTime, Boolean> map = initializeAppointmentMap(date);
                    List<Appointment> appointmentList = getProfessionalAppointmentsByDate(professional,date);
                    fillMapWithAppointments(appointmentList,map);
                    Map<LocalDateTime, Boolean> filteredMap = filterByValue(map, value -> value == false);

                    List<AvailableTimeAndDuration> availableTimeAndDurations = getAvailableTimeAndDuration(new ArrayList<>(new TreeMap<>(filteredMap).keySet()));
                    ProfessionalWithAvailableTimes professionalWithAvailableTimes = new ProfessionalWithAvailableTimes(professional, availableTimeAndDurations);
                    professionalsWithAvailableTimes.add(professionalWithAvailableTimes);

                }
                VehicleResponseWithProfessionalAvailableTimes response = new VehicleResponseWithProfessionalAvailableTimes(vehicle, professionalsWithAvailableTimes);
                responses.add(response);
            }
        }

        return responses;
    }

    public List<VehicleResponseWithProfessional> getAvailableAppointmentsForDateStartTimeAndDuration(LocalDateTime startTime, int duration){

        checkAppointmentDurationISValid(duration);
        checkAppointmentTimeIsValid(startTime);
        List<Vehicle> vehicles = vehicleService.findAll();
        List<VehicleResponseWithProfessional> vehicleResponseWithProfessionals = new ArrayList<>();

        for(Vehicle vehicle: vehicles){
            List<Professional> availableProfessionals = new ArrayList<>();
            for(Professional professional: vehicle.getProfessionalList()){

                Specification<Appointment> appointmentSpecification = Specification.where(null);
                appointmentSpecification = appointmentSpecification.and(AppointmentSpecification.getByProfessional(professional));
                appointmentSpecification = appointmentSpecification.and(AppointmentSpecification.getByStartTimeAndDuration(startTime,duration));
                List<Appointment> appointmentList = appointmentService.findAll(appointmentSpecification);

                if(appointmentList.isEmpty()){
                    availableProfessionals.add(professional);
                }
            }
            if(!availableProfessionals.isEmpty()){
                VehicleResponseWithProfessional vehicleResponseWithProfessional = new VehicleResponseWithProfessional(vehicle, availableProfessionals);
                vehicleResponseWithProfessionals.add(vehicleResponseWithProfessional);
            }

        }

        return vehicleResponseWithProfessionals;
    }

    static <K, V> Map<K, V> filterByValue(Map<K, V> map, Predicate<V> predicate) {
        return map.entrySet()
                .stream()
                .filter(entry -> predicate.test(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<LocalDateTime, Boolean> fillMapWithAppointments(List<Appointment> appointmentList, Map<LocalDateTime,Boolean> appointmentMap){
        for(Appointment appointment: appointmentList){
            fillAppointmentMap(appointment,appointmentMap);
        }
        return appointmentMap;
    }

    private Map<LocalDateTime, Boolean> fillAppointmentMap(Appointment appointment, Map<LocalDateTime,Boolean> appointmentMap){

        for(int i = 0; i<appointment.getDuration()*2 + 1;i++){ // +1 because at least 30 minutes break necessary
            LocalDateTime period = appointment.getStartTime().plusMinutes(i*30);
            appointmentMap.put(period,true);
        }
        return appointmentMap;
    }

    private Map<LocalDateTime, Boolean> initializeAppointmentMap(LocalDateTime date){
        Map<LocalDateTime, Boolean> appointmentMap = new TreeMap<>();
        for(int i = 16; i<=44; i++){
            if(i%2==0){
                LocalDateTime key = date.withHour(i/2);
                key = key.withMinute(0).withSecond(0).withNano(0);
                appointmentMap.put(key,false);
            }
            else{
                LocalDateTime key = date.withHour(i/2);
                key = key.withMinute(30).withSecond(0).withNano(0);
                appointmentMap.put(key,false);
            }
        }
        return appointmentMap;

    }

    private List<Appointment> getProfessionalAppointmentsByDate(Professional professional, LocalDateTime date){
        Specification<Appointment> appointmentSpecification = Specification.where(null);
        appointmentSpecification = appointmentSpecification.and(AppointmentSpecification.getByProfessional(professional));
        appointmentSpecification = appointmentSpecification.and(AppointmentSpecification.getInDate(date));
        List<Appointment> appointmentList = appointmentService.findAll(appointmentSpecification);
        return appointmentList;
    }

    private List<AvailableTimeAndDuration> getAvailableTimeAndDuration(List<LocalDateTime> dates){

        List<AvailableTimeAndDuration> availableTimeAndDurations = new ArrayList<>();
        for(int i =0; i<dates.size()-4;i++){

            LocalDateTime date = dates.get(i);
            LocalDateTime date2 = dates.get(i+1);
            LocalDateTime date3 = dates.get(i+2);
            LocalDateTime date4 = dates.get(i+3);
            LocalDateTime date5 = dates.get(i+4);

            if(isHoursConsecutiveBy30Minutes(new ArrayList<>(Arrays.asList(date,date2,date3,date4,date5)))){
                availableTimeAndDurations.add(new AvailableTimeAndDuration(date, 2));
            }
            if(i < dates.size()-8){
                LocalDateTime date6 = dates.get(i+5);
                LocalDateTime date7 = dates.get(i+6);
                LocalDateTime date8 = dates.get(i+7);
                LocalDateTime date9 = dates.get(i+8);

                if(isHoursConsecutiveBy30Minutes(new ArrayList<>(Arrays.asList(date,date2,date3,date4,date5,
                        date6,date7,date8,date9)))){
                    availableTimeAndDurations.add(new AvailableTimeAndDuration(date, 4));
                }
            }
        }
        return availableTimeAndDurations;

    }

    private boolean isHoursConsecutiveBy30Minutes(List<LocalDateTime> dateTimes){

        for(int i = 0; i < dateTimes.size()-1; i++){

            if(!dateTimes.get(i).equals(dateTimes.get(i+1).minusMinutes(30)))
                return false;
        }
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
}
