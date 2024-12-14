package beauty_salon.service.Impl;

import beauty_salon.DTO.ServiceDTO;
import beauty_salon.entities.CategoryEntity;
import beauty_salon.entities.ServiceEntity;
import beauty_salon.exception.EntityNotFoundException;
import beauty_salon.repository.CategoryRepository;
import beauty_salon.repository.ServiceRepository;
import beauty_salon.service.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ServiceServiceImpl(ServiceRepository serviceRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.serviceRepository = serviceRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<ServiceDTO> createService(ServiceDTO serviceDTO) {
        ServiceEntity serviceEntity = modelMapper.map(serviceDTO, ServiceEntity.class);
        CategoryEntity categoryEntity = categoryRepository.findById(serviceDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        serviceEntity.setCategory(categoryEntity);
        ServiceEntity savedServiceEntity = serviceRepository.save(serviceEntity);
        ServiceDTO savedServiceDTO = modelMapper.map(savedServiceEntity, ServiceDTO.class);
        return ResponseEntity.ok(savedServiceDTO);
    }

    @Override
    public ResponseEntity<ServiceDTO> updateService(Long id, ServiceDTO serviceDTO) {
        ServiceEntity serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service with ID " + id + " not found"));
        if (serviceDTO.getName() != null) {
            serviceEntity.setName(serviceDTO.getName());
        }
        if (serviceDTO.getDescription() != null) {
            serviceEntity.setDescription(serviceDTO.getDescription());
        }
        if (serviceDTO.getPrice() != null) {
            serviceEntity.setPrice(serviceDTO.getPrice());
        }
        if (serviceDTO.getDuration() != null) {
            serviceEntity.setDuration(serviceDTO.getDuration());
        }
        if (serviceDTO.getCategoryId() != null) {
            CategoryEntity categoryEntity = categoryRepository.findById(serviceDTO.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            serviceEntity.setCategory(categoryEntity);
        }
        ServiceEntity updatedServiceEntity = serviceRepository.save(serviceEntity);
        ServiceDTO updatedServiceDTO = modelMapper.map(updatedServiceEntity, ServiceDTO.class);
        return ResponseEntity.ok(updatedServiceDTO);
    }

    @Override
    public List<ServiceDTO> getAllServices() {
        List<ServiceEntity> service = serviceRepository.findAll();
        return service.stream()
                .map(serviceEntity -> modelMapper.map(serviceEntity, ServiceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ServiceDTO> findById(Long id) {
        ServiceEntity serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service with ID " + id + " not found"));
        ServiceDTO serviceDTO = modelMapper.map(serviceEntity, ServiceDTO.class);
        return ResponseEntity.ok().body(serviceDTO);
    }

    @Override
    public List<ServiceDTO> getTopServices(LocalDate startDate, LocalDate endDate) {
        Pageable pageable = PageRequest.of(0, 5);
        List<Object[]> results = serviceRepository.findTopServices(startDate, endDate, pageable);

        return results.stream()
                .map(result -> {
                    ServiceDTO serviceDTO = modelMapper.map(result, ServiceDTO.class);
                    serviceDTO.setId((Long) result[0]);
                    serviceDTO.setName((String) result[1]);
                    serviceDTO.setDescription((String) result[2]);
                    serviceDTO.setPrice((Integer) result[3]);
                    serviceDTO.setDuration((Integer) result[4]);
                    serviceDTO.setCategoryId((Long) result[5]);
                    serviceDTO.setCategoryName((String) result[6]);
                    return serviceDTO;
                })
                .collect(Collectors.toList());
    }


    //    @Override
//    public ResponseEntity<ServiceDTO> deleteService(Long id) {
//        ServiceEntity serviceEntity = serviceRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Service with ID " + id + " not found"));
//        serviceEntity.setDeleted(true);
//        serviceRepository.save(serviceEntity);
//        return ResponseEntity.noContent().build();

    @Override
    public ResponseEntity<ServiceDTO> deleteService(Long id) {
        serviceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public List<ServiceDTO> getServicesByCategory(Long categoryId) {
        List<ServiceEntity> services = serviceRepository.findByCategoryId(categoryId);
        return services.stream()
                .map(serviceEntity -> modelMapper.map(serviceEntity, ServiceDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public Page<ServiceDTO> getServices(String searchTerm, Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("name"));
        if (searchTerm != null && categoryId != null) {
            Page<ServiceEntity> servicesPage = serviceRepository.findByNameStartsWithIgnoreCaseAndCategoryId(searchTerm, categoryId, pageable);
            return servicesPage.map(service -> modelMapper.map(service, ServiceDTO.class));
        }
        if (searchTerm != null) {
            Page<ServiceEntity> servicesPage = serviceRepository.findByNameStartsWithIgnoreCase(searchTerm, pageable);
            return servicesPage.map(service -> modelMapper.map(service, ServiceDTO.class));
        }
        if (categoryId != null) {
            Page<ServiceEntity> servicesPage = serviceRepository.findByCategoryId(categoryId, pageable);
            return servicesPage.map(service -> modelMapper.map(service, ServiceDTO.class));
        }
        Page<ServiceEntity> servicesPage = serviceRepository.findAll(pageable);
        return servicesPage.map(service -> modelMapper.map(service, ServiceDTO.class));
    }

    @Override
    public ServiceDTO getServiceById(Long id) {
        return serviceRepository.findById(id)
                .map(service -> modelMapper.map(service, ServiceDTO.class))
                .orElse(null);
    }
    public ServiceDTO getServiceDetails(Long id) {
        return serviceRepository.findById(id)
                .map(service -> new ServiceDTO(
                        service.getId(),
                        service.getName(),
                        service.getDescription(),
                        service.getPrice(),
                        service.getDuration(),
                        service.getCategory().getId(),
                        service.getCategory().getName()))
                .orElseThrow(() -> new EntityNotFoundException("Service with ID " + id + " not found"));
    }
}

