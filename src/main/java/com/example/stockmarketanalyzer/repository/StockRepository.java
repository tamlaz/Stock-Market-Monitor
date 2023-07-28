package com.example.stockmarketanalyzer.repository;

import com.example.stockmarketanalyzer.domain.CustomStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<CustomStock, Long> {
}
