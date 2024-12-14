package beauty_salon.controllers;

import beauty_salon.DTO.CategoryDTO;
import beauty_salon.DTO.ServiceDTO;
import beauty_salon.service.CategoryService;
import beauty_salon.service.ServiceService;
import controllers.CatalogController;
import form.ServiceSerachInputModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import viewmodel.BaseViewModel;
import viewmodel.category.CategoryPageViewModel;
import viewmodel.category.CategoryViewModel;
import viewmodel.service.CardServiceViewModel;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/catalog")
public class CatalogControllerImpl implements CatalogController {
    private final CategoryService categoryService;
    private final ServiceService serviceService;

    public CatalogControllerImpl(CategoryService categoryService, ServiceService serviceService) {
        this.categoryService = categoryService;
        this.serviceService = serviceService;
    }

    @GetMapping
    public String getCatalogPage(@ModelAttribute ServiceSerachInputModel serviceSerachInputModel,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                 @RequestParam(value = "searchService", required = false) String searchService,
                                 @RequestParam(value = "categoryId", required = false) Long categoryId,
                                 Model model) {

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
                .map(category -> {
                    CategoryViewModel viewModel = new CategoryViewModel();
                    viewModel.setId(category.getId());
                    viewModel.setName(category.getName());
                    viewModel.setDescription(category.getDescription());
                    return viewModel;
                })
                .collect(Collectors.toList());
        Page<ServiceDTO> services = serviceService.getServices(searchService, categoryId, serviceSerachInputModel.getPage(), serviceSerachInputModel.getSize());

        List<CardServiceViewModel> cardServiceViewModels = services.stream()
                .map(service -> new CardServiceViewModel(service.getId(), service.getName(), service.getPrice(), service.getDuration(), service.getDescription(), service.getCategoryName()))
                .collect(Collectors.toList());

        CategoryPageViewModel categoryPageViewModel = new CategoryPageViewModel(salon, categoryViewModels, cardServiceViewModels, serviceSerachInputModel);

        model.addAttribute("categoryPageViewModel", categoryPageViewModel);

        return "Catalog.html";
    }

    @GetMapping("/category/{categoryId}")
    public String getServicesByCategory(@PathVariable Long categoryId, @ModelAttribute ServiceSerachInputModel serviceSerachInputModel, Model model) {
        BaseViewModel salon = new BaseViewModel("Beauty people", "Всегда делаем красоту для вас!");
        model.addAttribute("salon", salon);

        List<CategoryDTO> categories = categoryService.getCategories();
        List<CategoryViewModel> categoryViewModels = categories.stream()
                .map(category -> {
                    CategoryViewModel viewModel = new CategoryViewModel();
                    viewModel.setId(category.getId());
                    viewModel.setName(category.getName());
                    viewModel.setDescription(category.getDescription());
                    return viewModel;
                })
                .collect(Collectors.toList());

        List<ServiceDTO> services = serviceService.getServicesByCategory(categoryId);

        if (serviceSerachInputModel.getSearchService() != null && !serviceSerachInputModel.getSearchService().isEmpty()) {
            services = services.stream()
                    .filter(service -> service.getName().toLowerCase().contains(serviceSerachInputModel.getSearchService().toLowerCase()))
                    .collect(Collectors.toList());
        }

        List<CardServiceViewModel> cardServiceViewModels = services.stream()
                .map(service -> new CardServiceViewModel(service.getId(), service.getName(), service.getPrice(), service.getDuration(), service.getDescription(), service.getCategoryName()))
                .collect(Collectors.toList());

        CategoryPageViewModel categoryPageViewModel = new CategoryPageViewModel(salon, categoryViewModels, cardServiceViewModels, serviceSerachInputModel);

        model.addAttribute("categoryPageViewModel", categoryPageViewModel);
        return "Catalog.html";
    }
    @GetMapping("/service/{serviceId}")
    public String getServiceDetails(@PathVariable Long serviceId, Model model) {
        ServiceDTO serviceDTO = serviceService.getServiceById(serviceId);


        BaseViewModel salon = new BaseViewModel("Beauty people", "Всегда делаем красоту для вас!");
        model.addAttribute("salon", salon);
        model.addAttribute("service", serviceDTO);

        return "ServiceDetails.html";
    }
}
