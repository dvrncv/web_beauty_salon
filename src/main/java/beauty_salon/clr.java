package beauty_salon;

import beauty_salon.entities.Role;
import beauty_salon.enums.StaffRoles;
import beauty_salon.repository.UserRoleRepository;
import beauty_salon.service.Impl.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class clr implements CommandLineRunner {
    private final UserRoleRepository userRoleRepository;
    private final AuthService authService;

    public clr(UserRoleRepository userRoleRepository, AuthService authService) {
        this.userRoleRepository = userRoleRepository;
        this.authService = authService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRoleRepository.existsRoleByName(StaffRoles.EMPLOYEE)) {
            userRoleRepository.save(new Role(StaffRoles.EMPLOYEE));

        }
        if (!userRoleRepository.existsRoleByName(StaffRoles.ADMIN)) {
            userRoleRepository.save(new Role(StaffRoles.ADMIN));
        }
        authService.registerAdmin();
    }
}
