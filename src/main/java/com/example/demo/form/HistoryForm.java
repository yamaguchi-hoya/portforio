package com.example.demo.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class HistoryForm {
	private Integer categoryId;
	
	private String categoryName;
	
	private Integer itemId;
	
	private String itemName;

	private Integer sizeId;
	
	private String sizeName;

	private Integer amount;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	private LocalDate registerDate;

	private String person;

	private String comment;
	
	private String execute;
}
