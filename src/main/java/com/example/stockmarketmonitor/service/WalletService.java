package com.example.stockmarketmonitor.service;

import com.example.stockmarketmonitor.domain.CustomUser;
import com.example.stockmarketmonitor.domain.Wallet;
import com.example.stockmarketmonitor.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
