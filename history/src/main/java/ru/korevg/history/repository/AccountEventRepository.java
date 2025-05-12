package ru.korevg.history.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korevg.history.model.AccountEvent;
import ru.korevg.history.model.EventKey;

import java.util.List;

public interface AccountEventRepository extends JpaRepository<AccountEvent, EventKey> {
    List<AccountEvent> findAllByAccountIdAndUserIdOrderByCreatedDesc(Long accountId, Long userId);

    List<AccountEvent> findAllByUserIdOrderByCreatedDesc(Long userId);
}
