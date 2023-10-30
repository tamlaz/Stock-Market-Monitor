package com.example.stockmarketmonitor.dto.incoming;

import lombok.Data;

@Data
public class StockPurchaseFormData {

    private Long stockId;
    private Double quantity;
    private Double price;
}
