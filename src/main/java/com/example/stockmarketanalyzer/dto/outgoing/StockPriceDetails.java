package com.example.stockmarketanalyzer.dto.outgoing;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockPriceDetails {

    private double lastStockPrice;



    public StockPriceDetails(JsonNode jsonNode) {
        this.lastStockPrice = jsonNode.get("c").asDouble();
    }

}
