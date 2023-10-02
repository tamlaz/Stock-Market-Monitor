package com.example.stockmarketmonitor.dto.outgoing;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonStockListItem {

    private String ticker;
    private String name;
    private String primaryExchange;
    private String currencyName;
    private boolean active;
    private String locale;

    public CommonStockListItem(JsonNode jsonNode) {
        this.ticker = trimQuotationsMark(jsonNode.get("ticker").toString());
        this.name = trimQuotationsMark(jsonNode.get("name").toString());
        this.ticker = trimQuotationsMark(jsonNode.get("ticker").toString());
    }

    private String trimQuotationsMark(String data) {
        return data.substring(1,data.length()-1);
    }
}
