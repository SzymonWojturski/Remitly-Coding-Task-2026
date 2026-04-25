package com.szywoj.simplestock.stock.dto;

import lombok.Builder;

@Builder
public record StockDTO(
        String name,
        int quantity
) {}
