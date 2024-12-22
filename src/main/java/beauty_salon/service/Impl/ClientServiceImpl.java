package beauty_salon.service.Impl;

import beauty_salon.DTO.ClientDTO;
import beauty_salon.entities.ClientEntity;
import beauty_salon.exception.EntityNotFoundException;
import beauty_salon.repository.ClientRepository;
import beauty_salon.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ClientDTO getClientByEmail(String email) {
        ClientEntity client = clientRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Клиент c таким email" + email + " не найден"));
        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public List<ClientDTO> getClientsWithoutLoyaltyCard() {
        List<ClientEntity> clientsWithoutCard = clientRepository.findAll()
                .stream()
                .filter(client -> client.getLoyaltyCard() == null)
                .collect(Collectors.toList());

        return clientsWithoutCard.stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }
}
