package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryDashboardDtoPre {
	private LocalDate deliveryDate;
	private Integer itemId;
	private Integer amount;
}
