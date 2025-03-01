package ru.korevg.processing.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeMoneyDTO {

    @JsonAlias("uid")
    private String exchangeUid;

    @JsonAlias("from")
    private Long fromAccountId;

    @JsonAlias("to")
    private Long toAccountId;

    @JsonAlias("money")
    private BigDecimal amount;
}
