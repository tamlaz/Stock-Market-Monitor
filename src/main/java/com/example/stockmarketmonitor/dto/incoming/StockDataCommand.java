package com.example.stockmarketmonitor.dto.incoming;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockDataCommand {

    private String ticker;
    private String name;
    private String logoUrl;
    private String iconUrl;
    private String description;


    public StockDataCommand(JsonNode jsonNode) {
        this.ticker = trimQuotationsMark(jsonNode.get("results").get("ticker").toString());
        this.name = trimQuotationsMark(jsonNode.get("results").get("name").toString());
        this.logoUrl = trimQuotationsMark(jsonNode.get("results").get("branding").get("logo_url").toString());
        this.iconUrl = trimQuotationsMark(jsonNode.get("results").get("branding").get("icon_url").toString());
        this.description = trimQuotationsMark(jsonNode.get("results").get("description").toString());
    }

    private String trimQuotationsMark(String data) {
        return data.substring(1,data.length()-1);
    }
}
