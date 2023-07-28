package com.example.stockmarketanalyzer.controller;

import com.example.stockmarketanalyzer.dto.incoming.StockDataCommand;
import com.example.stockmarketanalyzer.dto.outgoing.StockDetails;
import com.example.stockmarketanalyzer.service.StockService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.math.BigDecimal;

@Controller
@RequestMapping("api/tickers")
public class StockController {

    private StockService stockService;
    private final String POLYGON_API_KEY = "bHLKcB4JTWbBZLpiv3r7_8vHjYlKmXE6";


    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<Void> addTicker(@PathVariable String ticker) {
        OkHttpClient client = new OkHttpClient();

        String url = String.format("https://api.polygon.io/v3/reference/tickers/%s/?apiKey=%s", ticker, POLYGON_API_KEY);
        Request request = new Request.Builder()
                .url(url)
                .build();
        String responseData = null;
        try {
            Response response = client.newCall(request).execute();
            responseData = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseData);
            StockDataCommand dto = new StockDataCommand(jsonNode);
            stockService.saveStock(dto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/last/{ticker}")
    public ResponseEntity<StockDetails> getLastPrice(@PathVariable String ticker) throws IOException {

        return new ResponseEntity<>(stockService.getLastStockPrice(ticker),HttpStatus.OK);
    }
}
