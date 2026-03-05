package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //disable CSRF since we are using API
                // (JWT in Authorization header isn't vulnerable the same way as cookies)
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        //public endpoints
                        .requestMatchers("/", "/login","/favicon.ico", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )

                // Enables JWT validation (uses jwk-set-uri from application.properties)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}