package com.example.stockmarketanalyzer.repository;

import com.example.stockmarketanalyzer.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
