package beauty_salon.service.Impl;

import beauty_salon.DTO.EmployeeDTO;
import beauty_salon.DTO.GrafikDTO;
import beauty_salon.entities.EmployeeEntity;
import beauty_salon.entities.GrafikEntity;
import beauty_salon.repository.GraficRepository;
import beauty_salon.service.GrafikService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrafikServiceImpl implements GrafikService {
    private final GraficRepository graficRepository;
    private final ModelMapper modelMapper;

    public GrafikServiceImpl(GraficRepository graficRepository, ModelMapper modelMapper) {
        this.graficRepository = graficRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createGrafik(GrafikDTO grafikDTO) {
        GrafikEntity grafikEntity = modelMapper.map(grafikDTO, GrafikEntity.class);
        graficRepository.save(grafikEntity);
    }

    @Override
    public List<GrafikDTO> getGrafikForEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity employee = modelMapper.map(employeeDTO, EmployeeEntity.class);
        LocalDate currentDate = LocalDate.now();
        List<GrafikEntity> grafikEntities = graficRepository.findByEmployeeAndCurrentDate(employee, currentDate);
        return grafikEntities.stream()
                .map(grafikEntity -> modelMapper.map(grafikEntity, GrafikDTO.class))
                .collect(Collectors.toList());
    }
}
