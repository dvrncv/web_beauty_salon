package beauty_salon.service;

import beauty_salon.DTO.AppointmentServiceDTO;
import beauty_salon.DTO.CategoryDTO;
import beauty_salon.DTO.EmployeeDTO;
import beauty_salon.entities.AppointmentServiceEntity;
import beauty_salon.entities.ClientEntity;
import beauty_salon.entities.EmployeeEntity;
import beauty_salon.entities.ServiceEntity;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface AppointmentServiceService {
    List<AppointmentServiceDTO> getAppointmentsForEmployee(EmployeeDTO employeeDTO, LocalDate startDate, LocalDate endDate);

    Map<String, List<AppointmentServiceDTO>> getAppointmentsGroupedByMonth();
}
