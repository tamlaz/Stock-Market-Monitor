package com.example.stockmarketmonitor.service;

import com.example.stockmarketmonitor.domain.CustomUser;
import com.example.stockmarketmonitor.domain.Stock;
import com.example.stockmarketmonitor.dto.outgoing.CustomUserDetails;
import com.example.stockmarketmonitor.repository.CustomUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class CustomUserService {

    private final CustomUserRepository customUserRepository;
    private final StockService stockService;

    private final AuthenticationService authService;

    @Autowired
    public CustomUserService(CustomUserRepository customUserRepository, StockService stockService, AuthenticationService authService) {
        this.customUserRepository = customUserRepository;
        this.stockService = stockService;
        this.authService = authService;
    }


    public CustomUserDetails getProfileDataDetails() throws IllegalCallerException{
        CustomUser loggedInUser = authService.authenticate();
        return new CustomUserDetails(loggedInUser);
    }

    public void addToWatchList(Long stockId, Long userId) {
        CustomUser user = customUserRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        Stock stock = stockService.findById(stockId);
        user.getWatchList().add(stock);
    }
}
