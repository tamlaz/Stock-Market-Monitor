package com.example.stockmarketanalyzer.service;

import com.example.stockmarketanalyzer.domain.CustomUser;
import com.example.stockmarketanalyzer.dto.outgoing.ProfileDataDetails;
import com.example.stockmarketanalyzer.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
public class CustomUserService {

    private CustomUserRepository customUserRepository;

    @Autowired
    public CustomUserService(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
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
}
