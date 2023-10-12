package com.example.stockmarketmonitor.service;

import com.example.stockmarketmonitor.config.UserRole;
import com.example.stockmarketmonitor.domain.CustomUser;
import com.example.stockmarketmonitor.domain.Wallet;
import com.example.stockmarketmonitor.dto.incoming.CustomUserCommand;
import com.example.stockmarketmonitor.dto.incoming.LoginCommand;
import com.example.stockmarketmonitor.dto.outgoing.LoginResponse;
import com.example.stockmarketmonitor.repository.UserDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class AuthenticationService {

    private final UserDetailsRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private TokenService tokenService;

    private final  WalletService walletService;

    public AuthenticationService(UserDetailsRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService, WalletService walletService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.walletService = walletService;
    }

    public void register(CustomUserCommand customUserCommand) {
        CustomUser customUser = userRepository.findByEmail(customUserCommand.getEmail()).orElse(null);

        if (customUser == null) {
            customUser = new CustomUser();
            customUser.setEmail(customUserCommand.getEmail());
            customUser.setPassword(passwordEncoder.encode(customUserCommand.getPassword()));
            customUser.setFirstName(customUserCommand.getFirstName());
            customUser.setLastName(customUserCommand.getLastName());
            customUser.setCreatedAt(LocalDateTime.now());
            customUser.setUserRoles(Stream.of("USER").map(UserRole::valueOf).collect(Collectors.toList()));
            userRepository.save(customUser);
            Wallet wallet = walletService.createWallet(customUser);
            customUser.setWallet(wallet);
        }
    }

    public LoginResponse loginUser(LoginCommand loginCommand) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginCommand.getEmail(), loginCommand.getPassword())
        );
        CustomUser user = userRepository.findByEmail(loginCommand.getEmail())
                .orElseThrow(EntityNotFoundException::new);
        return new LoginResponse(tokenService.generateJwt(user));
    }

    public CustomUser authenticate() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Jwt token = (Jwt) auth.getPrincipal();
        return userRepository.findByEmail((String) token.getClaims().get("sub"))
                .orElseThrow(EntityNotFoundException::new);
    }
}
