package com.example.stockmarketmonitor.dto.incoming;

import com.example.stockmarketmonitor.dto.outgoing.CommonStockListItem;
import lombok.Data;

import java.util.List;

@Data
public class CommonStockResponse {

    private List<CommonStock> commonStocks;
}
