package beauty_salon.repository;

import beauty_salon.entities.ClientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends GeneralRepository<ClientEntity, Long> {
    @Query("select c from ClientEntity c where c.email = :email")
    Optional<ClientEntity> findByEmail(@Param("email") String email);

    @Query("select c from ClientEntity c where c.id = :id")
    Optional<ClientEntity> findById(@Param("id") Long id);
}
