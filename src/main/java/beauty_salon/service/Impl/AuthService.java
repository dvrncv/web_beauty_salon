package beauty_salon.service.Impl;

import beauty_salon.DTO.UserRegistrationDTO;
import beauty_salon.entities.ClientEntity;
import beauty_salon.entities.EmployeeEntity;
import beauty_salon.enums.StaffRoles;
import beauty_salon.repository.ClientRepository;
import beauty_salon.repository.UserRepository;
import beauty_salon.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, UserRoleRepository userRoleRepository, ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerStaff(UserRegistrationDTO registrationDTO) {
        System.out.println(registrationDTO.getPassword());
        System.out.println(registrationDTO.getConfirmPassword());
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new RuntimeException("passwords.match");
        }

        Optional<EmployeeEntity> byEmail = this.userRepository.findByEmail(registrationDTO.getEmail());

        if (byEmail.isPresent()) {
            throw new RuntimeException("email.used");
        }

        var userRole = userRoleRepository.
                findRoleByName(StaffRoles.EMPLOYEE).orElseThrow();

        EmployeeEntity user = new EmployeeEntity(
                registrationDTO.getName(),
                registrationDTO.getSurname(),
                registrationDTO.getNumber_phone(),
                registrationDTO.getEmail(),
                passwordEncoder.encode(registrationDTO.getPassword())
        );

        user.setRoles(List.of(userRole));

        this.userRepository.save(user);
    }
    public void registerAdmin() {
        if (userRepository.findByName("Дарья").isPresent())
            return;

        var userRole = userRoleRepository.
                findRoleByName(StaffRoles.ADMIN).orElseThrow();

        EmployeeEntity user = new EmployeeEntity(
                "Дарья",
                "Воронцова",
                "89185213201",
                "darryvoron1234@gmail.com",
                passwordEncoder.encode("123456789")
        );

        user.setRoles(List.of(userRole));

        this.userRepository.save(user);
    }

    public void registerClient(UserRegistrationDTO registrationDTO) {
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new RuntimeException("Пароли не совпадает");
        }

        Optional<ClientEntity> existingClient = this.clientRepository.findByEmail(registrationDTO.getEmail());

        if (existingClient.isPresent()) {
            throw new RuntimeException("Email уже используется");
        }

        ClientEntity client = new ClientEntity(
                registrationDTO.getName(),
                registrationDTO.getSurname(),
                registrationDTO.getNumber_phone(),
                registrationDTO.getEmail(),
                passwordEncoder.encode(registrationDTO.getPassword()),
                null,
                new ArrayList<>()
        );

        this.clientRepository.save(client);
    }

    public ClientEntity getClient (String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " не найден!"));
    }
}

