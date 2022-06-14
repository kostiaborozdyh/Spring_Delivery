package com.gmail.kostia_borozdyh.util;

import com.gmail.kostia_borozdyh.dto.ValidationDTO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class Validator {
    public static ValidationDTO getValidationInfo(BindingResult bindingResult) {
        ValidationDTO validation = new ValidationDTO();
        for (FieldError suppressedField : bindingResult.getFieldErrors()) {
            switch (suppressedField.getField()) {
                case "login":
                    validation.setLogin("login");
                    break;
                case "password":
                    validation.setPassword("password");
                    break;
                case "firstName":
                    validation.setFirstName("firstName");
                    break;
                case "lastName":
                    validation.setLastName("lastName");
                    break;
                case "email":
                    validation.setEmail("email");
                    break;
                case "phoneNumber":
                    validation.setPhoneNumber("phoneNumber");
                    break;
            }
        }
        return validation;
    }
}
