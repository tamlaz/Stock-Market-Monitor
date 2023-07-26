package com.example.stockmarketanalyzer.service;

import com.example.stockmarketanalyzer.domain.Stock;
import com.example.stockmarketanalyzer.dto.incoming.StockDataCommand;
import com.example.stockmarketanalyzer.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StockService {

    private StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void saveStock(StockDataCommand dto) {
        Stock stockToSave = new Stock(dto);
        stockRepository.save(stockToSave);
    }
}
