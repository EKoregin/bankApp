package ru.korevg.history.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.korevg.history.model.AccountEvent;
import ru.korevg.history.repository.AccountEventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountEventService {

    private final AccountEventRepository accountEventRepository;

    public List<AccountEvent> getAccountEventsByAccountId(Long accountId, Long userId) {
        return accountEventRepository.findAllByAccountIdAndUserIdOrderByCreatedDesc(accountId, userId);
    }

    public List<AccountEvent> getAccountEventsByUserId(Long userId) {
        return accountEventRepository.findAllByUserIdOrderByCreatedDesc(userId);
    }
}
