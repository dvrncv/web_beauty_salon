package beauty_salon.service.Impl;

import beauty_salon.DTO.AppointmentServiceDTO;
import beauty_salon.DTO.EmployeeDTO;
import beauty_salon.entities.*;
import beauty_salon.exception.EntityNotFoundException;
import beauty_salon.repository.*;
import beauty_salon.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {
    private final ServiceRepository serviceEntityRepository;
    private final EmployeeRepository employeeRepository;
    private final AppointmentServiceRepository appointmentRepository;
    private final GraficRepository grafikRepository;
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public BookingServiceImpl(ServiceRepository serviceEntityRepository,
                              EmployeeRepository employeeRepository,
                              AppointmentServiceRepository appointmentRepository,
                              GraficRepository grafikRepository, ClientRepository clientRepository, ModelMapper modelMapper) {
        this.serviceEntityRepository = serviceEntityRepository;
        this.employeeRepository = employeeRepository;
        this.appointmentRepository = appointmentRepository;
        this.grafikRepository = grafikRepository;
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Map<EmployeeDTO, List<LocalTime>> getAvailableEmployeesAndTimes(Long serviceId, LocalDate date) {
        ServiceEntity service = serviceEntityRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Услуга не найдена с id " + serviceId));

        List<EmployeeEntity> employees = employeeRepository.findAll();
        Map<EmployeeEntity, List<LocalTime>> availableSlotsMap = new HashMap<>();

        for (EmployeeEntity employee : employees) {
            Optional<GrafikEntity> grafikOpt = grafikRepository.findGrafikForEmployeeAndDate(employee.getId(), date);

            if (grafikOpt.isEmpty()) {
                continue;
            }

            GrafikEntity grafik = grafikOpt.get();
            List<AppointmentServiceEntity> existingAppointments = appointmentRepository.findByEmployeeAndDate(employee.getId(), date);
            List<LocalTime> availableSlots = availableSlotsForEmployee(grafik, existingAppointments, service.getDuration());

            if (!availableSlots.isEmpty()) {
                availableSlotsMap.put(employee, availableSlots);
            }
        }

        Map<EmployeeDTO, List<LocalTime>> employeeDto = new HashMap<>();
        for (Map.Entry<EmployeeEntity, List<LocalTime>> entry : availableSlotsMap.entrySet()) {
            EmployeeEntity employeeEntity = entry.getKey();
            List<LocalTime> slots = entry.getValue();
            EmployeeDTO employeeDTO = modelMapper.map(employeeEntity, EmployeeDTO.class);
            employeeDto.put(employeeDTO, slots);
        }
        return employeeDto;
    }

    private List<LocalTime> availableSlotsForEmployee(GrafikEntity grafik,
                                                      List<AppointmentServiceEntity> appointments,
                                                      int serviceDuration) {
        List<LocalTime> availableSlots = new ArrayList<>();
        LocalTime startTime = grafik.getStartTime();
        LocalTime endTime = grafik.getEndTime();

        appointments.sort(Comparator.comparing(AppointmentServiceEntity::getTimeStart));
        LocalTime slot = startTime;

        while (!slot.plusMinutes(serviceDuration).isAfter(endTime)) {
            final LocalTime currentSlot = slot;
            final LocalTime slotEnd = currentSlot.plusMinutes(serviceDuration);

            boolean overlaps = appointments.stream().anyMatch(a -> {
                LocalTime appointmentStart = a.getTimeStart();
                int appointmentDuration = a.getService().getDuration();
                LocalTime appointmentsEnd = appointmentStart.plusMinutes(appointmentDuration);
                return currentSlot.isBefore(appointmentsEnd) && slotEnd.isAfter(appointmentStart);
            });

            if (!overlaps) {
                availableSlots.add(currentSlot);
            }

            int interval = 10;
            slot = slot.plusMinutes(interval);
        }

        return availableSlots;
    }

    @Override
    public AppointmentServiceDTO createAppointment(Long serviceId, Long employeeId, Principal principal,
                                                   LocalDate date, LocalTime time) {
        ServiceEntity service = serviceEntityRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Услуга c таким id" + serviceId + " не найдена"));

        Optional<ClientEntity> client = clientRepository.findByEmail(principal.getName());
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Сотрудник с таким id " + employeeId + " не найден"));

        AppointmentServiceEntity appointment = new AppointmentServiceEntity(
                date,
                time,
                service.getPrice(),
                client.get(),
                service,
                employee,
                false
        );

        AppointmentServiceEntity savedAppointment = appointmentRepository.save(appointment);
        return modelMapper.map(savedAppointment, AppointmentServiceDTO.class);
    }
}
