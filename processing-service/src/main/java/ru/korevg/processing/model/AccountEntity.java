package ru.korevg.processing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ACCOUNT")
public class AccountEntity {

    @Id
    @SequenceGenerator(name = "account_generator", sequenceName = "account_seq", allocationSize = 1)
    @GeneratedValue(generator = "account_generator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "CURRENCY_CODE", nullable = false)
    private String currencyCode;

    @Column(name = "BALANCE", nullable = false)
    private BigDecimal balance;
}
