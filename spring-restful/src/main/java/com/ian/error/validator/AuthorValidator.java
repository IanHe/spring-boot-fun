package com.ian.error.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class AuthorValidator implements ConstraintValidator<Author, String> {
    List<String> authors = Arrays.asList("Ian", "Santideva", "Marie Kondo", "Martin Fowler");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return authors.contains(s);
    }

//    @Override
//    public void initialize(Author constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }
}
