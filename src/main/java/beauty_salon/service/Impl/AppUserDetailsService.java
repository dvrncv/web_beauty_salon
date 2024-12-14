package beauty_salon.service.Impl;

import beauty_salon.entities.ClientEntity;
import beauty_salon.entities.EmployeeEntity;
import beauty_salon.repository.ClientRepository;
import beauty_salon.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    public AppUserDetailsService(UserRepository userRepository, ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<EmployeeEntity> employee = userRepository.findByEmail(name);
        if (employee.isPresent()){
            String role ="ROLE_" + employee.get().getRoles().get(0).getName().name();
            System.out.println("ТЕКУЩАЯ РОЛЬ " + role);
            return new User(

                    employee.get().getEmail(),
                    employee.get().getPassword(),
                    List.of(new SimpleGrantedAuthority(role))

            );
        }
        else {
            Optional<ClientEntity> client = clientRepository.findByEmail(name);
            if(client.isPresent()){
                return new User (
                        client.get().getEmail(),
                        client.get().getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_CLIENT"))
                );
            }
        }
        throw new UsernameNotFoundException("Такого нот фаунд");
    }
}
