package com.example.stockmarketanalyzer.dto.outgoing;

import com.example.stockmarketanalyzer.domain.Stock;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockDetails {

    private Long id;
    private String ticker;
    private String name;
    private String logoUrl;
    private String iconUrl;
    private String description;
    private double lastStockPrice;
    private double highPrice;
    private double lowPrice;
    private double openPrice;
    private double previousClosePrice;
    private double lastTradeTime;

    public StockDetails(Stock stock) {
        this.id = stock.getId();
        this.ticker = stock.getTicker();
        this.name = stock.getName();
        this.logoUrl = stock.getLogoUrl();
        this.iconUrl = stock.getIconUrl();
        this.description = stock.getDescription();

    }
}
