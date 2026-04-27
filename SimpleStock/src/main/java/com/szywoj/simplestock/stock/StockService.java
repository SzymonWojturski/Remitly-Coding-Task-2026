package com.szywoj.simplestock.stock;

import com.szywoj.simplestock.stock.dto.StockDTO;
import com.szywoj.simplestock.stock.dto.StockListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        List<Stock> existing = repository.findAll();

        Map<String, Integer> requestMap = dtos.stream()
                .collect(Collectors.toMap(StockDTO::name, StockDTO::quantity));

        List<Stock> result = new ArrayList<>();

        for (Stock stock : existing) {
            Integer newQty = requestMap.get(stock.getName());

            if (newQty != null) {
                stock.setQuantity(newQty);
                requestMap.remove(stock.getName());
            } else {
                stock.setQuantity(0);
            }

            result.add(stock);
        }

        for (Map.Entry<String, Integer> entry : requestMap.entrySet()) {
            Stock s = new Stock();
            s.setName(entry.getKey());
            s.setQuantity(entry.getValue());
            result.add(s);
        }

        repository.saveAll(result);
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