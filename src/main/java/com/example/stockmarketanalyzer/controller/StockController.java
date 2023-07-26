package com.example.stockmarketanalyzer.controller;

import com.example.stockmarketanalyzer.dto.incoming.StockDataCommand;
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

@Controller
@RequestMapping("api/tickers")
public class StockController {

    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<String> getTickerData(@PathVariable String ticker) {
        OkHttpClient client = new OkHttpClient();
        String apiKey = "bHLKcB4JTWbBZLpiv3r7_8vHjYlKmXE6";
        String url = String.format("https://api.polygon.io/v3/reference/tickers/%s/?apiKey=%s", ticker, apiKey);
        Request request = new Request.Builder()
                .url(url)
                .build();
        String responseData = null;
        try {
            Response response = client.newCall(request).execute();
            responseData = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseData);
            System.out.println(jsonNode.get("results").get("ticker"));
            System.out.println(jsonNode.get("results").get("name"));
            System.out.println(jsonNode.get("results").get("branding").get("logo_url"));
            StockDataCommand dto = new StockDataCommand(jsonNode);
            stockService.saveStock(dto);

        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(responseData);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
