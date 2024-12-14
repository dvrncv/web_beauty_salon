package beauty_salon.service;

import beauty_salon.DTO.AppointmentServiceDTO;
import beauty_salon.DTO.CategoryDTO;
import org.springframework.http.ResponseEntity;

public interface AppointmentServiceService {
    ResponseEntity<AppointmentServiceDTO> createAppointmentService(AppointmentServiceDTO appointmentServiceDTO);
    ResponseEntity<AppointmentServiceDTO> updateAppointmentService(Long id, AppointmentServiceDTO appointmentServiceDTO);
}
