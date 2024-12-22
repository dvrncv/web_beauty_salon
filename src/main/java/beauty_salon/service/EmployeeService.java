package beauty_salon.service;

import beauty_salon.DTO.EmployeeDTO;


import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getTopEmployees(LocalDate startDate, LocalDate endDate);

    List<EmployeeDTO> getAllEmployee();

    EmployeeDTO getEmployeeByEmail(String email);
}
