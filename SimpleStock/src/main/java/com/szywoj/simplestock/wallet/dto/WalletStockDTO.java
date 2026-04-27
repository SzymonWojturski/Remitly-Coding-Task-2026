package com.szywoj.simplestock.wallet.dto;

import lombok.Builder;

@Builder
public record WalletStockDTO(
    String name,
    long quantity
) {}
