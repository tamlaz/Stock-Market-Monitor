package com.example.stockmarketmonitor.dto.outgoing;

import com.example.stockmarketmonitor.domain.Stock;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockListItem {

    private Long id;
    private String ticker;
    private String iconUrl;
    private StockPriceDetails stockPriceDetails;


    public StockListItem(Stock stock) {
        this.id = stock.getId();
        this.ticker = stock.getTicker();
        this.iconUrl = stock.getIconUrl();
    }
}
