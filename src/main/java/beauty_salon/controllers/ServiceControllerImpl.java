package beauty_salon.controllers;

import beauty_salon.DTO.CategoryDTO;
import beauty_salon.DTO.ServiceDTO;
import beauty_salon.service.CategoryService;
import beauty_salon.service.ServiceService;
import controllers.ServiceController;
import form.CreateServiceInputModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import viewmodel.service.CardServiceViewModel;


import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ServiceControllerImpl implements ServiceController {
    private final ServiceService serviceService;
    private final CategoryService categoryService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public ServiceControllerImpl(ServiceService serviceService, CategoryService categoryService) {
        this.serviceService = serviceService;
        this.categoryService = categoryService;
    }

    @Override
    public String listServices(Principal principal, Model model) {
        LOG.info("GET:/service/list Show all service request from " + principal.getName());

        List<ServiceDTO> services = serviceService.getAllServices();
        List<CardServiceViewModel> cardServiceViewModels = services.stream()
                .map(service -> new CardServiceViewModel(
                        service.getId(),
                        service.getName(),
                        service.getPrice(),
                        service.getDuration(),
                        service.getDescription(),
                        service.getCategoryName()))
                .collect(Collectors.toList());

        for (ServiceDTO service : services) {
            String categoryName = categoryService.getCategoryNameById(service.getCategoryId());
            service.setCategoryName(categoryName);
        }

        model.addAttribute("services", cardServiceViewModels);
        return "ServiceList.html";
    }

    @Override
    public String createService(Principal principal, Model model) {
        LOG.info("GET:/service/create Create service request from " + principal.getName());

        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("createService", new CreateServiceInputModel());
        return "ServiceList.html";
    }

    @Override
    public String createService(@Valid @ModelAttribute("createService") CreateServiceInputModel createService,
                                Principal principal, Model model) {
        LOG.info("POST:/service/create Create service request from " + principal.getName());

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName(createService.getName());
        serviceDTO.setDescription(createService.getDescription());
        serviceDTO.setPrice(createService.getPrice());
        serviceDTO.setDuration(createService.getDuration());
        serviceDTO.setCategoryId(createService.getCategoryId());
        serviceService.createService(serviceDTO);
        return "redirect:/service/list";
    }

    @Override
    public String updateService(@PathVariable("id") Long id,
                                Principal principal, Model model) {

        LOG.info("GET:/service/update Update service request from " + principal.getName());

        ServiceDTO serviceDTO = serviceService.findById(id).getBody();
        List<CategoryDTO> categories = categoryService.getAllCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("updateService", serviceDTO);
        return "ServiceList.html";
    }

    @Override
    public String updateService(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("updateService") CreateServiceInputModel createService,
                                Principal principal, Model model) {

        LOG.info("POST:/service/update Update service request from " + principal.getName());

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName(createService.getName());
        serviceDTO.setDescription(createService.getDescription());
        serviceDTO.setPrice(createService.getPrice());
        serviceDTO.setDuration(createService.getDuration());
        serviceDTO.setCategoryId(createService.getCategoryId());

        serviceService.updateService(id, serviceDTO);
        return "redirect:/service/list";
    }


    @Override
    public String getServiceDetails(@PathVariable("id") Long id,
                                    Principal principal, Model model) {

        LOG.info("GET:/service/{id} Id service request from " + principal.getName());

        ServiceDTO service = serviceService.getServiceDetails(id);
        CardServiceViewModel serviceViewModel = new CardServiceViewModel(
                service.getId(),
                service.getName(),
                service.getPrice(),
                service.getDuration(),
                service.getDescription(),
                service.getCategoryName()
        );

        model.addAttribute("service", serviceViewModel);
        return "ServiceAbout.html";
    }
}
