package com.example.stockmarketanalyzer.controller;

import com.example.stockmarketanalyzer.dto.outgoing.StockPriceDetails;
import com.example.stockmarketanalyzer.service.StockService;
import com.fasterxml.jackson.databind.JsonNode;
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
    public ResponseEntity<JsonNode> addTicker(@PathVariable String ticker) {
        return new ResponseEntity<>(stockService.saveStock(ticker),HttpStatus.OK);
    }

    @GetMapping("/last/{ticker}")
    public ResponseEntity<StockPriceDetails> getLastPrice(@PathVariable String ticker) throws IOException {

        return new ResponseEntity<>(stockService.getLastStockPrice(ticker),HttpStatus.OK);
    }
}
