package com.example.stockmarketmonitor.dto.incoming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonStock {

    private String ticker;
    private String name;
    private String market;
    private String locale;
    private String primary_exchange;
    private String type;
    private boolean active;
    private String currency_name;
    private String cik;
    private String composite_figi;
    private String share_class_figi;
    private String last_updated_utc;
}
