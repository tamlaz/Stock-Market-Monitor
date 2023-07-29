package com.example.stockmarketanalyzer.dto.outgoing;

import com.example.stockmarketanalyzer.domain.Stock;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockDetails {


    private String ticker;
    private String name;
    private String logoUrl;
    private String iconUrl;
    private String description;

    public StockDetails(Stock stock) {
        this.ticker = stock.getTicker();
        this.name = stock.getName();
        this.logoUrl = stock.getLogoUrl();
        this.iconUrl = stock.getIconUrl();
        this.description = stock.getDescription();
    }
}
