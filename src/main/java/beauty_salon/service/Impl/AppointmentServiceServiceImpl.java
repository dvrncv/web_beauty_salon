package beauty_salon.service.Impl;

import beauty_salon.DTO.AppointmentServiceDTO;
import beauty_salon.entities.*;
import beauty_salon.exception.EntityNotFoundException;
import beauty_salon.repository.AppointmentServiceRepository;
import beauty_salon.repository.ClientRepository;
import beauty_salon.repository.EmployeeRepository;
import beauty_salon.repository.ServiceRepository;
import beauty_salon.service.AppointmentServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

public class AppointmentServiceServiceImpl implements AppointmentServiceService {
    private final AppointmentServiceRepository appointmentServiceRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;

    public AppointmentServiceServiceImpl(AppointmentServiceRepository appointmentServiceRepository, EmployeeRepository employeeRepository, ClientRepository clientRepository, ServiceRepository serviceRepository, ModelMapper modelMapper) {
        this.appointmentServiceRepository = appointmentServiceRepository;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public ResponseEntity<AppointmentServiceDTO> createAppointmentService (AppointmentServiceDTO appointmentServiceDTO) {
        ClientEntity client = clientRepository.findById(appointmentServiceDTO.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        ServiceEntity service = serviceRepository.findById(appointmentServiceDTO.getServiceId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
        EmployeeEntity employee = employeeRepository.findById(appointmentServiceDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        AppointmentServiceEntity appointmentServiceEntity = modelMapper.map(appointmentServiceDTO, AppointmentServiceEntity.class);
        appointmentServiceEntity.setClient(client);
        appointmentServiceEntity.setService(service);
        appointmentServiceEntity.setEmployee(employee);
        AppointmentServiceEntity savedAppointment = appointmentServiceRepository.save(appointmentServiceEntity);
        AppointmentServiceDTO savedDTO = modelMapper.map(savedAppointment, AppointmentServiceDTO.class);
        return ResponseEntity.ok().body(savedDTO);
    }
    @Override
    public ResponseEntity<AppointmentServiceDTO> updateAppointmentService(Long id, AppointmentServiceDTO appointmentServiceDTO) {
        AppointmentServiceEntity appointmentServiceEntity = appointmentServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AppointmentService with ID " + id + " not found"));
        if (appointmentServiceDTO.getDateStart() != null) {
            appointmentServiceEntity.setDateStart(appointmentServiceDTO.getDateStart());
        }
        if (appointmentServiceDTO.getTimeStart() != null) {
            appointmentServiceEntity.setTimeStart(appointmentServiceDTO.getTimeStart());
        }
        if (appointmentServiceDTO.getTotalCost() != null) {
            appointmentServiceEntity.setTotalCost(appointmentServiceDTO.getTotalCost());
        }
        if (appointmentServiceDTO.getClientId() != null) {
            ClientEntity clientEntity = clientRepository.findById(appointmentServiceDTO.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("Client not found"));
            appointmentServiceEntity.setClient(clientEntity);
        }
        if (appointmentServiceDTO.getServiceId() != null) {
            ServiceEntity serviceEntity = serviceRepository.findById(appointmentServiceDTO.getServiceId())
                    .orElseThrow(() -> new EntityNotFoundException("Service not found"));
            appointmentServiceEntity.setService(serviceEntity);
        }
        if (appointmentServiceDTO.getEmployeeId() != null) {
            EmployeeEntity employeeEntity = employeeRepository.findById(appointmentServiceDTO.getEmployeeId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
            appointmentServiceEntity.setEmployee(employeeEntity);
        }
        AppointmentServiceEntity updatedAppointment = appointmentServiceRepository.save(appointmentServiceEntity);
        AppointmentServiceDTO updatedDTO = modelMapper.map(updatedAppointment, AppointmentServiceDTO.class);
        return ResponseEntity.ok().body(updatedDTO);
    }
}
