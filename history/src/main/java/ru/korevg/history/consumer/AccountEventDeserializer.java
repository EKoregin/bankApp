package ru.korevg.history.consumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import ru.korevg.history.model.AccountEvent;

import java.util.stream.Collectors;

@Slf4j
public class AccountEventDeserializer implements Deserializer<AccountEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AccountEventDeserializer() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public AccountEvent deserialize(String topic, byte[] data) {
        if (data == null) return null;
        try {
            String jsonString = new String(data, "UTF-8");
            log.info("Deserializing JSON: {}", jsonString);
            return objectMapper.readValue(jsonString, AccountEvent.class);
        } catch (Exception e) {
            log.error("Error deserializing JSON to AccountEvent: {}", e.getMessage());
            if (e instanceof JsonMappingException) {
                JsonMappingException jme = (JsonMappingException) e;
                log.error("Field causing the error: {}", jme.getPath().stream()
                        .map(ref -> ref.getFieldName())
                        .collect(Collectors.joining(" -> ")));
            }
            throw new RuntimeException("Error deserializing JSON to AccountEvent", e);
        }
    }
}
