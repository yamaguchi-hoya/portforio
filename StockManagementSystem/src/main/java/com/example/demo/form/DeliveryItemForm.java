package com.example.demo.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import com.example.demo.validator.SizeIfAvailableDelivery;

import lombok.Data;

@Data
@SizeIfAvailableDelivery
public class DeliveryItemForm {
	@Min(value=1,message="選択してください。")
	private Integer categoryId;
	
	@Min(value=1,message="選択してください。")
	private Integer itemId;
	
	private String itemName;
	
	private Integer sizeId = 0;
	
	private String sizeName;
	
	private Integer unitPrice;
	
	@Min(value=1, message="1以上を入力してください。")
	@NotNull(message="入力してください。")
	private Integer amount;
	
	private Integer price;
}
