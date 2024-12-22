package beauty_salon.repository;

import beauty_salon.entities.EmployeeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends GeneralRepository<EmployeeEntity, Long> {
    @Query("select e.id as id, e.name as name, e.surname as surname, " +
            "count (a.id) as appointmentCount " +
            "from AppointmentServiceEntity a " +
            "join a.employee e " +
            "where a.dateStart between :startDate and :endDate " +
            "group by e.id " +
            "order by appointmentCount desc")
    List<Object[]> findTopEmployees(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("select e from EmployeeEntity e where e.email = :email")
    Optional<EmployeeEntity> findByEmail(@Param("email") String email);

}

