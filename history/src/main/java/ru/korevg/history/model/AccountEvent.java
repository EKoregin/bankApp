package ru.korevg.history.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@IdClass(EventKey.class)
public class AccountEvent {
    @Id
    @Column(name = "uid", nullable = false)
    private String uuid;

    @Id
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "from_account")
    private Long fromAccount;

    @Column(name = "currency_code", length = 3, nullable = false)
    private String currency;

    @Column(name = "operation_code", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Operation operation;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
}

