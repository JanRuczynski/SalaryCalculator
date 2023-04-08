package com.janruczynski.SalaryCalculator.Validation;

import com.janruczynski.SalaryCalculator.biz.model.WorkDay;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class WorkDayTimeValidator implements ConstraintValidator<ValidateTime, WorkDay> {
    @Override
    public boolean isValid(WorkDay workDay, ConstraintValidatorContext context) {
//        x - workDay.getStartTime();
//        p - workDay.getEndTime();
//        y - workDay.getDate();
//        z - LocalTime.now();//

        return workDay.getStartTime().isBefore(workDay.getEndTime())
                && (!workDay.getDate().isAfter(LocalDate.now())
                || (workDay.getStartTime().isBefore(LocalTime.now())
                && workDay.getEndTime().isBefore(LocalTime.now())));
    }
}
