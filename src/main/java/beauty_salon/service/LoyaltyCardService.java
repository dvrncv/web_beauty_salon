package beauty_salon.service;

import beauty_salon.DTO.LoyaltyCardDTO;
import beauty_salon.DTO.ServiceDTO;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface LoyaltyCardService {
    List<LoyaltyCardDTO> getAllLoyaltyCard();

    ResponseEntity<LoyaltyCardDTO> createLoyaltyCard(LoyaltyCardDTO loyaltyCardDTO);

    void addPointsToLoyaltyCard(Long clientId, Long appointmentId);

    LoyaltyCardDTO getLoyaltyCardClient(String email);
}
