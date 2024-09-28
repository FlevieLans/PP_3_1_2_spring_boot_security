package application.pp_3_1_2_spring_boot_security.config;


import application.pp_3_1_2_spring_boot_security.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserServiceImpl userService; // Внедрение вашего UserService
    private final AuthenticationSuccessHandler loginSuccessHandler;

    public WebSecurityConfig(UserServiceImpl userService, LoginSuccessHandler loginSuccessHandler) {
        this.userService = userService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    // Конфигурация SecurityFilterChain для обработки безопасности
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Отключаем CSRF (если необходимо)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Доступ для администраторов
                        .requestMatchers("/user").hasRole("USER") // Доступ для пользователей с ролью USER
                        .requestMatchers("/", "/resources/**").permitAll() // Общедоступные страницы
                        .anyRequest().authenticated() // Все остальные требуют аутентификации
                )
                .formLogin(form -> form
                        .successHandler(loginSuccessHandler)
                        .permitAll() // Используем встроенную страницу логина Spring Security
                )
                .logout(logout -> logout
                        .permitAll()
                        .logoutSuccessUrl("/") // Перенаправление после выхода
                );

        return http.build();
    }

    // Конфигурация для AuthenticationManager без шифрования паролей
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService) // Без passwordEncoder, поскольку шифрование отключено
                .and()
                .build();
    }
}
