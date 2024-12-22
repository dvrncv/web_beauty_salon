package beauty_salon.repository;

import beauty_salon.entities.AppointmentServiceEntity;
import beauty_salon.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AppointmentServiceRepository extends GeneralRepository<AppointmentServiceEntity,Long> {
    @Query("select a from AppointmentServiceEntity a where a.employee.id = :employeeId and a.dateStart = :date")
    List<AppointmentServiceEntity> findByEmployeeAndDate(@Param("employeeId") Long employeeId,
                                                         @Param("date") LocalDate date);

    @Query("select a from AppointmentServiceEntity a where a.employee = :employee and a.dateStart between :startDate and :endDate")
    List<AppointmentServiceEntity> findByEmployeeAndDateBetween(
                    @Param("employee") EmployeeEntity employee,
                    @Param("startDate") LocalDate startDate,
                    @Param("endDate") LocalDate endDate);

    List<AppointmentServiceEntity> findAll();
}
