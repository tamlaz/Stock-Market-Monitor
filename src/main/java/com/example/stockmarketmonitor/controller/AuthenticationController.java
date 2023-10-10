package com.example.stockmarketmonitor.controller;

import com.example.stockmarketmonitor.dto.incoming.CustomUserCommand;
import com.example.stockmarketmonitor.dto.incoming.LoginCommand;
import com.example.stockmarketmonitor.dto.outgoing.LoginResponse;
import com.example.stockmarketmonitor.dto.outgoing.TokenValidationResponse;
import com.example.stockmarketmonitor.service.AuthenticationService;
import com.example.stockmarketmonitor.service.TokenService;
import com.example.stockmarketmonitor.validators.CustomUserCommandValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final CustomUserCommandValidator userCommandValidator;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationService authenticationService, CustomUserCommandValidator userCommandValidator, TokenService tokenService) {
        this.authenticationService = authenticationService;
        this.userCommandValidator = userCommandValidator;
        this.tokenService = tokenService;
    }

    @InitBinder("customUserCommand")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCommandValidator);
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<String> registerUser(@RequestBody @Valid CustomUserCommand customUserCommand) {
        authenticationService.register(customUserCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginCommand loginCommand) {
        return new ResponseEntity<>(authenticationService.loginUser(loginCommand), HttpStatus.OK);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<TokenValidationResponse> validateToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        TokenValidationResponse validationResponse = new TokenValidationResponse();
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            Jwt accessToken = tokenService.decodeToken(authHeader.substring(7));
            if (tokenService.isValid(accessToken)) {
                validationResponse.setValid(true);
                return new ResponseEntity<>(validationResponse, HttpStatus.OK);
            }
        }
        validationResponse.setValid(false);
        return new ResponseEntity<>(validationResponse, HttpStatus.UNAUTHORIZED);
    }

}
