package com.example.stockmarketanalyzer.dto.incoming;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockDataCommand {

    private String ticker;
    private String name;
    private String logoUrl;


    public StockDataCommand(JsonNode jsonNode) {
        this.ticker = jsonNode.get("results").get("ticker").toString();
        this.name = jsonNode.get("results").get("name").toString();
        this.logoUrl = jsonNode.get("results").get("branding").get("logo_url").toString();
    }
}
