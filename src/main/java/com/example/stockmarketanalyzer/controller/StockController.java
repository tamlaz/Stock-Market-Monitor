package com.example.stockmarketanalyzer.controller;

import com.example.stockmarketanalyzer.dto.outgoing.StockDetails;
import com.example.stockmarketanalyzer.dto.outgoing.StockPriceDetails;
import com.example.stockmarketanalyzer.service.StockService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/tickers")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<StockDetails>> getAllListedStocks(){
        return new ResponseEntity<>(stockService.getAllListedStocks(), HttpStatus.OK);
    }

    @PostMapping("/{ticker}")
    public ResponseEntity<JsonNode> addStock(@PathVariable String ticker){
        return new ResponseEntity<>(stockService.saveStock(ticker),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDetails> getStockData(@PathVariable Long id) {
        return new ResponseEntity<>(stockService.getStockData(id), HttpStatus.OK);
    }

    @GetMapping("/last/{ticker}")
    public ResponseEntity<StockPriceDetails> getLastPrice(@PathVariable String ticker) {
        StockPriceDetails priceDetails = new StockPriceDetails();
        return new ResponseEntity<>(priceDetails,HttpStatus.OK);
    }
}
