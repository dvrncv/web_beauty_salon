package beauty_salon.repository;

import beauty_salon.entities.CategoryEntity;
import beauty_salon.entities.ClientEntity;
import beauty_salon.entities.EmployeeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends GeneralRepository<ClientEntity,Long> {
    Optional<ClientEntity> findByEmail(String email);
    Optional<ClientEntity> findByName(String name);
    List<ClientEntity> findByLoyaltyCardIsNull();
}
