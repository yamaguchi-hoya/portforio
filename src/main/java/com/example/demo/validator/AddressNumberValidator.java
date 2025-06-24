package com.example.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressNumberValidator implements ConstraintValidator<AddressNumber,String>{
	
	@Override
	public void initialize(AddressNumber addressNumber) {
		
	}
	
	@Override
	public boolean isValid(String input, ConstraintValidatorContext content) {
		if (input == null) {
			return true;
		}
		return input.matches("[0-9]{3}-[0-9]{4}|[0-9]{7}");
	}
}
