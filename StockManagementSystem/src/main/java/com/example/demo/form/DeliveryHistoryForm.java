package com.example.demo.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class DeliveryHistoryForm {
	private String destination;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	private LocalDate DeliveryDate;
}
