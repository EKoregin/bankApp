package ru.korevg.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {


    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${history-service.url}")
    private String historyServiceUrl;


    @Override
    public String getBotUsername() {
        return botUsername;
    }


    @Override
    public void onUpdateReceived(Update update) {

    }
}
