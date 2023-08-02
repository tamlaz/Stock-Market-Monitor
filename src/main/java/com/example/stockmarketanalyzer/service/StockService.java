package com.example.stockmarketanalyzer.service;

import com.example.stockmarketanalyzer.domain.Stock;
import com.example.stockmarketanalyzer.dto.incoming.StockDataCommand;
import com.example.stockmarketanalyzer.dto.outgoing.StockDetails;
import com.example.stockmarketanalyzer.repository.StockRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StockService {

    private StockRepository stockRepository;
    private RestTemplate restTemplate;

    @Value("${finnhubapikey}")
    private String FINNHUB_API_KEY;

    @Value(("${polygonapikey}"))
    private String POLYGON_API_KEY;


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
        stockDto.setLogoUrl(saveImgPath(logoUrlWithApiKey,ticker));
        stockDto.setIconUrl(saveImgPath(iconUrlWithApiKey,ticker));
        stockRepository.save(new Stock(stockDto));
        return jsonNode;
    }

    private String saveImgPath(String url, String ticker) {
        ResponseEntity<byte[]> imageResponse = restTemplate.getForEntity(url, byte[].class);
        byte[] imageBytes = imageResponse.getBody();
        String relativePath =  "/assets/images/" + ticker;
        if (url.contains("svg")) {
            relativePath += ".svg";
        } else {
            relativePath += ".png";
        }
        String absolutePath = "/Users/lazartamas/IdeaProjects/Stock-Market-Monitor/angular-frontend/stock-monitor-ui/src" + relativePath;
        try (FileOutputStream fos = new FileOutputStream(absolutePath)) {
            fos.write(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativePath;
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
        return stockRepository.getAllStocks().stream().map(StockDetails::new).collect(Collectors.toList());
    }
}
