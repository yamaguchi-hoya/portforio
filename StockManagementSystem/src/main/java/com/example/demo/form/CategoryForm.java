package com.example.demo.form;

import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class CategoryForm {
	@Size(min=1, max=20,message="入力してください")
	private String categoryName;
	private int categoryId;
}
