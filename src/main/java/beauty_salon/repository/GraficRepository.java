package beauty_salon.repository;

import beauty_salon.entities.EmployeeEntity;
import beauty_salon.entities.GrafikEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GraficRepository extends GeneralRepository<GrafikEntity, Long> {
    @Query("select g from GrafikEntity g where g.employee.id = :employeeId and g.day = :date")
    Optional<GrafikEntity> findGrafikForEmployeeAndDate(@Param("employeeId") Long employeeId,
                                                        @Param("date") LocalDate date);

    @Query("select g from GrafikEntity g where g.employee = :employee and g.day >= :currentDate")
    List<GrafikEntity> findByEmployeeAndCurrentDate(EmployeeEntity employee, LocalDate currentDate);
}
