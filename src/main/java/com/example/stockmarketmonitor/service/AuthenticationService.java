package com.example.stockmarketmonitor.service;

import com.example.stockmarketmonitor.config.UserRole;
import com.example.stockmarketmonitor.domain.CustomUser;
import com.example.stockmarketmonitor.dto.incoming.CustomUserCommand;
import com.example.stockmarketmonitor.repository.UserDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthenticationService {

    private UserDetailsRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(UserDetailsRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
            customUser.setUserRoles(List.of("ROLE_USER").stream().map(UserRole::valueOf).collect(Collectors.toList()));
            userRepository.save(customUser);
        }
    }
}
