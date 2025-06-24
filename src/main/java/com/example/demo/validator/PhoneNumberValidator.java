package com.example.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber,String>{
	
	@Override
	public void initialize(PhoneNumber phoneNumber) {
		
	}
	
	@Override
	public boolean isValid(String input, ConstraintValidatorContext content) {
		if (input == null) {
			return true;
		}
		return input.matches("[0-9]{1,4}-[0-9]{1,6}(-[0-9]{0,5})?|[0-9]{10}|[0-9]{11}");
	}
}
