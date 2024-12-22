package beauty_salon.service.Impl;

import beauty_salon.DTO.EmployeeDTO;
import beauty_salon.entities.*;
import beauty_salon.exception.EntityNotFoundException;
import beauty_salon.repository.EmployeeRepository;
import beauty_salon.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<EmployeeDTO> getTopEmployees(LocalDate startDate, LocalDate endDate) {
        Pageable pageable = PageRequest.of(0, 5);
        List<Object[]> results = employeeRepository.findTopEmployees(startDate, endDate, pageable);
        return results.stream()
                .map(result -> {
                    EmployeeDTO employeeDTO = modelMapper.map(result, EmployeeDTO.class);
                    employeeDTO.setId((Long) result[0]);
                    employeeDTO.setName((String) result[1]);
                    employeeDTO.setSurname((String) result[2]);
                    return employeeDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeByEmail(String email) {
        EmployeeEntity employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Сотрудник с таким email " + email + " не найден"));
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        List<EmployeeEntity> employee = employeeRepository.findAll();
        return employee.stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }
}
