package beauty_salon.service;

import beauty_salon.DTO.LoyaltyCardDTO;
import beauty_salon.DTO.ServiceDTO;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface LoyaltyCardService {
    LoyaltyCardDTO addPoints(Long clientId, BigDecimal totalCost);
    List<LoyaltyCardDTO> getAllLoyaltyCard();
    ResponseEntity<LoyaltyCardDTO> createLoyaltyCard(LoyaltyCardDTO loyaltyCardDTO);
}
