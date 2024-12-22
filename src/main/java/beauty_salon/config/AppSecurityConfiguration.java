package beauty_salon.config;

import beauty_salon.enums.StaffRoles;
import beauty_salon.repository.ClientRepository;
import beauty_salon.repository.UserRepository;
import beauty_salon.service.Impl.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class AppSecurityConfiguration {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;


    public AppSecurityConfiguration(UserRepository userRepository, ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityContextRepository securityContextRepository) throws Exception {
        http
                .authorizeHttpRequests(
                        authorizeHttpRequests ->
                                authorizeHttpRequests.
                                        requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                        .permitAll()
                                        .requestMatchers("/favicon.ico").permitAll()
                                        .requestMatchers("/error").permitAll()
                                        .requestMatchers("/", "/users/login", "/users/logout", "/users/register-client", "/users/login-error")
                                        .permitAll().
                                        requestMatchers("/users/profile").authenticated().
                                        requestMatchers("/employee/create/grafik", "/employee/grafik","/employee/appointment").hasRole(StaffRoles.EMPLOYEE.name()).
                                        requestMatchers("/service/list","/service/create","/service/update/{id}","/category/list", "/category/create", "/category/update/{id}", "/employee/list", "/users/register-employee", "/loyalty/list", "/loyalty/create","/booking/client-appointments","/booking/add-points").hasRole(StaffRoles.ADMIN.name()).
                                        requestMatchers("/service/{id}","/catalog", "/catalog/service/{serviceId}","/catalog/category/{categoryId}","/users/profile", "/loyalty/card", "/home", "/booking/service/{serviceId}/appoinment", "booking/service/{serviceId}").hasRole("CLIENT").
                                        anyRequest().authenticated()

                )
                .formLogin(
                        (formLogin) ->
                                formLogin.
                                        loginPage("/users/login").
                                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                                        successHandler((request, response, authentication) -> {
                                            String redirectUrl = "/home";
                                            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                                                redirectUrl = "/booking/client-appointments";
                                            }
                                            else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
                                                redirectUrl = "/employee/grafik";
                                            }
                                            response.sendRedirect(redirectUrl);
                                        }).
                                        failureForwardUrl("/users/login-error")
                )
                .logout((logout) ->
                        logout.logoutUrl("/users/logout").
                                logoutSuccessUrl("/home").
                                invalidateHttpSession(true)
                ).securityContext(
                        securityContext -> securityContext.
                                securityContextRepository(securityContextRepository)
                )
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(new LoggingAccessDeniedHandler()));

        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService(userRepository, clientRepository);
    }
}
