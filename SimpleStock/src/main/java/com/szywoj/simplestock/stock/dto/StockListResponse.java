package com.szywoj.simplestock.stock.dto;

import java.util.List;

public record StockListResponse(
        List<StockDTO> stockList
) {
}
