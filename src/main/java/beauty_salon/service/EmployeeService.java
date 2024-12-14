package beauty_salon.service;

import beauty_salon.DTO.EmployeeDTO;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getTopEmployees (LocalDate startDate, LocalDate endDate);
    List<EmployeeDTO> findAvailableEmployees(LocalDate dateStart, LocalTime timeStart);
    List<EmployeeDTO> getAllEmployee();
}
