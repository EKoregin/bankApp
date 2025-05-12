package ru.korevg.clientapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;

@FeignClient(name = "currency-service", url = "${currency-service.host}:${cloud.currency-port}")
public interface CurrencyServiceClient {

    @GetMapping("/currency/getCurrency")
    BigDecimal getCurrencyRate(@RequestParam("code") String code,
                               @RequestParam(required = false) LocalDate onDate,
                               @RequestHeader(value = "Authorization", required = false) String authToken);
}
