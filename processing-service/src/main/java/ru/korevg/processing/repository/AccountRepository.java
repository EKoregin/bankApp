package ru.korevg.processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korevg.processing.model.AccountEntity;

import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findByUserId(Long userId);
}
