package ru.korevg.processing.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AccountEvent {
    @NonNull
    private String uuid;

    private Long accountId;

    private Long userId;

    private Long fromAccount;

    @NonNull
    private String currency;

    @NonNull
    private Operation operation;

    @NonNull
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime created;

}
