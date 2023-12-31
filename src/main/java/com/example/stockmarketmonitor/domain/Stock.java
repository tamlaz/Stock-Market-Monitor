package com.example.stockmarketmonitor.domain;

import com.example.stockmarketmonitor.dto.incoming.StockDataCommand;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "icon_url")
    private String iconUrl;


    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private CustomUser user;

    public Stock(StockDataCommand dto) {
        this.ticker = dto.getTicker();
        this.name = dto.getName();
        this.logoUrl = dto.getLogoUrl();
        this.iconUrl = dto.getIconUrl();
        this.description = dto.getDescription();
    }

}
