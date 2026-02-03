/*package com.example.taskmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // disable CSRF for testing
                .authorizeHttpRequests()
                .requestMatchers("/h2-console/**", "/login", "/register", "/css/**", "/js/**").permitAll() // public URLs
                .anyRequest().authenticated() // all other URLs need login
                .and()
                .formLogin()
                .defaultSuccessUrl("/dashboard", true) // redirect after login
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .and()
                .headers()
                .frameOptions().disable(); // allow H2 console in a frame

        return http.build();
    }
}
*/