package ru.korevg.processing.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewAccountDTO {

    private Long id;

    @JsonAlias("user")
    private Long userId;

    @JsonAlias("currency")
    private String currencyCode;

    private BigDecimal balance;
}
