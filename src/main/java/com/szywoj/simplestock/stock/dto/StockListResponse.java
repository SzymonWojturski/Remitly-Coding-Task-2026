package com.szywoj.simplestock.stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record StockListResponse(
        @JsonProperty("stocks")
        List<StockDTO> stockList
) {
}
