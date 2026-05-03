package com.szywoj.simplestock.stock;

import com.szywoj.simplestock.stock.dto.StockDTO;
import com.szywoj.simplestock.stock.dto.StockListRequest;
import com.szywoj.simplestock.stock.dto.StockListResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .filter(s -> s.getQuantity() > 0)
                .map(s -> new StockDTO(s.getName(), s.getQuantity()))
                .toList();

        return new StockListResponse(list);
    }

    @Transactional
    public void setStocks(StockListRequest request) {
        Map<String, Integer> incoming = request.stockList().stream()
                .collect(Collectors.toMap(StockDTO::name, StockDTO::quantity));

        for (Map.Entry<String, Integer> e : incoming.entrySet()) {
            Stock stock = repository.findById(e.getKey())
                    .orElse(new Stock());

            stock.setName(e.getKey());
            stock.setQuantity(e.getValue());

            repository.save(stock);
        }
    }
}