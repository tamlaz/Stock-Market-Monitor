package com.example.stockmarketanalyzer.controller;

import com.example.stockmarketanalyzer.dto.incoming.CustomUserCommand;
import com.example.stockmarketanalyzer.dto.outgoing.ProfileDataDetails;
import com.example.stockmarketanalyzer.service.CustomUserService;
import com.example.stockmarketanalyzer.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class CustomUserController {

    private JpaUserDetailsService jpaUserDetailsService;
    private CustomUserService customUserService;


    @Autowired
    public CustomUserController(JpaUserDetailsService jpaUserDetailsService, CustomUserService customUserService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.customUserService = customUserService;
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<String> registerUser(@RequestBody CustomUserCommand customUserCommand) {
        jpaUserDetailsService.register(customUserCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDataDetails> displayProfileData(@PathVariable ("id") Long id) {
        ProfileDataDetails details = customUserService.getProfileDataDetails(id);
        return new ResponseEntity<>(details,HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<Void> getTest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
