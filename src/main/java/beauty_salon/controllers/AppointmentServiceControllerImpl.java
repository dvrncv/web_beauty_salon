//package beauty_salon.controllers;
//
//import beauty_salon.DTO.EmployeeDTO;
//import beauty_salon.DTO.LoyaltyCardDTO;
//import beauty_salon.DTO.ServiceDTO;
//import beauty_salon.service.EmployeeService;
//import beauty_salon.service.LoyaltyCardService;
//import beauty_salon.service.ServiceService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import viewmodel.employee.EmployeePickViewModel;
//import viewmodel.loyaltyCard.LoyaltyCardViewModel;
//import viewmodel.service.CardServiceViewModel;
//import viewmodel.service.ServiceViewModel;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/service")
//public class AppointmentServiceControllerImpl {
//    private final ServiceService serviceService;
//    private final EmployeeService employeeService;
//    private final LoyaltyCardService loyaltyCardService;
//
//    public ServiceController(ServiceService serviceService, EmployeeService employeeService, LoyaltyCardService loyaltyCardService) {
//        this.serviceService = serviceService;
//        this.employeeService = employeeService;
//        this.loyaltyCardService = loyaltyCardService;
//    }
//
//    @GetMapping("service/{serviceId}")
//    public ResponseEntity<ServiceViewModel> getServiceDetails(@PathVariable Long serviceId) {
//        // Получаем информацию о услуге
//        ServiceDTO service = serviceService.getServiceById(serviceId);
//
//        // Получаем список доступных сотрудников для услуги
//        List<EmployeeDTO> availableEmployees = employeeService.getAvailableEmployeesByServiceId(serviceId);
//
//        // Получаем данные о карте лояльности для клиента, если нужно
//        LoyaltyCardDTO loyaltyCard = loyaltyCardService.getLoyaltyCardForClient(service.getLoyaltyCardId());
//
//        // Сформируем список EmployeePickViewModel для отображения на фронтенде
//        List<EmployeePickViewModel> employeePickViewModels = availableEmployees.stream()
//                .map(employee -> new EmployeePickViewModel(employee.getId(), employee.getName(), employee.getSurname()))
//                .collect(Collectors.toList());
//
//        // Заполняем модель представления
//        ServiceViewModel serviceViewModel = new ServiceViewModel(
//                employeePickViewModels,
//                new CardServiceViewModel(service.getCategoryId(), service.getCategoryName()),
//                new LoyaltyCardViewModel(loyaltyCard.getNumberCard(), loyaltyCard.getBalancePoint())
//        );
//
//        return ResponseEntity.ok(serviceViewModel);
//    }
//}
