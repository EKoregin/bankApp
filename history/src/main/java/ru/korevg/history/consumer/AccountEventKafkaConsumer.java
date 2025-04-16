package ru.korevg.history.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import ru.korevg.history.model.AccountEvent;
import ru.korevg.history.repository.AccountEventRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountEventKafkaConsumer {

    private final AccountEventRepository accountEventRepository;

    @KafkaListener(topics = "${spring.kafka.topics.account.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenAccountEvents(AccountEvent accountEvent, Acknowledgment ack) {
        log.info("Received AccountEvent {}", accountEvent);
        accountEventRepository.save(accountEvent);
        ack.acknowledge();
    }
}
