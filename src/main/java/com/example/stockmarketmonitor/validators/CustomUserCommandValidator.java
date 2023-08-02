package com.example.stockmarketmonitor.validators;

import com.example.stockmarketmonitor.domain.CustomUser;
import com.example.stockmarketmonitor.dto.incoming.CustomUserCommand;
import com.example.stockmarketmonitor.repository.CustomUserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class CustomUserCommandValidator implements Validator {

    private CustomUserRepository customUserRepository;

    public CustomUserCommandValidator(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomUserCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomUserCommand customUserCommand = (CustomUserCommand) target;
        Optional<CustomUser> customUser = customUserRepository.findByEmail(customUserCommand.getEmail());

        if (customUser.isPresent()) {
            errors.rejectValue("email", "email.exists");
        }
    }
}
