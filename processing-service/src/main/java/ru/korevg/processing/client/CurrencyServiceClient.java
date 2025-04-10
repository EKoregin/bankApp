package ru.korevg.processing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "currency-service", url = "${currency-service.host}:${cloud.currency-port}")
public interface CurrencyServiceClient {

    @GetMapping("/currency/getCurrency")
    BigDecimal getCurrencyRate(@RequestParam("code") String code,
                               @RequestHeader(value = "Authorization", required = false) String authToken);
}
