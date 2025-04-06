package ru.korevg.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.korevg.authservice.model.UserEntity;
import ru.korevg.authservice.repository.AuthUserRepository;

@Component
@RequiredArgsConstructor
public class AuthUsersLoader implements CommandLineRunner {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        authUserRepository.save(
                UserEntity.builder()
                        .userId(1L)
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .build());
        authUserRepository.save(
                UserEntity.builder()
                        .userId(2L)
                        .username("mnemonic")
                        .password(passwordEncoder.encode("mnemonic"))
                        .build());
    }
}
