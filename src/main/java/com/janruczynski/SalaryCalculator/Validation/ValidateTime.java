package com.janruczynski.SalaryCalculator.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {WorkDayTimeValidator.class})
public @interface ValidateTime {
    String message() default "Please enter valid information.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
