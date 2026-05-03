package com.szywoj.simplestock.stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record StockListRequest(
        @JsonProperty("stocks")
        List<StockDTO> stockList
) {
}
