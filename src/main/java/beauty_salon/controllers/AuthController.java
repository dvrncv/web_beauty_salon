    package beauty_salon.controllers;

    import beauty_salon.DTO.EmployeeDTO;
    import beauty_salon.DTO.UserRegistrationDTO;
    import beauty_salon.entities.ClientEntity;
    import beauty_salon.entities.EmployeeEntity;
    import beauty_salon.service.Impl.AuthService;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.ModelAttribute;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    import viewmodel.auth.UserProfileView;

    import java.security.Principal;
    @Controller
    @RequestMapping("/users")
    public class AuthController {
        private final AuthService authService;

        @Autowired
        public AuthController(AuthService authService) {
            this.authService = authService;
        }

        @ModelAttribute("userRegistrationDto")
        public UserRegistrationDTO initForm() {
            return new UserRegistrationDTO();
        }

        @GetMapping("/register-employee")
        public String registerEmployee() {
            return "registerEmployee";
        }

        @PostMapping("/register-employee")
        public String staffRegister(UserRegistrationDTO userRegistrationDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("userRegistrationDto", userRegistrationDto);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);
                try {
                    authService.registerStaff(userRegistrationDto);
                } catch (RuntimeException e) {
                    redirectAttributes.addFlashAttribute("error", e.getMessage());
                    return "redirect:/register-employee";
                }

                return "redirect:/employee/list";
            }

            this.authService.registerStaff(userRegistrationDto);

            return "redirect:/employee/list";
        }


        @GetMapping("/register-client")
        public String registerClient() {
            return "registerClient.html";
        }

        @PostMapping("/register-client")
        public String doRegisterClient(@Valid UserRegistrationDTO userRegistrationDto,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("userRegistrationDto", userRegistrationDto);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);
                return "redirect:/users/register-client";
            }
            this.authService.registerClient(userRegistrationDto);
            return "redirect:/users/login";
        }


        @GetMapping("/login")
        public String login() {
            return "login";
        }

        @PostMapping("/login-error")
        public String onFailedLogin(
                @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                RedirectAttributes redirectAttributes) {

            redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
            redirectAttributes.addFlashAttribute("badCredentials", true);

            return "redirect:/users/login";
        }

        @GetMapping("/profile")
        public String profile(Principal principal, Model model) {
            String email = principal.getName();
            ClientEntity client = authService.getClient(email);

            UserProfileView userProfileView = new UserProfileView(
                    client.getName(),
                    client.getSurname(),
                    client.getEmail(),
                    client.getNumberPhone()
            );

            model.addAttribute("user", userProfileView);

            return "Profile.html";
        }
    }
