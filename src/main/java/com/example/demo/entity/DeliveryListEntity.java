package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "delivery_record")
public class DeliveryListEntity {
	@Id
	private String deliveryId;	
	
	@Column(name = "delivery_date")
	private LocalDate deliveryDate;
	
	@Column(name = "item_list")
	private String itemList;
}
