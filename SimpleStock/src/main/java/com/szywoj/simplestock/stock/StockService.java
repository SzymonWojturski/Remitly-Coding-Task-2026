package com.szywoj.simplestock.stock;

import com.szywoj.simplestock.stock.dto.StockDTO;
import com.szywoj.simplestock.stock.dto.StockListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository repository;

    public StockListResponse getStocks() {
        List<StockDTO> list = repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();

        return new StockListResponse(list);
    }

    public void setStocks(List<StockDTO> dtos) {
        repository.deleteAll();

        List<Stock> stocks = dtos.stream()
                .map(this::mapToEntity)
                .toList();

        repository.saveAll(stocks);
    }

    private StockDTO mapToDTO(Stock stock) {
        return new StockDTO(
                stock.getName(),
                stock.getQuantity()
        );
    }

    private Stock mapToEntity(StockDTO dto) {
        Stock stock = new Stock();
        stock.setName(dto.name());
        stock.setQuantity(dto.quantity());
        return stock;
    }
}