package beauty_salon.repository;

import beauty_salon.entities.AppointmentServiceEntity;
import beauty_salon.entities.CategoryEntity;
import beauty_salon.entities.EmployeeEntity;
import beauty_salon.entities.ServiceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface AppointmentServiceRepository extends GeneralRepository<AppointmentServiceEntity,Long> {
    Optional<AppointmentServiceEntity> findByEmployeeAndDateStartAndTimeStart(EmployeeEntity employee, LocalDate dateStart, LocalTime timeStart);
}
