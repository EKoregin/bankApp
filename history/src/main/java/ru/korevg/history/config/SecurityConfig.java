package ru.korevg.history.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${keySetURI}")
    private String keySetURI;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(
                c-> c.jwt(j->j.jwkSetUri(keySetURI))
        );

        http.authorizeHttpRequests(c->c.anyRequest().authenticated());
        return http.build();
    }
}
