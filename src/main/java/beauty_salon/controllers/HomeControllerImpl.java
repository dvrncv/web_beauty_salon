package beauty_salon.controllers;

import beauty_salon.DTO.EmployeeDTO;
import beauty_salon.DTO.ServiceDTO;
import beauty_salon.service.EmployeeService;
import beauty_salon.service.ServiceService;
import controllers.HomeController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import viewmodel.BaseViewModel;
import viewmodel.employee.EmployeeViewModel;
import viewmodel.service.CardServiceViewModel;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeControllerImpl implements HomeController {
    private final EmployeeService employeeService;
    private final ServiceService serviceService;

    public HomeControllerImpl(EmployeeService employeeService, ServiceService serviceService) {
        this.employeeService = employeeService;
        this.serviceService = serviceService;
    }

    @Override
    @GetMapping
    public String homePage(Model model) {
        BaseViewModel salon = new BaseViewModel("Beauty people", "Всегда делаем красоту для вас!");
        model.addAttribute("salon", salon);

        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();
        List<EmployeeDTO> topEmployees = employeeService.getTopEmployees(startDate, endDate);
        topEmployees = topEmployees.stream().limit(5).collect(Collectors.toList());
        List<EmployeeViewModel> employeeViewModels = convertToEmployeeViewModels(topEmployees);
        model.addAttribute("topEmployees", employeeViewModels);
        List<ServiceDTO> topServices = serviceService.getTopServices(startDate, endDate);
        topServices=topServices.stream().limit(5).collect(Collectors.toList());
        List<CardServiceViewModel> cardServiceViewModel = convertToServiceViewModels(topServices);
        model.addAttribute("topServices", cardServiceViewModel);
        return "Home.html";
    }


    private List<EmployeeViewModel> convertToEmployeeViewModels(List<EmployeeDTO> employees) {
        return employees.stream()
                .map(emp -> new EmployeeViewModel(
                        emp.getName(),
                        emp.getSurname(),
                        emp.getClientCount()
                ))
                .collect(Collectors.toList());
    }

    private List<CardServiceViewModel> convertToServiceViewModels(List<ServiceDTO> services) {
        return services.stream()
                .map(ser -> new CardServiceViewModel(
                        ser.getId(),
                        ser.getName(),
                        ser.getPrice(),
                        ser.getDuration(),
                        ser.getDescription(),
                        ser.getCategoryName()
                ))
                .collect(Collectors.toList());
    }
}
