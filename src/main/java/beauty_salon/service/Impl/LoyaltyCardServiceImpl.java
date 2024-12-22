package beauty_salon.service.Impl;

import beauty_salon.DTO.LoyaltyCardDTO;
import beauty_salon.entities.AppointmentServiceEntity;
import beauty_salon.entities.ClientEntity;
import beauty_salon.entities.LoyaltyCardEntity;
import beauty_salon.exception.EntityNotFoundException;
import beauty_salon.repository.AppointmentServiceRepository;
import beauty_salon.repository.ClientRepository;
import beauty_salon.repository.LoyaltyCardRepository;
import beauty_salon.service.LoyaltyCardService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class LoyaltyCardServiceImpl implements LoyaltyCardService {
    private final LoyaltyCardRepository loyaltyCardRepository;
    private final ClientRepository clientRepository;
    private final AppointmentServiceRepository appointmentServiceRepository;
    private final ModelMapper modelMapper;

    public LoyaltyCardServiceImpl(LoyaltyCardRepository loyaltyCardRepository, ClientRepository clientRepository, AppointmentServiceRepository appointmentServiceRepository, ModelMapper modelMapper) {
        this.loyaltyCardRepository = loyaltyCardRepository;
        this.clientRepository = clientRepository;
        this.appointmentServiceRepository = appointmentServiceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<LoyaltyCardDTO> createLoyaltyCard(LoyaltyCardDTO loyaltyCardDTO) {
        ClientEntity clientEntity = clientRepository.findById(loyaltyCardDTO.getClientId()).orElseThrow(() -> new EntityNotFoundException("Клиент c id " + id + " не найден"));
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
    public void addPointsToLoyaltyCard(Long clientId, Long appointmentId) {
        AppointmentServiceEntity appointment = appointmentServiceRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Записи с таким id " + appointmentId + " не существует"));
        if (appointment.isPointsAdded()) {
            throw new RuntimeException("Баллы уже начислены");
        }
        LoyaltyCardEntity loyaltyCard = loyaltyCardRepository.findByClientId(clientId)
                .orElse(null);
        if (loyaltyCard == null) {
            throw new IllegalStateException("Клиент не имеет карты лояльности");
        }
        double pointsToAdd = appointment.getTotalCost() * 0.03;
        loyaltyCard.setBalancePoint(loyaltyCard.getBalancePoint() + (int) pointsToAdd);
        loyaltyCardRepository.save(loyaltyCard);
        appointment.setPointsAdded(true);
        appointmentServiceRepository.save(appointment);
    }

    @Override
    public LoyaltyCardDTO getLoyaltyCardClient(String email) {
        ClientEntity client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Клиент с таким email " + email + " не существует"));

        LoyaltyCardEntity loyaltyCard = loyaltyCardRepository.findByClient(client);

        LoyaltyCardDTO loyaltyCardDTO = modelMapper.map(loyaltyCard, LoyaltyCardDTO.class);
        loyaltyCardDTO.setClientName(client.getName());
        loyaltyCardDTO.setClientSurname(client.getSurname());
        return loyaltyCardDTO;
    }
}

