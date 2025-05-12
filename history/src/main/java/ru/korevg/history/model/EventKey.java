package ru.korevg.history.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventKey implements Serializable {
    private String uuid;
    private Long accountId;
}
