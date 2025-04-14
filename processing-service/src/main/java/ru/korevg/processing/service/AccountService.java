package ru.korevg.processing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.korevg.processing.dto.NewAccountDTO;
import ru.korevg.processing.mapper.AccountMapper;
import ru.korevg.processing.model.AccountEntity;
import ru.korevg.processing.model.AccountEvent;
import ru.korevg.processing.model.Operation;
import ru.korevg.processing.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public AccountEntity createAccount(NewAccountDTO dto) {
        var account = accountMapper.toEntity(dto);
        account.setBalance(BigDecimal.ZERO);
        return accountRepository.save(account);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AccountEntity addMoneyToAccount(String uid, Long accountId, Long targetAccount, Operation operation, BigDecimal amount) {
        log.info("Add money to account {}", accountId);
        var account = accountRepository.findById(accountId);

        return account.map(acc -> {
            acc.setBalance(acc.getBalance().add(amount));
            eventPublisher.publishEvent(createEvent(uid, acc, targetAccount, operation, amount));
            return accountRepository.save(acc);
        }).orElseThrow(() -> new IllegalArgumentException("Account with ID " + accountId + " not found"));
    }

    public AccountEntity getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() ->
                new IllegalArgumentException("Account with ID " + accountId + " not found"));
    }

    public List<AccountEntity> getAllAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    private AccountEvent createEvent(String uid, AccountEntity acc, Long targetId, Operation operation, BigDecimal amount) {
        var current = new Date();
        return AccountEvent.builder()
                .uuid(uid)
                .accountId(acc.getId())
                .currency(acc.getCurrencyCode())
                .operation(operation)
                .fromAccount(targetId)
                .amount(amount)
                .userId(acc.getUserId())
                .created(current)
                .build();
    }
}
