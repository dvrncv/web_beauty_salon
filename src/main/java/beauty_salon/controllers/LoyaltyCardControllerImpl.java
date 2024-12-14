package beauty_salon.controllers;

import beauty_salon.DTO.CategoryDTO;
import beauty_salon.DTO.LoyaltyCardDTO;
import beauty_salon.entities.ClientEntity;
import beauty_salon.entities.LoyaltyCardEntity;
import beauty_salon.repository.ClientRepository;
import beauty_salon.service.LoyaltyCardService;
import controllers.LoyaltyCardController;
import form.CreateLoyaltyCardInput;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import viewmodel.BaseViewModel;
import viewmodel.category.CategoryViewModel;
import viewmodel.loyaltyCard.LoyaltyCardViewModel;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/loyalty")
public class LoyaltyCardControllerImpl implements LoyaltyCardController {
    private final LoyaltyCardService loyaltyCardService;
    private final ClientRepository clientRepository;

    public LoyaltyCardControllerImpl(LoyaltyCardService loyaltyCardService, ClientRepository clientRepository) {
        this.loyaltyCardService = loyaltyCardService;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/list")
    public String listLoyaltyCards(Model model) {
        List<LoyaltyCardDTO> loyaltyCards = loyaltyCardService.getAllLoyaltyCard();
        List<LoyaltyCardViewModel> loyaltyCardViewModels = loyaltyCards.stream()
                .map(loyaltyCardDTO -> new LoyaltyCardViewModel(
                        loyaltyCardDTO.getNumberCard(),
                        loyaltyCardDTO.getBalancePoint(),
                        loyaltyCardDTO.getDateIssue(),
                        loyaltyCardDTO.getClientName(),
                        loyaltyCardDTO.getClientSurname()
                ))
                .toList();

        model.addAttribute("loyaltyCards", loyaltyCards);
        return "LoyaltyCardList.html";
    }

    @GetMapping("/create")
    public String createLoyaltyCard(Model model) {
        List<ClientEntity> clients = clientRepository.findAll()
                .stream()
                .filter(client -> client.getLoyaltyCard() == null)
                .collect(Collectors.toList());
        model.addAttribute("clients", clients);
        return "CreateLoyaltyCardForm.html";
    }

    @PostMapping("/create")
    public String createLoyaltyCard(
            @Valid @ModelAttribute("createLoyaltyCard") CreateLoyaltyCardInput createLoyaltyCard,
            Model model
    ) {
        LoyaltyCardDTO loyaltyCardDTO = new LoyaltyCardDTO();
        loyaltyCardDTO.setClientId(createLoyaltyCard.getClientId());
        loyaltyCardService.createLoyaltyCard(loyaltyCardDTO);
        model.addAttribute("message", "Loyalty card created successfully!");
        return "redirect:/loyalty/list";
    }

    @GetMapping("/card")
    public String loyaltyCard(Principal principal, Model model) {
        ClientEntity client = clientRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Client not found with email: " + principal.getName()));
        LoyaltyCardEntity loyaltyCard = client.getLoyaltyCard();

        LoyaltyCardViewModel cardViewModel = new LoyaltyCardViewModel(
                loyaltyCard.getNumberCard(),
                loyaltyCard.getBalancePoint(),
                loyaltyCard.getDateIssue(),
                client.getName(),
                client.getSurname()
        );

        model.addAttribute("loyaltyCard", cardViewModel);

        BaseViewModel salon = new BaseViewModel("Beauty people", "Всегда делаем красоту для вас!");
        model.addAttribute("salon", salon);
        return "LoyaltyCardView.html";
    }



//    @PostMapping("/addPoints")
//    public LoyaltyCardDTO addPoints (@RequestParam Long clientId, @RequestParam BigDecimal totalCost){
//        return loyaltyCardService.addPoints(clientId, totalCost);
//    }
}
