package com.example.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MailValidator implements ConstraintValidator<Mail,String>{
	
	@Override
	public void initialize(Mail mail) {
		
	}
	
	@Override
	public boolean isValid(String input, ConstraintValidatorContext content) {
		if (input == null) {
			return true;
		}
		return input.matches("^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$");
	}
}
