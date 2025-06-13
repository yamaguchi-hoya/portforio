package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SizeDto {
	private int sizeId;
	private String sizeName;
	private int itemId;
}
