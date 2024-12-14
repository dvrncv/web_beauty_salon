package beauty_salon.service;

import beauty_salon.DTO.CategoryDTO;
import beauty_salon.DTO.EmployeeDTO;
import beauty_salon.DTO.ServiceDTO;
import form.SearchInputModel;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface ServiceService {
    ResponseEntity<ServiceDTO> createService(ServiceDTO serviceDTO);
    ResponseEntity<ServiceDTO> findById (Long id);
    ResponseEntity<ServiceDTO> updateService(Long id, ServiceDTO serviceDTO);
    ResponseEntity<ServiceDTO> deleteService(Long id);
    List<ServiceDTO> getAllServices();
    List<ServiceDTO> getTopServices (LocalDate startDate, LocalDate endDate);
    List<ServiceDTO> getServicesByCategory(Long categoryId);
    Page<ServiceDTO> getServices(String searchTerm, Long categoryId, int page, int size);
    ServiceDTO getServiceById(Long id);
    ServiceDTO getServiceDetails(Long id);
}
