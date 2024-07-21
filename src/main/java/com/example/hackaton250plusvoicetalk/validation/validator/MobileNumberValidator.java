package com.example.hackaton250plusvoicetalk.validation.validator;

import com.example.hackaton250plusvoicetalk.validation.annotation.MobileNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class MobileNumberValidator implements ConstraintValidator<MobileNumber, String> {

    private String regexp;

    @Override
    public void initialize(MobileNumber constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String mobileNumber, ConstraintValidatorContext context) {
        return Pattern.matches(regexp, mobileNumber);
    }

}
