package beauty_salon.controllers;

import beauty_salon.DTO.AppointmentServiceDTO;
import beauty_salon.DTO.EmployeeDTO;
import beauty_salon.service.AppointmentServiceService;
import beauty_salon.service.BookingService;
import beauty_salon.service.LoyaltyCardService;
import controllers.AppointmentServiceController;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


@Controller
public class AppointmentServiceControllerImpl implements AppointmentServiceController {
    private final BookingService bookingService;
    private final AppointmentServiceService appointmentServiceService;
    private final LoyaltyCardService loyaltyCardService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);


    public AppointmentServiceControllerImpl(BookingService bookingService, AppointmentServiceService appointmentServiceService, LoyaltyCardService loyaltyCardService) {
        this.bookingService = bookingService;
        this.appointmentServiceService = appointmentServiceService;
        this.loyaltyCardService = loyaltyCardService;
    }

    @Override
    public String showServiceAppointment (@PathVariable Long serviceId,
                                          @RequestParam(name = "date", required = false) String dateStr,
                                          Principal principal, Model model) {
        LOG.info("GET:/booking/service/{serviceId} Show appointment service request from " + principal.getName());

        LocalDate date = (dateStr != null) ? LocalDate.parse(dateStr) : LocalDate.now();
        Map<EmployeeDTO, List<LocalTime>> availableAppointments = bookingService.getAvailableEmployeesAndTimes(serviceId, date);

        model.addAttribute("serviceId", serviceId);
        model.addAttribute("date", date);
        model.addAttribute("availabilityMap", availableAppointments);
        return "ChooseEmployee.html";
    }

    @Override
    public String appointmentService (@PathVariable Long serviceId,
                                      @RequestParam Long employeeId,
                                      @RequestParam String date,
                                      HttpServletRequest request,
                                      Model model, Principal principal) {
        LOG.info("POST:/booking/service/{serviceId} Create appointment service request from " + principal.getName());

        LocalDate selectedDate = LocalDate.parse(date);
        String timeParamName = employeeId + "_time";
        String timeStr = request.getParameter(timeParamName);
        LocalTime selectedTime;
        selectedTime = LocalTime.parse(timeStr);
        AppointmentServiceDTO appointment = bookingService.createAppointment(serviceId, employeeId, principal, selectedDate, selectedTime);
        model.addAttribute("appointment", appointment);
        return "AppointmentSuccess.html";
    }

    @Override
    public String getClientAppointments(Principal principal, Model model) {
        LOG.info("GET:/booking/client-appointments Get client appointments service request from " + principal.getName());

        Map<String, List<AppointmentServiceDTO>> appointmentsByMonth = appointmentServiceService.getAppointmentsGroupedByMonth();
        model.addAttribute("appointmentsGroupedByMonth", appointmentsByMonth);
        return "AdminAppointments.html";
    }

    @Override
    public String addPoints(@RequestParam Long clientId,
                            @RequestParam Long appointmentId,
                            Principal principal, Model model) {
        LOG.info("POST:/booking/add-points Add points appointment service request from " + principal.getName());

        loyaltyCardService.addPointsToLoyaltyCard(clientId, appointmentId);
        model.addAttribute("message", "Баланс успешно пополнен");
        return "redirect:/booking/client-appointments";
    }
}
