package ru.korevg.notificationservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountEvent {
    private String uuid;
    private Long accountId;
    private Long userId;
    private Long fromAccount;
    private String currency;
    private String operation;
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime created;
}
