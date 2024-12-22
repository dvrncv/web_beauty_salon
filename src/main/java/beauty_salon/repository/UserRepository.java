package beauty_salon.repository;

import beauty_salon.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends GeneralRepository<EmployeeEntity, Long> {
    @Query("select e from EmployeeEntity e where e.email = :email")
    Optional<EmployeeEntity> findByEmail(@Param("email") String email);

    @Query("select e from EmployeeEntity e where e.name = :name")
    Optional<EmployeeEntity> findByName(@Param("name") String name);
}
