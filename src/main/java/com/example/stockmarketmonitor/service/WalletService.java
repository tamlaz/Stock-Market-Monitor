package com.example.stockmarketmonitor.service;

import com.example.stockmarketmonitor.domain.CustomUser;
import com.example.stockmarketmonitor.domain.Wallet;
import com.example.stockmarketmonitor.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class WalletService {

    private final WalletRepository walletRepository;


    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    public Wallet createWallet(CustomUser user) {
        Wallet walletToSave = new Wallet(1000.0, user);
        walletRepository.save(walletToSave);
        return walletToSave;
    }

    public void buyStock(Long walletId,Long stockId, Double quantity, Double price) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(EntityNotFoundException::new);
        Map<Long, Double> portfolio = wallet.getPortfolio();
        if (portfolio.containsKey(stockId)) {
            Double updatedQuantity = portfolio.get(stockId) + quantity;
            portfolio.put(stockId, updatedQuantity);
        } else {
            portfolio.put(stockId, quantity);
        }
        wallet.setBalance(wallet.getBalance()-price);
    }
}
