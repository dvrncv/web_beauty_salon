package beauty_salon.controllers;

import beauty_salon.DTO.ClientDTO;
import beauty_salon.DTO.LoyaltyCardDTO;
import beauty_salon.service.ClientService;
import beauty_salon.service.LoyaltyCardService;
import controllers.LoyaltyCardController;
import form.CreateLoyaltyCardInput;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import viewmodel.BaseViewModel;
import viewmodel.loyaltyCard.LoyaltyCardViewModel;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class LoyaltyCardControllerImpl implements LoyaltyCardController {
    private final LoyaltyCardService loyaltyCardService;
    private final ClientService clientService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);


    public LoyaltyCardControllerImpl(LoyaltyCardService loyaltyCardService, ClientService clientService) {
        this.loyaltyCardService = loyaltyCardService;
        this.clientService = clientService;
    }

    @Override
    public String listLoyaltyCards(Principal principal, Model model) {
        LOG.info("GET:/loyalty/list Get loyalty card list by category request from " + principal.getName());

        List<LoyaltyCardDTO> loyaltyCards = loyaltyCardService.getAllLoyaltyCard();
        List<LoyaltyCardViewModel> loyaltyCardViewModels = loyaltyCards.stream()
                .map(loyaltyCard -> new LoyaltyCardViewModel(
                        loyaltyCard.getNumberCard(),
                        loyaltyCard.getBalancePoint(),
                        loyaltyCard.getDateIssue(),
                        loyaltyCard.getStatus(),
                        loyaltyCard.getClientName(),
                        loyaltyCard.getClientSurname()
                ))
                .collect(Collectors.toList());

        model.addAttribute("loyaltyCards", loyaltyCardViewModels);
        return "LoyaltyCardList.html";
    }

    @Override
    public String createLoyaltyCard(Principal principal, Model model) {
        LOG.info("GET:/loyalty/create Create loyalty card request from " + principal.getName());
        List<ClientDTO> clients = clientService.getClientsWithoutLoyaltyCard();
        model.addAttribute("clients", clients);
        return "CreateLoyaltyCardForm.html";
    }

    @Override
    public String createLoyaltyCard(@Valid @ModelAttribute("createLoyaltyCard") CreateLoyaltyCardInput createLoyaltyCard,
                                    Principal principal, Model model) {
        LOG.info("POST:/loyalty/create Create loyalty card request from " + principal.getName());
        LoyaltyCardDTO loyaltyCardDTO = new LoyaltyCardDTO();
        loyaltyCardDTO.setClientId(createLoyaltyCard.getClientId());
        loyaltyCardService.createLoyaltyCard(loyaltyCardDTO);
        return "redirect:/loyalty/list";
    }

    @Override
    public String loyaltyCard(Principal principal, Model model) {
        LOG.info("GET:/loyalty/card Get loyalty card client request from " + principal.getName());
        String email = principal.getName();
        ClientDTO client = clientService.getClientByEmail(email);

        LoyaltyCardDTO loyaltyCard = loyaltyCardService.getLoyaltyCardClient(email);

        BaseViewModel salon = new BaseViewModel("Beauty people", "Всегда делаем красоту для вас!");
        model.addAttribute("salon", salon);

        LoyaltyCardViewModel cardViewModel = new LoyaltyCardViewModel(
                loyaltyCard.getNumberCard(),
                loyaltyCard.getBalancePoint(),
                loyaltyCard.getDateIssue(),
                loyaltyCard.getStatus(),
                client.getName(),
                client.getSurname()
        );

        model.addAttribute("loyaltyCard", cardViewModel);

        return "LoyaltyCardView.html";
    }
}
