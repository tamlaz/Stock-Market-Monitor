package com.example.stockmarketanalyzer.service;

import com.example.stockmarketanalyzer.domain.CustomStock;
import com.example.stockmarketanalyzer.dto.incoming.StockDataCommand;
import com.example.stockmarketanalyzer.dto.outgoing.StockDetails;
import com.example.stockmarketanalyzer.repository.StockRepository;
import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@Transactional
public class StockService {

    private StockRepository stockRepository;
    private RestTemplate restTemplate;
    private final String FINNHUB_API_KEY = "cj1bjh9r01qhv0uhmnf0cj1bjh9r01qhv0uhmnfg";

    @Autowired
    public StockService(StockRepository stockRepository, RestTemplate restTemplate) {
        this.stockRepository = stockRepository;
        this.restTemplate = restTemplate;
    }

    public void saveStock(StockDataCommand dto) {
        CustomStock stockToSave = new CustomStock(dto);
        stockRepository.save(stockToSave);
    }

    public StockDetails getLastStockPrice(String symbol)  {
        String url = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + FINNHUB_API_KEY;
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode jsonNode = response.getBody();
        return new StockDetails(jsonNode);
    }

}
