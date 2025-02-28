package ru.korevg.processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korevg.processing.model.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
