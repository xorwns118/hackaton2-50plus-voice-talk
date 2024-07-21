package com.example.hackaton250plusvoicetalk.validation.annotation;

import com.example.hackaton250plusvoicetalk.validation.validator.MobileNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {MobileNumberValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MobileNumber {
    String message() default "Invalid mobile number ex) 010-0000-0000";
    String regexp() default "^\\d{2,3}-\\d{3,4}-\\d{4}$";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
