package com.example.demo.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = SizeIfAvailableValidatorStock.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface SizeIfAvailableStock {
	String message() default "サイズを指定してください";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
