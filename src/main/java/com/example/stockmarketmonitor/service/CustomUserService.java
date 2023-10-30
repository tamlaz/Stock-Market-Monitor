package com.example.stockmarketmonitor.service;

import com.example.stockmarketmonitor.domain.CustomUser;
import com.example.stockmarketmonitor.domain.Stock;
import com.example.stockmarketmonitor.dto.outgoing.CustomUserDetails;
import com.example.stockmarketmonitor.dto.incoming.StockPurchaseFormData;
import com.example.stockmarketmonitor.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class CustomUserService {

    private final CustomUserRepository customUserRepository;
    private final StockService stockService;
    private final AuthenticationService authService;
    private final WalletService walletService;

    @Autowired
    public CustomUserService(CustomUserRepository customUserRepository, StockService stockService, AuthenticationService authService, WalletService walletService) {
        this.customUserRepository = customUserRepository;
        this.stockService = stockService;
        this.authService = authService;
        this.walletService = walletService;
    }


    public CustomUserDetails getProfileDataDetails() throws IllegalCallerException{
        CustomUser loggedInUser = authService.authenticate();
        return new CustomUserDetails(loggedInUser);
    }

    public void addToWatchList(Long stockId, Long userId) {
        CustomUser user = authService.authenticate();
        Stock stock = stockService.findById(stockId);
        user.getWatchList().add(stock.getId());
    }

    public void removeFromWatchList(Long stockId, long l) {
        CustomUser user = authService.authenticate();
        Stock stock = stockService.findById(stockId);
        user.getWatchList().remove(stock.getId());
    }

    public void buyStock(StockPurchaseFormData stockPurchaseFormData) {
        CustomUser loggedInUser = authService.authenticate();
        Long walletId = loggedInUser.getWallet().getId();
        walletService.buyStock(walletId,stockPurchaseFormData.getStockId(),
                stockPurchaseFormData.getQuantity(),
                stockPurchaseFormData.getPrice());
    }
}
