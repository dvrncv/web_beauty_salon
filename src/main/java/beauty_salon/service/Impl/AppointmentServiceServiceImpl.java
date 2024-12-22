package beauty_salon.service.Impl;

import beauty_salon.DTO.AppointmentServiceDTO;
import beauty_salon.DTO.EmployeeDTO;
import beauty_salon.entities.*;
import beauty_salon.repository.*;
import beauty_salon.service.AppointmentServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceServiceImpl implements AppointmentServiceService {
    private final AppointmentServiceRepository appointmentServiceRepository;
    private final ModelMapper modelMapper;


    public AppointmentServiceServiceImpl(AppointmentServiceRepository appointmentServiceRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.appointmentServiceRepository = appointmentServiceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AppointmentServiceDTO> getAppointmentsForEmployee(EmployeeDTO employeeDTO, LocalDate startDate, LocalDate endDate) {
        EmployeeEntity employee = modelMapper.map(employeeDTO, EmployeeEntity.class);
        List<AppointmentServiceEntity> appointments = appointmentServiceRepository.findByEmployeeAndDateBetween(employee, startDate, endDate);
        return appointments.stream()
                .map(appointment -> {
                    AppointmentServiceDTO appointmentDTO = modelMapper.map(appointment, AppointmentServiceDTO.class);
                    return appointmentDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public Map<String, List<AppointmentServiceDTO>> getAppointmentsGroupedByMonth() {
        List<AppointmentServiceEntity> allAppointments = appointmentServiceRepository.findAll();
        List<AppointmentServiceDTO> allAppointmentsDTO = allAppointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentServiceDTO.class))
                .collect(Collectors.toList());
        return allAppointmentsDTO.stream()
                .collect(Collectors.groupingBy(
                        appointment -> appointment.getDateStart().getMonth().name()));
    }
}
