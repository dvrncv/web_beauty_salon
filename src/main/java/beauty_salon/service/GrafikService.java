package beauty_salon.service;

import beauty_salon.DTO.EmployeeDTO;
import beauty_salon.DTO.GrafikDTO;


import java.util.List;

public interface GrafikService {
    void createGrafik(GrafikDTO grafikDTO);

    List<GrafikDTO> getGrafikForEmployee(EmployeeDTO employeeDTO);
}
