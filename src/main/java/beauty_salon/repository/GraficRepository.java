package beauty_salon.repository;

import beauty_salon.entities.EmployeeEntity;
import beauty_salon.entities.GrafikEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraficRepository extends GeneralRepository<GrafikEntity,Long>{
    List<GrafikEntity> findByEmployeeAndDayOfWeek(EmployeeEntity employee, String dayOfWeek);
}
