package com.example.demo.form;

import java.sql.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class DeliveryForm {
	@Size(min=1, max=20,message="入力してください。")
	private String destination;
	
	private String title;
	
	@NotNull(message="入力してください。")
	private Date deliveryDate;
	
	@Size(min=1, max=20,message="入力してください。")
	private String person;
	
	private String note;
}
