package ru.korevg.clientapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.korevg.clientapp.model.AccountEvent;

import java.util.List;

@FeignClient(name = "history-service", url = "${cloud.history-service.url}")
public interface HistoryServiceClient {

    @GetMapping("/history/account/{id}")
    ResponseEntity<List<AccountEvent>> getAccountEvents(@PathVariable(name = "id") long accountId,
                                                        @AuthenticationPrincipal Jwt jwt,
                                                        @RequestHeader(value = "Authorization", required = false) String authToken);

    @GetMapping("/history/account")
    ResponseEntity<List<AccountEvent>> getAllAccountEvents(@RequestHeader(value = "Authorization", required = false) String authToken);
}
