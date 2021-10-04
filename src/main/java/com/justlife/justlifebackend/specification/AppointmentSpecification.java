package com.justlife.justlifebackend.specification;

import com.justlife.justlifebackend.model.Appointment;
import com.justlife.justlifebackend.model.Professional;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AppointmentSpecification {

    public static Specification<Appointment> getInDate(LocalDateTime dateTime){

        LocalDateTime startDate = fixStartTime(dateTime);
        LocalDateTime endDate = fixEndTime(dateTime);

        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.isNotNull(root.get("startTime")),
                criteriaBuilder.lessThan(root.get("startTime"), endDate),
                criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startDate));

    }
    public static Specification<Appointment> getByStartTimeAndDuration(LocalDateTime startDate, int duration){

        LocalDateTime endDate = startDate.plusHours(duration);

        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.and(
                    criteriaBuilder.isNotNull(root.get("startTime")),
                    criteriaBuilder.between(root.get("startTime"), startDate, endDate)),

                criteriaBuilder.and(
                    criteriaBuilder.isNotNull(root.get("endTime")),
                    criteriaBuilder.between(root.get("endTime"), startDate, endDate)),

                criteriaBuilder.and(
                        criteriaBuilder.isNotNull(root.get("endTime")),
                        criteriaBuilder.greaterThanOrEqualTo(root.get("endTime"), endDate),
                        criteriaBuilder.lessThanOrEqualTo(root.get("startTime"), startDate)),
                criteriaBuilder.and(
                        criteriaBuilder.isNotNull(root.get("endTime")),
                        criteriaBuilder.greaterThanOrEqualTo(root.get("endTime"), startDate),
                        criteriaBuilder.lessThanOrEqualTo(root.get("startTime"), endDate))
                );

    }

    public static Specification<Appointment> getByProfessional(Professional professional){

        return (root, query, criteriaBuilder) -> criteriaBuilder.isMember(professional, root.get("professionalList"));
    }


    private static LocalDateTime fixStartTime(LocalDateTime date) {

        return date.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    private static LocalDateTime fixEndTime(LocalDateTime date) {

        return date.withHour(23).withMinute(59).withSecond(0).withNano(0);

    }
}
