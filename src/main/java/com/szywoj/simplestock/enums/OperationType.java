package com.szywoj.simplestock.enums;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum OperationType {
    @JsonProperty("buy")
    BUY,
    @JsonProperty("sell")
    SELL
}
