package ru.korevg.clientapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.korevg.clientapp.client.CurrencyServiceClient;
import ru.korevg.clientapp.config.TokenFetcher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/bank")
public class BankController {

    private final CurrencyServiceClient currencyClient;
    private final TokenFetcher tokenFetcher;

    @GetMapping
    public String index(Model model) {
        List<String> links = new ArrayList<>();
        links.add("http://localhost:8080/bank/currency");
        links.add("http://localhost:8080/bank/token");

        model.addAttribute("links", links);
        return "index";
    }

    @GetMapping("/currency")
    public String currency() {
        return "currency";
    }

    @PostMapping("/getCurrencyRank")
    public String getCurrencyRank(@RequestParam("date") LocalDate date,
                                  @RequestParam("currencyCode") String currencyCode,
                                  Model model) {
        String authToken = "Bearer " + tokenFetcher.fetchToken(null).getTokenValue();
        log.info("Fetched auth token: {}", authToken);
        var currentRank = currencyClient.getCurrencyRate(currencyCode, date, authToken);

        model.addAttribute("date", date);
        model.addAttribute("currencyCode", currencyCode);
        model.addAttribute("rank", currentRank);
        return "resultRank";
    }
}
