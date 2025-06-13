package com.example.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.form.DeliveryItemForm;
import com.example.demo.repository.SizeRepositorySerchiExixts;

@Component
public class SizeIfAvailableValidatorDelivery implements ConstraintValidator<SizeIfAvailableDelivery, DeliveryItemForm> {
	@Autowired
	private SizeRepositorySerchiExixts sizeRepositorySerchiExixts;
	
	@Override
	public boolean isValid(DeliveryItemForm form, ConstraintValidatorContext context) {
		if (form == null) {
			return true;
		}
		Integer itemId = form.getItemId();
		Integer sizeId = form.getSizeId();
		
		boolean hasSizes = sizeRepositorySerchiExixts.existsByItemId(itemId);
		if (!hasSizes) {
			return true;
		}
//		if (sizeId == null) {
//			return false;
//		}
		
		if (sizeId == null || sizeId == 0) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("サイズを選択してください")
					.addPropertyNode("sizeId")
					.addConstraintViolation();
			return false;
		}
		return true;
	}
}
