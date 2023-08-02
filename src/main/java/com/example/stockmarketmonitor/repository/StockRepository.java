package com.example.stockmarketmonitor.repository;

import com.example.stockmarketmonitor.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query("SELECT s FROM Stock s ORDER BY s.ticker")
    List<Stock> getAllStocks();
}
