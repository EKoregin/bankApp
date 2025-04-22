package ru.korevg.clientapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.korevg.clientapp.client.HistoryServiceClient;
import ru.korevg.clientapp.model.AccountEvent;

import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/bank/history")
public class HistoryController {

    private final HistoryServiceClient historyServiceClient;

    @GetMapping
    public String getUserHistory(Model model,
                                 @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getClaimAsString("userId");
        String tokenValue = jwt.getTokenValue();
        log.info("Fetching history for userId: {}", userId);
        try {
            ResponseEntity<List<AccountEvent>> response = historyServiceClient.getAllAccountEvents("Bearer " + tokenValue);

            List<AccountEvent> events = response.getBody() != null
                    ? response.getBody()
                    : Collections.emptyList();

            log.info("Received {} events for userId: {}", events.size(), userId);

            // Передача данных в модель для Thymeleaf
            model.addAttribute("events", events);
            model.addAttribute("userId", userId);
        } catch (Exception e) {
            log.error("Error fetching history for userId: {}", userId, e);
            model.addAttribute("error", "Failed to load history: " + e.getMessage());
            model.addAttribute("events", Collections.emptyList());
        }

        return "history";
    }
}
