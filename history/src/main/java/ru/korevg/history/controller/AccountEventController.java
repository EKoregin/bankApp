package ru.korevg.history.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korevg.history.model.AccountEvent;
import ru.korevg.history.service.AccountEventService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/history")
public class AccountEventController {

    private final AccountEventService accountEventService;

    @GetMapping("/account/{id}")
    public List<AccountEvent> getAccountEvents(@PathVariable(name = "id") long accountId,
                                               @AuthenticationPrincipal Jwt jwt) {
        log.info("Auth Token {}:", jwt);
        String userId = jwt.getClaimAsString("userId");
        log.info("User ID: {}", userId);
        log.info("JWT Claims: {}", jwt.getClaims());
        var result = accountEventService.getAccountEventsByAccountId(accountId, Long.valueOf(userId));
        log.info("Account Events Found: size {} events {}", result.size(), result);
        return result;
    }
}
