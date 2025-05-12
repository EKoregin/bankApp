package ru.korevg.history.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.korevg.history.model.AccountEvent;
import ru.korevg.history.service.AccountEventService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/history")
public class AccountEventController {

    private final AccountEventService accountEventService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/account")
    public ResponseEntity<List<AccountEvent>> getAllAccountEvents(@AuthenticationPrincipal Jwt jwt) {
        log.info("Auth Token {}:", jwt);
        log.info("JWT Claims: {}", jwt.getClaims());
        String userIdStr = jwt.getClaimAsString("userId");
        log.info("User ID: {}", userIdStr);

        if (userIdStr == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is missing in JWT");
        }

        long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User ID format: " + userIdStr);
        }

        if (userId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID must be positive");
        }

        try {
            List<AccountEvent> result = accountEventService.getAccountEventsByUserId(userId);
            log.info("Account Events Found: size {} events {}", result.size(), result);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error retrieving account events for userId: {}", userId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving account events", e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/account/{id}")
    public ResponseEntity<List<AccountEvent>> getAccountEvents(@PathVariable(name = "id") long accountId,
                                                               @AuthenticationPrincipal Jwt jwt) {
        log.info("Auth Token {}:", jwt);
        log.info("JWT Claims: {}", jwt.getClaims());
        String userIdStr = jwt.getClaimAsString("userId");
        log.info("User ID: {}", userIdStr);

        if (userIdStr == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is missing in JWT");
        }

        long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User ID format: " + userIdStr);
        }

        if (userId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID must be positive");
        }

        try {
            List<AccountEvent> result = accountEventService.getAccountEventsByAccountId(accountId, userId);
            log.info("Account Events Found: size {} events {}", result.size(), result);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error retrieving account events for accountId: {}, userId: {}", accountId, userId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving account events", e);
        }
    }
}
