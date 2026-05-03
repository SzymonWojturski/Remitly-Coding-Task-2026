package com.szywoj.simplestock.stock;

import com.szywoj.simplestock.stock.dto.StockDTO;
import com.szywoj.simplestock.stock.dto.StockListRequest;
import com.szywoj.simplestock.stock.dto.StockListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @GetMapping("/stocks")
    public StockListResponse getStocks() {
        return service.getStocks();
    }

    @PostMapping("/stocks")
    public void setStocks(@RequestBody StockListRequest stocks) {
        service.setStocks(stocks);
    }
}