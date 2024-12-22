package beauty_salon.controllers;

import beauty_salon.DTO.CategoryDTO;
import beauty_salon.DTO.ServiceDTO;
import beauty_salon.service.CategoryService;
import beauty_salon.service.ServiceService;
import controllers.CatalogController;
import form.ServiceSerachInputModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import viewmodel.BaseViewModel;
import viewmodel.category.CategoryPageViewModel;
import viewmodel.category.CategoryViewModel;
import viewmodel.service.CardServiceViewModel;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CatalogControllerImpl implements CatalogController {
    private final CategoryService categoryService;
    private final ServiceService serviceService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public CatalogControllerImpl(CategoryService categoryService, ServiceService serviceService) {
        this.categoryService = categoryService;
        this.serviceService = serviceService;
    }

    @Override
    public String getCatalogPage(@ModelAttribute ServiceSerachInputModel serviceSerachInputModel,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                 @RequestParam(value = "searchService", required = false) String searchService,
                                 Principal principal, Model model) {

        LOG.info("GET:/catalog Get catalog page request from " + principal.getName());

        if (serviceSerachInputModel.getPage() == null) {
            serviceSerachInputModel.setPage(page);
        }
        if (serviceSerachInputModel.getSize() == null) {
            serviceSerachInputModel.setSize(size);
        }

        BaseViewModel salon = new BaseViewModel("Beauty people", "Всегда делаем красоту для вас!");
        model.addAttribute("salon", salon);

        List<CategoryDTO> categories = categoryService.getCategories();


        List<CategoryViewModel> categoryViewModels = categories.stream()
                .map(category -> new CategoryViewModel(
                        category.getId(),
                        category.getName(),
                        category.getDescription()))
                .collect(Collectors.toList());

        Page<ServiceDTO> services = serviceService.getServices(searchService, serviceSerachInputModel.getPage(), serviceSerachInputModel.getSize());
        services = serviceService.filterServices(services, searchService);

        List<CardServiceViewModel> cardServiceViewModels = services.stream()
                .map(service -> new CardServiceViewModel(
                        service.getId(),
                        service.getName(),
                        service.getPrice(),
                        service.getDuration(),
                        service.getDescription(),
                        service.getCategoryName()))
                .collect(Collectors.toList());

        CategoryPageViewModel categoryPageViewModel = new CategoryPageViewModel(salon, categoryViewModels, cardServiceViewModels, serviceSerachInputModel);
        model.addAttribute("categoryPageViewModel", categoryPageViewModel);

        return "Catalog.html";
    }

    @Override
    public String getServicesByCategory(@PathVariable Long categoryId, @ModelAttribute ServiceSerachInputModel serviceSerachInputModel,
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                        Principal principal, Model model) {
        LOG.info("GET:/catalog/category/{categoryId} Get service by category request from " + principal.getName());

        if (serviceSerachInputModel.getPage() == null) {
            serviceSerachInputModel.setPage(page);
        }
        if (serviceSerachInputModel.getSize() == null) {
            serviceSerachInputModel.setSize(size);
        }

        BaseViewModel salon = new BaseViewModel("Beauty people", "Всегда делаем красоту для вас!");
        model.addAttribute("salon", salon);

        List<CategoryDTO> categories = categoryService.getCategories();
        List<CategoryViewModel> categoryViewModels = categories.stream()
                .map(category -> new CategoryViewModel(
                        category.getId(),
                        category.getName(),
                        category.getDescription()))
                .collect(Collectors.toList());

        Page<ServiceDTO> services = serviceService.getServicesByCategory(categoryId, page, size);
        services = serviceService.filterServices(services, serviceSerachInputModel.getSearchService());

        List<CardServiceViewModel> cardServiceViewModels = services.stream()
                .map(service -> new CardServiceViewModel(
                        service.getId(),
                        service.getName(),
                        service.getPrice(),
                        service.getDuration(),
                        service.getDescription(),
                        service.getCategoryName()))
                .collect(Collectors.toList());

        CategoryPageViewModel categoryPageViewModel = new CategoryPageViewModel(salon, categoryViewModels, cardServiceViewModels, serviceSerachInputModel);
        model.addAttribute("categoryPageViewModel", categoryPageViewModel);

        return "Catalog.html";
    }

    @Override
    public String getServiceDetails(@PathVariable Long serviceId,
                                    Principal principal, Model model) {

        LOG.info("GET:/catalog/service/{serviceId} Get service details request from " + principal.getName());

        ServiceDTO serviceDTO = serviceService.getServiceById(serviceId);
        BaseViewModel salon = new BaseViewModel("Beauty people", "Всегда делаем красоту для вас!");

        CardServiceViewModel viewModel = new CardServiceViewModel(
                serviceDTO.getId(),
                serviceDTO.getName(),
                serviceDTO.getPrice(),
                serviceDTO.getDuration(),
                serviceDTO.getDescription(),
                serviceDTO.getCategoryName()
        );

        model.addAttribute("service", viewModel);
        model.addAttribute("salon", salon);

        return "ServiceDetails.html";
    }
}
