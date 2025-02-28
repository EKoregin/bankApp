package ru.korevg.currency.http;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.korevg.currency.service.CbrService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CbrService cbrService;

    @GetMapping("/getByCode/{code}")
    public BigDecimal getByCode(@PathVariable("code") String code) {
        return cbrService.requestByCurrencyCode(code);
    }
}
