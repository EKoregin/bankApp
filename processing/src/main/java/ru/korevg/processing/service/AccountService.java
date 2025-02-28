package ru.korevg.processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korevg.processing.dto.NewAccountDTO;
import ru.korevg.processing.mapper.AccountMapper;
import ru.korevg.processing.model.AccountEntity;
import ru.korevg.processing.repository.AccountRepository;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    public final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    public AccountEntity createAccount(NewAccountDTO dto) {
        var account = accountMapper.toEntity(dto);
        account.setBalance(BigDecimal.ZERO);
        return accountRepository.save(account);
    }

    @Transactional
    public AccountEntity addMoneyToAccount(String uid, Long accountId,  BigDecimal amount) {
        var account = accountRepository.findById(accountId);

        return account.map(acc -> {
            acc.setBalance(acc.getBalance().add(amount));
            return accountRepository.save(acc);
        }).orElseThrow(() -> new IllegalArgumentException("Account with ID " + accountId + " not found"));
    }
}
