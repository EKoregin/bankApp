package ru.korevg.processing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.korevg.processing.model.AccountEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountOperationEventListener {

    private final AccountEventSendingService accountEventSendingService;

    @TransactionalEventListener
    public void handleEvent(AccountEvent event) {
        log.info("Received account event: {}", event);
        accountEventSendingService.sendAccountEvent(event);
    }
}
