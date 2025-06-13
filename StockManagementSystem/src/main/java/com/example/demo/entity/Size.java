package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "size")
public class Size {
	@Id
	@Column(name = "size_id")
	private int sizeId;
	
	@Column(name = "size_name")
	private String sizeName;
	
	@Column(name = "item_id")
	private int itemId;
	
}
