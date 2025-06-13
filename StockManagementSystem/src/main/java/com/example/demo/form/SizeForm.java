package com.example.demo.form;

import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class SizeForm {
	private int sizeId;
	
	@Size(min=1,max=30, message="入力してください")
	private String sizeName;
	
//	@Min(value=1,message="選択してくしてください")
	private int itemId;
}

