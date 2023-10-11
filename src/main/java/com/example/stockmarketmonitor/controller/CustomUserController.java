package com.example.stockmarketmonitor.controller;

import com.example.stockmarketmonitor.dto.outgoing.CustomUserDetails;
import com.example.stockmarketmonitor.service.CustomUserService;
import com.example.stockmarketmonitor.validators.CustomUserCommandValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class CustomUserController {

    private CustomUserService customUserService;

    private CustomUserCommandValidator userCommandValidator;


    public CustomUserController(CustomUserService customUserService, CustomUserCommandValidator userCommandValidator) {
        this.customUserService = customUserService;
        this.userCommandValidator = userCommandValidator;
    }

    @InitBinder("customUserCommand")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCommandValidator);
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomUserDetails> displayProfileData() {
        CustomUserDetails details = customUserService.getProfileDataDetails();
        return new ResponseEntity<>(details,HttpStatus.OK);
    }

    @PutMapping("/addToWatchList/{stockId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> addToWatchList(@PathVariable Long stockId) {
        customUserService.addToWatchList(stockId, 1L);
        System.out.println(stockId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/removeFromWatchList/{stockId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> removeFromWatchList(@PathVariable Long stockId) {
        customUserService.removeFromWatchList(stockId, 1L);
        System.out.println(stockId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
