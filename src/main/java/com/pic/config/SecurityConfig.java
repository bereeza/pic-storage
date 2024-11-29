package com.pic.config;

import com.pic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService oauthService;

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/api/v1").permitAll();
                            auth.anyRequest().authenticated();
                        }
                )
                .oauth2Login(
                        oauth -> oauth.userInfoEndpoint(info ->
                                info.userService(oauthService))
                )
                .build();
    }
}
