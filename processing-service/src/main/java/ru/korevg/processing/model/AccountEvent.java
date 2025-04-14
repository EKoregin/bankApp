package ru.korevg.processing.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class AccountEvent {
    @NonNull
    private String uuid;

    private long userId, accountId;

    private Long fromAccount;

    @NonNull
    private String currency;

    @NonNull
    private Operation operation;

    @NonNull
    private BigDecimal amount;

    @NotNull
    private Date created;

}
