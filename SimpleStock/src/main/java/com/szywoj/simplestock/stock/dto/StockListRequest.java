package com.szywoj.simplestock.stock.dto;

import java.util.List;

public record StockListRequest(
        List<StockDTO> stockList
) {
}
