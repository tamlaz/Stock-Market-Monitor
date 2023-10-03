package com.example.stockmarketmonitor.validators;

import com.example.stockmarketmonitor.domain.CustomUser;
import com.example.stockmarketmonitor.dto.incoming.LoginCommand;
import com.example.stockmarketmonitor.repository.UserDetailsRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LoginCommandValidator implements Validator {

    private final UserDetailsRepository userRepository;

    public LoginCommandValidator(UserDetailsRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginCommand loginCommand = (LoginCommand) target;

        CustomUser customUser = userRepository.findByEmail(loginCommand.getEmail())
                .orElse(null);

        if (customUser == null) {
            errors.rejectValue("email", "email.unknown");
        }
    }
}
