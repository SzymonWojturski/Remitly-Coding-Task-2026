package com.szywoj.simplestock.wallet.dto;

import java.util.List;

public record WalletResponse(
    String id,
    List<WalletStockDTO> stocks
) {}