package ru.korevg.currency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.korevg.currency.service.CbrService;

import java.math.BigDecimal;
import java.time.LocalDate;

@Tag(name = "Валюты")
@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final CbrService currencyService;

    @GetMapping("/getCurrency")
    @Operation(description = "Получение курса валюты по коду на требуемую дату")
    public BigDecimal getCurrency(
            @Parameter(description = "код валюты (USD)", required = true)
            @RequestParam("code") String code,
            @Parameter(description = "курс на дату")
            @RequestParam(required = false) LocalDate onDate) {
        return currencyService.requestByCurrencyCodeAndDate(onDate, code.toUpperCase());
    }
}
