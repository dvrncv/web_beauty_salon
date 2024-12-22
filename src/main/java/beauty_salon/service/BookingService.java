package beauty_salon.service;

import beauty_salon.DTO.AppointmentServiceDTO;
import beauty_salon.DTO.EmployeeDTO;
import beauty_salon.entities.AppointmentServiceEntity;
import beauty_salon.entities.GrafikEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


public interface BookingService {
    Map<EmployeeDTO, List<LocalTime>> getAvailableEmployeesAndTimes(Long serviceId, LocalDate date);

    AppointmentServiceDTO createAppointment(Long serviceId, Long employeeId, Principal principal, LocalDate date, LocalTime time);

}
