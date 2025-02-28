package ru.korevg.currency.http;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.korevg.currency.service.CbrService;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CbrService cbrService;

    @GetMapping("/getCurrency")
    public BigDecimal getCurrency(
            @RequestParam("code") String code,
            @RequestParam(required = false) LocalDate onDate) {
        return cbrService.requestByCurrencyCodeAndDate(onDate, code.toUpperCase());
    }
}
