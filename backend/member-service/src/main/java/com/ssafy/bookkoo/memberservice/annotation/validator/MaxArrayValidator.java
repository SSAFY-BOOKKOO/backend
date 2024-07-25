package com.ssafy.bookkoo.memberservice.annotation.validator;

import com.ssafy.bookkoo.memberservice.annotation.MaxArray;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaxArrayValidator implements ConstraintValidator<MaxArray, Integer[]> {

    private long maxValue;
    @Override
    public void initialize(MaxArray constraintAnnotation) {
        maxValue = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer[] value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        for (Integer i : value) {
            if (i != null && i > maxValue) {
                return false;
            }
        }
        return true;
    }
}
