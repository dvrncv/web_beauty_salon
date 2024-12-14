package beauty_salon.service.Impl;

import beauty_salon.DTO.LoyaltyCardDTO;
import beauty_salon.entities.ClientEntity;
import beauty_salon.entities.LoyaltyCardEntity;
import beauty_salon.exception.EntityNotFoundException;
import beauty_salon.repository.ClientRepository;
import beauty_salon.repository.LoyaltyCardRepository;
import beauty_salon.service.LoyaltyCardService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoyaltyCardServiceImpl implements LoyaltyCardService {
    private final LoyaltyCardRepository loyaltyCardRepository;
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public LoyaltyCardServiceImpl(LoyaltyCardRepository loyaltyCardRepository, ClientRepository clientRepository, ModelMapper modelMapper) {
        this.loyaltyCardRepository = loyaltyCardRepository;
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<LoyaltyCardDTO> createLoyaltyCard(LoyaltyCardDTO loyaltyCardDTO) {
        ClientEntity clientEntity = clientRepository.findById(loyaltyCardDTO.getClientId()).orElseThrow(() -> new IllegalArgumentException("Client with not found"));
        if (clientEntity.getLoyaltyCard() != null) {
            throw new IllegalStateException("Client already has a loyalty card.");
        }
        LoyaltyCardEntity loyaltyCardEntity = new LoyaltyCardEntity(clientEntity);
        LoyaltyCardEntity savedLoyaltyCardEntity = loyaltyCardRepository.save(loyaltyCardEntity);
        LoyaltyCardDTO savedLoyaltyCardDTO = modelMapper.map(savedLoyaltyCardEntity, LoyaltyCardDTO.class);
        return ResponseEntity.ok(savedLoyaltyCardDTO);
    }

    @Override
    public List<LoyaltyCardDTO> getAllLoyaltyCard() {
        List<LoyaltyCardEntity> loyaltyCard = loyaltyCardRepository.findAll();
        return loyaltyCard.stream()
                .map(loyaltyCardEntity -> modelMapper.map(loyaltyCardEntity, LoyaltyCardDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LoyaltyCardDTO addPoints(Long clientId, BigDecimal totalCost) {
        ClientEntity client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId));
        LoyaltyCardEntity loyaltyCard = client.getLoyaltyCard();
        if (loyaltyCard == null) {
            throw new IllegalStateException("Client with ID " + clientId + " does not have a loyalty card.");
        }
        int pointsAdd = totalCost.multiply(BigDecimal.valueOf(0.03)).intValue();
        loyaltyCard.setBalancePoint(loyaltyCard.getBalancePoint() + pointsAdd);
        loyaltyCardRepository.save(loyaltyCard);
        return modelMapper.map(loyaltyCard, LoyaltyCardDTO.class);
    }
}

