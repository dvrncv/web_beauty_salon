package beauty_salon.service.Impl;

import beauty_salon.DTO.EmployeeDTO;
import beauty_salon.DTO.ServiceDTO;
import beauty_salon.entities.EmployeeEntity;
import beauty_salon.entities.GrafikEntity;
import beauty_salon.entities.ServiceEntity;
import beauty_salon.repository.AppointmentServiceRepository;
import beauty_salon.repository.EmployeeRepository;
import beauty_salon.repository.GraficRepository;
import beauty_salon.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AppointmentServiceRepository appointmentServiceRepository;
    private final GraficRepository graficRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, AppointmentServiceRepository appointmentServiceRepository, GraficRepository graficRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.appointmentServiceRepository = appointmentServiceRepository;
        this.graficRepository = graficRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<EmployeeDTO> getTopEmployees(LocalDate startDate, LocalDate endDate) {
        Pageable pageable = PageRequest.of(0, 5);
        List<Object[]> results = employeeRepository.findTopEmployees(startDate, endDate, pageable);

        return results.stream()
                .map(result -> {
                    EmployeeDTO employeeDTO = modelMapper.map(result,EmployeeDTO.class);
                    employeeDTO.setId((Long) result[0]);
                    employeeDTO.setName((String) result[1]);
                    employeeDTO.setSurname((String) result[2]);
                    employeeDTO.setClientCount(((Number) result[3]).intValue());
                    return employeeDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> findAvailableEmployees(LocalDate dateStart, LocalTime timeStart) {
        List<EmployeeEntity> allEmployees = employeeRepository.findAll();
        return allEmployees.stream()
                .filter(employee -> isEmployeeAvailable(employee, dateStart, timeStart))
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }


    private boolean isEmployeeAvailable(EmployeeEntity employee, LocalDate dateStart, LocalTime timeStart) {
        boolean isWorkingOnDate = graficRepository.findByEmployeeAndDayOfWeek(employee, dateStart.getDayOfWeek().toString())
                .stream()
                .anyMatch(grafik -> isTimeInRange(grafik, timeStart));

        if (!isWorkingOnDate) {
            return false;
        }

        boolean isBooked = appointmentServiceRepository.findByEmployeeAndDateStartAndTimeStart(employee, dateStart, timeStart).isPresent();

        return !isBooked;
    }


    private boolean isTimeInRange(GrafikEntity grafik, LocalTime time) {
        return !time.isBefore(grafik.getStartTime()) && !time.isAfter(grafik.getEndTime());
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        List<EmployeeEntity> employee = employeeRepository.findAll();
        return employee.stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }
}
