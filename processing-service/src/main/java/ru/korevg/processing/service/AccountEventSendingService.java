package ru.korevg.processing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.korevg.processing.model.AccountEvent;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountEventSendingService {

    private final KafkaTemplate<Long, Object> kafkaTemplate;
    private final ObjectMapper mapper;

    @Value("${spring.kafka.topics.account.name}")
    private String topic;

    public void sendAccountEvent(AccountEvent accountEvent) {
        long accountId = accountEvent.getAccountId();
        String message;
        try {
            message = mapper.writeValueAsString(accountEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        var future = kafkaTemplate.send(topic, accountId, message);
        try {
            future.get();
            log.info("Account event sent message: {} to topic {}", message, topic);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
