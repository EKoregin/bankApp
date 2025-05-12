package ru.korevg.processing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korevg.processing.dto.ExchangeMoneyDTO;
import ru.korevg.processing.dto.NewAccountDTO;
import ru.korevg.processing.dto.PutAccountMoneyDTO;
import ru.korevg.processing.mapper.AccountMapper;
import ru.korevg.processing.model.AccountEntity;
import ru.korevg.processing.service.AccountService;
import ru.korevg.processing.service.ExchangeService;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Процессинг")
@RestController
@RequiredArgsConstructor
@RequestMapping("/processing")
public class ProcessingController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final ExchangeService exchangeService;

    @PostMapping("/account")
    @Operation(description = "Создание счета пользователя")
    public NewAccountDTO createAccount(
            @Parameter(description = "Account DTO")
            @RequestBody NewAccountDTO newAccountDto) {
        var createdAccount = accountService.createAccount(newAccountDto);
        return accountMapper.toDto(createdAccount);
    }

    @PutMapping("/account/{accountId}/deposit")
    @Operation(description = "Внесение денег на счет пользователя")
    public AccountEntity putMoney(@PathVariable("accountId") Long accountId,
                                  @RequestBody PutAccountMoneyDTO data) {
        return accountService.addMoneyToAccount(data.getUid(), accountId, null, ru.korevg.processing.model.Operation.PUT, data.getAmount());
    }

    @PutMapping("/exchange/{uid}")
    @Operation(description = "Перевод денег с одного счета на другой с конвертацией валюты по курсу")
    public BigDecimal exchange(@PathVariable("uid") String uid,
                               @RequestBody ExchangeMoneyDTO data) {
        return exchangeService.exchangeCurrency(uid, data.getFromAccountId(), data.getToAccountId(), data.getAmount());
    }

    @GetMapping("/accounts/{userId}")
    @Operation(description = "Получение всех счетов пользователя")
    public List<AccountEntity> getAccountsByUserId(@PathVariable("userId") Long userId) {
        return accountService.getAllAccountsByUserId(userId);
    }
}
