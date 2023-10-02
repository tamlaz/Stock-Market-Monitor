package com.example.stockmarketmonitor.service;

import com.example.stockmarketmonitor.domain.CustomUser;
import com.example.stockmarketmonitor.domain.Stock;
import com.example.stockmarketmonitor.dto.outgoing.ProfileDataDetails;
import com.example.stockmarketmonitor.repository.CustomUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class CustomUserService {

    private CustomUserRepository customUserRepository;
    private StockService stockService;

    @Autowired
    public CustomUserService(CustomUserRepository customUserRepository, StockService stockService) {
        this.customUserRepository = customUserRepository;
        this.stockService = stockService;
    }


    public ProfileDataDetails getProfileDataDetails(Long id) throws IllegalCallerException{
        CustomUser user = customUserRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        if (!loggedInUser.getUsername().equals(user.getEmail())) {
            throw new IllegalCallerException();
        }
        return new ProfileDataDetails(user);
    }

    public void addToWatchList(Long stockId, Long userId) {
        CustomUser user = customUserRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        Stock stock = stockService.findById(stockId);
        user.getWatchList().add(stock);
    }
}
