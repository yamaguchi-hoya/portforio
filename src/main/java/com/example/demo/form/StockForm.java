package com.example.demo.form;

import java.sql.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.example.demo.validator.SizeIfAvailableStock;

import lombok.Data;

@Data
@SizeIfAvailableStock
public class StockForm {
	@Min(value=1,message="選択してください。")
	private Integer categoryId;
	
	@Min(value=1,message="選択してください。")
	private Integer itemId;
	
	private Integer sizeId = 0;
	
	@Min(value=1, message="正の整数を入力してください。")
	@NotNull(message="入力してください。")
	private Integer amount;
	
	@NotNull(message="入力してください。")
	private Date registerDate;
	
	@Size(min=1, max=20,message="入力してください。")
	private String person;
	
	
	private String comment;
}
