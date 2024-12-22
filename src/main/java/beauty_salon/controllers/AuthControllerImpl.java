package beauty_salon.controllers;

import beauty_salon.DTO.UserRegistrationDTO;
import beauty_salon.entities.ClientEntity;
import beauty_salon.service.Impl.AuthService;
import controllers.AuthController;
import form.UserRegistrationInputModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import viewmodel.BaseViewModel;
import viewmodel.auth.UserProfileView;

import java.security.Principal;

@Controller
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @ModelAttribute("userRegistrationDto")
    public UserRegistrationDTO initForm() {
        return new UserRegistrationDTO();
    }

    @Override
    public String registerEmployee(Principal principal) {
        LOG.info("GET:/auth/register-employee Get register employee request from " + principal.getName());
        return "registerEmployee";
    }

    @Override
    public String staffRegister(UserRegistrationInputModel userRegistrationInputModel,
                                BindingResult bindingResult,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {
        LOG.info("POST:/auth/register-employee Get register employee request from " + principal.getName());

        UserRegistrationDTO userRegistrationDto = new UserRegistrationDTO();
        userRegistrationDto.setName(userRegistrationInputModel.getName());
        userRegistrationDto.setSurname(userRegistrationInputModel.getSurname());
        userRegistrationDto.setNumber_phone(userRegistrationInputModel.getNumberPhone());
        userRegistrationDto.setEmail(userRegistrationInputModel.getEmail());
        userRegistrationDto.setPassword(userRegistrationInputModel.getPassword());
        userRegistrationDto.setConfirmPassword(userRegistrationInputModel.getConfirmPassword());

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDto", userRegistrationInputModel);
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


    @Override
    public String registerClient(Principal principal) {
        LOG.info("GET:/auth/register-client Get register client request from " + principal.getName());

        return "registerClient.html";
    }

    @Override
    public String doRegisterClient(@Valid UserRegistrationInputModel userRegistrationInputModel,
                                   BindingResult bindingResult,
                                   Principal principal,
                                   RedirectAttributes redirectAttributes) {
        LOG.info("POST:/auth/register-client Get register client request from " + principal.getName());

        UserRegistrationDTO userRegistrationDto = new UserRegistrationDTO();
        userRegistrationDto.setName(userRegistrationInputModel.getName());
        userRegistrationDto.setSurname(userRegistrationInputModel.getSurname());
        userRegistrationDto.setNumber_phone(userRegistrationInputModel.getNumberPhone());
        userRegistrationDto.setEmail(userRegistrationInputModel.getEmail());
        userRegistrationDto.setPassword(userRegistrationInputModel.getPassword());
        userRegistrationDto.setConfirmPassword(userRegistrationInputModel.getConfirmPassword());

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDto", userRegistrationInputModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);
            return "redirect:/users/register-client";
        }
        this.authService.registerClient(userRegistrationDto);
        return "redirect:/users/login";
    }


    @Override
    public String login() {
        LOG.info("GET:/auth/login Get login request from ");
        return "login";
    }

    @Override
    public String onFailedLogin(String username,RedirectAttributes redirectAttributes) {
        LOG.info("GET:/auth/login-error Get login-error request from ");

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }

    @Override
    public String profile(Principal principal, Model model) {
        LOG.info("GET:/auth/profile Get profile user request from " + principal.getName());

        String email = principal.getName();
        ClientEntity client = authService.getClient(email);

        BaseViewModel salon = new BaseViewModel("Beauty people", "Всегда делаем красоту для вас!");
        model.addAttribute("salon", salon);

        UserProfileView userProfileView = new UserProfileView(
                client.getName(),
                client.getEmail(),
                client.getSurname(),
                client.getNumberPhone()
        );

        model.addAttribute("user", userProfileView);

        return "Profile.html";
    }
}
