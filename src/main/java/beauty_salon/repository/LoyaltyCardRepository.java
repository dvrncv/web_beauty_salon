package beauty_salon.repository;

import beauty_salon.entities.ClientEntity;
import beauty_salon.entities.LoyaltyCardEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoyaltyCardRepository extends GeneralRepository<LoyaltyCardEntity, Long> {
    @Query("select l from LoyaltyCardEntity l where l.client.id = :clientId")
    Optional<LoyaltyCardEntity> findByClientId(@Param("clientId") Long clientId);

    LoyaltyCardEntity findByClient(ClientEntity client);
}
