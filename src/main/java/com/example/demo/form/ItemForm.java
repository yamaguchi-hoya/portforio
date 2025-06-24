package com.example.demo.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class ItemForm {
	public int itemId;
	
	@Size(min=1, max=20,message="入力してください")
	public String itemName;
	
	@Min(value=1,message="選択してくしてください")
	public int categoryId;
	
	@NotNull
	public int price;
//	public List<SizeForm> sizeList;
}
