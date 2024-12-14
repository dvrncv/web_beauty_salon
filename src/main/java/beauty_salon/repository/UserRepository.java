package beauty_salon.repository;

import beauty_salon.entities.ClientEntity;
import beauty_salon.entities.EmployeeEntity;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

public interface UserRepository extends GeneralRepository<EmployeeEntity,Long> {
    Optional<EmployeeEntity> findByEmail(String email);


    Optional<EmployeeEntity> findByName(String name);
}
