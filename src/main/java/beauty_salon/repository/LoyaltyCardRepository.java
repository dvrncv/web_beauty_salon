package beauty_salon.repository;

import beauty_salon.entities.LoyaltyCardEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoyaltyCardRepository extends GeneralRepository<LoyaltyCardEntity,Long>{
    @Query(value = "select l from LoyaltyCardEntity l where l.numberCard=: numberCard")
    List<LoyaltyCardEntity> findByNumberCard (@Param("numberCard") Integer numberCard);

    @Query(value = "select l from LoyaltyCardEntity l where l.status =: status")
    List<LoyaltyCardEntity> findByLoyaltyCardStatus (@Param("status") String status);

    @Query(value = "select l from LoyaltyCardEntity l join ClientEntity c on c.loyaltyCard=l where c.id = :clientId")
    Optional<LoyaltyCardEntity> findByClientId (@Param("clientId") Long clientId);
}
