package com.example.demo.form;

import java.util.List;

import jakarta.validation.Valid;

import lombok.Data;

@Data
public class SizeFormWrapper {
	@Valid
	private List<SizeForm> sizeFormList;
}