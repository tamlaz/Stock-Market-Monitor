package com.example.stockmarketanalyzer.service;

import com.example.stockmarketanalyzer.domain.Stock;
import com.example.stockmarketanalyzer.dto.incoming.StockDataCommand;
import com.example.stockmarketanalyzer.dto.outgoing.StockPriceDetails;
import com.example.stockmarketanalyzer.repository.StockRepository;
import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.OkHttpClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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

    public void saveStock(StockDataCommand dto) {
        Stock stockToSave = new Stock(dto);
        stockRepository.save(stockToSave);
    }

    public StockPriceDetails getLastStockPrice(String symbol)  {
        String url = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + FINNHUB_API_KEY;
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode jsonNode = response.getBody();
        return new StockPriceDetails(jsonNode);
    }

    public JsonNode saveStock(String ticker) {
        OkHttpClient client = new OkHttpClient();
        String url = String.format("https://api.polygon.io/v3/reference/tickers/%s/?apiKey=%s", ticker, POLYGON_API_KEY);
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode jsonNode = response.getBody();
        StockDataCommand stockDto = new StockDataCommand(jsonNode);
        stockRepository.save(new Stock(stockDto));
        return jsonNode;
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        String responseData = null;
//        try {
//            Response response = client.newCall(request).execute();
//            responseData = response.body().string();
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(responseData);
//            StockDataCommand dto = new StockDataCommand(jsonNode);
//            stockService.saveStock(dto);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
