package com.example.stockmarketmonitor.controller;

import com.example.stockmarketmonitor.dto.incoming.CustomUserCommand;
import com.example.stockmarketmonitor.dto.outgoing.ProfileDataDetails;
import com.example.stockmarketmonitor.service.CustomUserService;
import com.example.stockmarketmonitor.service.JpaUserDetailsService;
import com.example.stockmarketmonitor.validators.CustomUserCommandValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
public class CustomUserController {

    private JpaUserDetailsService jpaUserDetailsService;
    private CustomUserService customUserService;

    private CustomUserCommandValidator userCommandValidator;


    public CustomUserController(JpaUserDetailsService jpaUserDetailsService, CustomUserService customUserService, CustomUserCommandValidator userCommandValidator) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.customUserService = customUserService;
        this.userCommandValidator = userCommandValidator;
    }

    @InitBinder("customUserCommand")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCommandValidator);
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<String> registerUser(@RequestBody @Valid CustomUserCommand customUserCommand) {
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
