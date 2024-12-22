package beauty_salon.service;

import beauty_salon.DTO.ClientDTO;

import java.util.List;

public interface ClientService {
    ClientDTO getClientByEmail(String email);

    List<ClientDTO> getClientsWithoutLoyaltyCard();
}
