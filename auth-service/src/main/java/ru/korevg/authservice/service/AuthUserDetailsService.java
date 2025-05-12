package ru.korevg.authservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.korevg.authservice.repository.AuthUserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Find UserDetails by Username {}", username);
        return authUserRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Username {} not found", username);
                    return new UsernameNotFoundException(username);
                });
    }
}
