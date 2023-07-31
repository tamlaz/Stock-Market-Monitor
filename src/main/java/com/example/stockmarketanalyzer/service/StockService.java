package com.example.stockmarketanalyzer.service;

import com.example.stockmarketanalyzer.domain.Stock;
import com.example.stockmarketanalyzer.dto.incoming.StockDataCommand;
import com.example.stockmarketanalyzer.dto.outgoing.StockDetails;
import com.example.stockmarketanalyzer.dto.outgoing.StockPriceDetails;
import com.example.stockmarketanalyzer.repository.StockRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StockService {

    private StockRepository stockRepository;
    private RestTemplate restTemplate;
    private final String FINNHUB_API_KEY = "cj1bjh9r01qhv0uhmnf0cj1bjh9r01qhv0uhmnfg";
    private final String POLYGON_API_KEY = "bHLKcB4JTWbBZLpiv3r7_8vHjYlKmXE6";


    public StockService(StockRepository stockRepository, RestTemplate restTemplate) {
        this.stockRepository = stockRepository;
        this.restTemplate = restTemplate;
    }

    public JsonNode getLastStockPrice(String symbol)  {
        String url = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + FINNHUB_API_KEY;
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode jsonNode = response.getBody();
        return jsonNode;
    }

    public JsonNode saveStock(String ticker) {
        String url = String.format("https://api.polygon.io/v3/reference/tickers/%s/?apiKey=%s", ticker, POLYGON_API_KEY);
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode jsonNode = response.getBody();
        StockDataCommand stockDto = new StockDataCommand(jsonNode);
        String logoUrlWithApiKey = stockDto.getLogoUrl() + "?apiKey=bHLKcB4JTWbBZLpiv3r7_8vHjYlKmXE6";
        String iconUrlWithApiKey = stockDto.getIconUrl() + "?apiKey=bHLKcB4JTWbBZLpiv3r7_8vHjYlKmXE6";
        stockDto.setLogoUrl(logoUrlWithApiKey);
        stockDto.setIconUrl(iconUrlWithApiKey);
        stockRepository.save(new Stock(stockDto));
        return jsonNode;
    }

    public StockDetails getStockData(Long id) {
        Stock stock = stockRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Could not find this ticker."));
        System.out.println(stock.getTicker());
        JsonNode jsonNode = getLastStockPrice(stock.getTicker());
        System.out.println(jsonNode);
        StockDetails stockDetails = new StockDetails(stock);
        stockDetails.setLastStockPrice(jsonNode.get("c").asDouble());
        stockDetails.setHighPrice(jsonNode.get("h").asDouble());
        stockDetails.setLowPrice(jsonNode.get("l").asDouble());
        stockDetails.setOpenPrice(jsonNode.get("o").asDouble());
        stockDetails.setPreviousClosePrice(jsonNode.get("pc").asDouble());
        stockDetails.setLastTradeTime(jsonNode.get("t").asDouble());
        return stockDetails;
    }

    public List<StockDetails> getAllListedStocks() {
        return stockRepository.findAll().stream().map(StockDetails::new).collect(Collectors.toList());
    }
}
