package ru.korevg.history.consumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.korevg.history.model.AccountEvent;

import java.util.stream.Collectors;

@Slf4j
public class AccountEventDeserializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AccountEventDeserializationTest() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void testDeserializeAccountEvent() {
        String jsonString = "{\"uuid\":\"831a950c-2ae9-458a-a37a-4411382695d3\",\"accountId\":2,\"userId\":1,\"fromAccount\":null,\"currency\":\"USD\",\"operation\":\"PUT\",\"amount\":150,\"created\":\"2025-04-16T12:22:50.159+00:00\"}";

        try {
            AccountEvent event = objectMapper.readValue(jsonString, AccountEvent.class);
            log.info("Deserialized event: {}", event);
        } catch (Exception e) {
            log.error("Deserialization error: {}", e.getMessage());
            if (e instanceof JsonMappingException) {
                JsonMappingException jme = (JsonMappingException) e;
                log.error("Field causing the error: {}", jme.getPath().stream()
                        .map(ref -> ref.getFieldName())
                        .collect(Collectors.joining(" -> ")));
            }
            throw new RuntimeException(e);
        }
    }
}
