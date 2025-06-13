package com.example.demo.form;

import java.sql.Date;

import lombok.Data;

@Data
public class HistorySearchForm {
	private Integer categoryId;	
	private String categoryName;
	private Integer itemId;
	private String itemName;
	private Integer sizeId;
	private String sizeName;
	private Integer amount;
	private Date registerDate;
	private String person;
	private String comment;
	private Date startDate;
	private Date endDate;
	private String execute;

}
