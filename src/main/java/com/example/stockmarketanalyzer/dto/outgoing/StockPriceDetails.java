package com.example.stockmarketanalyzer.dto.outgoing;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockPriceDetails {

    private double lastStockPrice;
    private double highPrice;
    private double lowPrice;
    private double openPrice;
    private double previousClosePrice;
    private double lastTradeTime;


    public StockPriceDetails(JsonNode jsonNode) {
        this.lastStockPrice = jsonNode.get("c").asDouble();
        this.highPrice = jsonNode.get("h").asDouble();
        this.lowPrice = jsonNode.get("l").asDouble();
        this.openPrice = jsonNode.get("o").asDouble();
        this.previousClosePrice = jsonNode.get("pc").asDouble();
        this.lastTradeTime = jsonNode.get("t").asDouble();
    }

}
