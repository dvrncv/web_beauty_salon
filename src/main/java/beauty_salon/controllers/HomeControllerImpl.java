package beauty_salon.controllers;

import beauty_salon.service.EmployeeService;
import beauty_salon.service.ServiceService;
import controllers.HomeController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import viewmodel.BaseViewModel;
import viewmodel.employee.EmployeeViewModel;
import viewmodel.service.CardServiceViewModel;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeControllerImpl implements HomeController {
    private final EmployeeService employeeService;
    private final ServiceService serviceService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public HomeControllerImpl(EmployeeService employeeService, ServiceService serviceService) {
        this.employeeService = employeeService;
        this.serviceService = serviceService;
    }

    @Override
    public String homePage(Principal principal, Model model) {
        LOG.info("GET:/home Get home page request from " + principal.getName());

        BaseViewModel salon = new BaseViewModel("Beauty people", "Всегда делаем красоту для вас!");
        model.addAttribute("salon", salon);

        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();

        List<EmployeeViewModel> employeeViewModels = employeeService.getTopEmployees(startDate, endDate).stream()
                .map(employee -> new EmployeeViewModel(
                        employee.getName(),
                        employee.getSurname()))
                .collect(Collectors.toList());

        model.addAttribute("topEmployees", employeeViewModels);

        List<CardServiceViewModel> cardServiceViewModel = serviceService.getTopServices(startDate, endDate).stream()
                .map(service -> new CardServiceViewModel(
                        service.getId(),
                        service.getName(),
                        service.getPrice(),
                        service.getDuration(),
                        service.getDescription(),
                        service.getCategoryName()))
                .collect(Collectors.toList());

        model.addAttribute("topServices", cardServiceViewModel);

        return "Home.html";
    }
}
