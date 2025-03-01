package ru.korevg.processing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "currency-service", url = "${currency-service.host}:${currency-service.port}")
public interface CurrencyServiceClient {

    @GetMapping("/currency/v1/getCurrency")
    BigDecimal getCurrencyRate(@RequestParam("code") String code);
}
