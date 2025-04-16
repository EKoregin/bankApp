package ru.korevg.history.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korevg.history.model.AccountEvent;
import ru.korevg.history.model.EventKey;

public interface AccountEventRepository extends JpaRepository<AccountEvent, EventKey> {
}
