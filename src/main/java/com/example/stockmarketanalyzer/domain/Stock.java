package com.example.stockmarketanalyzer.domain;

import com.example.stockmarketanalyzer.dto.incoming.StockDataCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "stocks")
@Data
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @Column(name = "ticker_name")
    private String ticker;

    @Column(name = "company_name")
    private String name;

    @Column(name = "logo_url")
    private String logoUrl;

    public Stock(StockDataCommand dto) {
        this.ticker = dto.getTicker();
        this.name = dto.getName();
        this.logoUrl = dto.getLogoUrl();
    }

}
