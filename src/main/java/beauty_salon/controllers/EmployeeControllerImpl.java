package beauty_salon.controllers;

import beauty_salon.DTO.AppointmentServiceDTO;
import beauty_salon.DTO.EmployeeDTO;
import beauty_salon.DTO.GrafikDTO;
import beauty_salon.service.AppointmentServiceService;
import beauty_salon.service.EmployeeService;
import beauty_salon.service.GrafikService;
import controllers.EmployeeController;
import form.CreateGrafikInputModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import viewmodel.appointment.AppointmentServiceViewModel;
import viewmodel.employee.EmployeeListViewModel;
import viewmodel.employee.GrafikViewModel;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EmployeeControllerImpl implements EmployeeController {
    private final EmployeeService employeeService;
    private final GrafikService grafikService;
    private final AppointmentServiceService appointmentServiceService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public EmployeeControllerImpl(EmployeeService employeeService, GrafikService grafikService, AppointmentServiceService appointmentServiceService) {
        this.employeeService = employeeService;
        this.grafikService = grafikService;
        this.appointmentServiceService = appointmentServiceService;
    }

    @Override
    public String employeeList(Principal principal, Model model) {
        LOG.info("GET:/employee/list Show employee request from " + principal.getName());

        List<EmployeeDTO> employees = employeeService.getAllEmployee();
        List<EmployeeListViewModel> viewModel = employees.stream()
                .map(employee -> new EmployeeListViewModel(
                        employee.getName(),
                        employee.getSurname(),
                        employee.getEmail()))
                .collect(Collectors.toList());

        model.addAttribute("employees", viewModel);
        return "EmployeeList";
    }

    @Override
    public String createGrafik (Model model, Principal principal) {
        LOG.info("GET:/employee/create/grafik Create grafik employee request from " + principal.getName());

        String email = principal.getName();
        EmployeeDTO employee = employeeService.getEmployeeByEmail(email);
        model.addAttribute("employee", employee);
        model.addAttribute("createGrafikInputModel", new CreateGrafikInputModel());
        return "CreateGrafik";
    }

    @Override
    public String createGrafik (@ModelAttribute CreateGrafikInputModel createGrafikInputModel,
                                Principal principal, Model model) {
        LOG.info("POST:/employee/create/grafik Create grafik employee request from " + principal.getName());

        String email = principal.getName();
        EmployeeDTO employee = employeeService.getEmployeeByEmail(email);

        GrafikDTO grafikDTO = new GrafikDTO(
                createGrafikInputModel.getDay(),
                createGrafikInputModel.getStartTime(),
                createGrafikInputModel.getEndTime(),
                employee.getId()
        );
            grafikService.createGrafik(grafikDTO);
            model.addAttribute("successMessage", "График успешно назначен!");
            return "redirect:/employee/grafik";
    }

    @Override
    public String getGrafik (Principal principal, Model model) {
        LOG.info("GET:/employee/grafik Get grafik request from " + principal.getName());

        String email = principal.getName();
        EmployeeDTO employee = employeeService.getEmployeeByEmail(email);

        List<GrafikDTO> grafikList = grafikService.getGrafikForEmployee(employee);
        List<GrafikViewModel> grafikViewModels = grafikList.stream()
                .map(grafik -> new GrafikViewModel(
                        grafik.getDay(),
                        grafik.getStartTime(),
                        grafik.getEndTime()))
                .collect(Collectors.toList());

        model.addAttribute("grafikViewModels", grafikViewModels);
        return "EmployeeGrafik.html";
    }

    @Override
    public String getEmployeeGrafik(Principal principal,Model model) {
        LOG.info("GET:/employee/appointment Get employee grafik request from " + principal.getName());

        String email = principal.getName();
        EmployeeDTO employee = employeeService.getEmployeeByEmail(email);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1);

        List<AppointmentServiceDTO> appointments = appointmentServiceService.getAppointmentsForEmployee(employee, startDate, endDate);
        List<AppointmentServiceViewModel> appointmentViewModels = appointments.stream()
                .map(appointment -> new AppointmentServiceViewModel(
                        appointment.getDateStart(),
                        appointment.getTimeStart(),
                        appointment.getClientName(),
                        appointment.getServiceName(),
                        appointment.getTotalCost()))
                .collect(Collectors.toList());

        model.addAttribute("viewModel", appointmentViewModels);
        return "EmployeeAppointment.html";
    }
}
