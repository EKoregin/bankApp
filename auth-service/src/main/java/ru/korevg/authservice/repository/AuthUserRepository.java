package ru.korevg.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korevg.authservice.model.UserEntity;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
