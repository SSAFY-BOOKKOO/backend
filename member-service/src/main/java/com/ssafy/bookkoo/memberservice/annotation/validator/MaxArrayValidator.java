package com.ssafy.bookkoo.memberservice.annotation.validator;

import com.ssafy.bookkoo.memberservice.annotation.MaxArray;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaxArrayValidator implements ConstraintValidator<MaxArray, Integer[]> {

    private long maxValue;
    private long minValue;

    @Override
    public void initialize(MaxArray constraintAnnotation) {
        maxValue = constraintAnnotation.maxValue();
        minValue = constraintAnnotation.minValue();
    }

    @Override
    public boolean isValid(Integer[] value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        for (Integer i : value) {
            if (i > maxValue || i < minValue) {
                return false;
            }
        }
        return true;
    }
}
