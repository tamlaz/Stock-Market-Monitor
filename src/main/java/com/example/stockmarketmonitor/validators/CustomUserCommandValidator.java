package com.example.stockmarketmonitor.validators;

import com.example.stockmarketmonitor.domain.CustomUser;
import com.example.stockmarketmonitor.dto.incoming.CustomUserCommand;
import com.example.stockmarketmonitor.repository.CustomUserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



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
        CustomUser customUser= customUserRepository.findByEmail(customUserCommand.getEmail()).orElse(null);

        if (customUser != null) {
            errors.rejectValue("email", "email.already.used");
        }
    }
}
