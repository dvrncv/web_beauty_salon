package beauty_salon.controllers;

import beauty_salon.DTO.EmployeeDTO;
import beauty_salon.entities.EmployeeEntity;
import beauty_salon.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeControllerImpl {
    private final EmployeeService employeeService;

    public EmployeeControllerImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String employeeList(Model model) {
        List<EmployeeDTO> employees = employeeService.getAllEmployee();
        model.addAttribute("employees", employeeService.getAllEmployee());
        return "EmployeeList";
    }
}
