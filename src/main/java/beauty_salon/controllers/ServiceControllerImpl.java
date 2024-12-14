package beauty_salon.controllers;

import beauty_salon.DTO.CategoryDTO;
import beauty_salon.DTO.ServiceDTO;
import beauty_salon.service.CategoryService;
import beauty_salon.service.ServiceService;
import controllers.ServiceController;
import form.CreateServiceInputModel;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/service")
public class ServiceControllerImpl implements ServiceController {
    private final ServiceService serviceService;
    private final CategoryService categoryService;

    public ServiceControllerImpl(ServiceService serviceService, CategoryService categoryService) {
        this.serviceService = serviceService;
        this.categoryService = categoryService;
    }
    @GetMapping("/list")
    public String listServices(Model model) {
        List<ServiceDTO> services = serviceService.getAllServices();
        for (ServiceDTO service : services) {
            String categoryName = categoryService.getCategoryNameById(service.getCategoryId());
            service.setCategoryName(categoryName);
        }
        model.addAttribute("services", services);
        return "ServiceList.html";
    }

    @GetMapping("/create")
    public String createService(Model model){
        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("createService", new CreateServiceInputModel());
        return "ServiceList.html";
    }

    @Override
    @PostMapping("/create")
    public String createService(
            @Valid @ModelAttribute("createService") CreateServiceInputModel createService,
            Model model
    ) {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName(createService.getName());
        serviceDTO.setDescription(createService.getDescription());
        serviceDTO.setPrice(createService.getPrice());
        serviceDTO.setDuration(createService.getDuration());
        serviceDTO.setCategoryId(createService.getCategoryId());
        serviceService.createService(serviceDTO);
        model.addAttribute("message", "Service created successfully!");
        return "redirect:/service/list";
    }

    @GetMapping("/update/{id}")
    public String updateService(
            @PathVariable("id") Long id, Model model
    )
    {
        ServiceDTO serviceDTO = serviceService.findById(id).getBody();
        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("updateService", serviceDTO);
        return "ServiceList.html";
    }

    @Override
    @PostMapping("/update/{id}")
    public String updateService(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("updateService") CreateServiceInputModel createService,
            Model model
    ) {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setName(createService.getName());
        serviceDTO.setDescription(createService.getDescription());
        serviceDTO.setPrice(createService.getPrice());
        serviceDTO.setDuration(createService.getDuration());
        serviceDTO.setCategoryId(createService.getCategoryId());

        serviceService.updateService(id, serviceDTO);
        model.addAttribute("message", "Service updated successfully!");
        return "redirect:/service/list";
    }

    @Override
    @GetMapping("/delete/{id}")
    public String deleteService(
            @PathVariable("id") Long id,
            Model model
    )
    {
        serviceService.deleteService(id);
        model.addAttribute("message", "Service deleted successfully!");
        return "redirect:/service/list";
    }

}
