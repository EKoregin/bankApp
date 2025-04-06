package ru.korevg.clientapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.korevg.clientapp.config.TokenFetcher;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/token")
public class TokenController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
    private final TokenFetcher tokenFetcher;

    @GetMapping
    public String getToken(Authentication authentication, Model model) {
        var token = tokenFetcher.fetchToken(null);
        model.addAttribute("username", authentication.getName());
        model.addAttribute("token", "Bearer " + token.getTokenValue());
        model.addAttribute("tokenType", token.getTokenType().getValue());
        model.addAttribute("expiresAt", token.getExpiresAt().atZone(ZoneId.of("Europe/Moscow")).format(FORMATTER));
        return "token";
    }
}
