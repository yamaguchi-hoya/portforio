package com.example.demo.form;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.DeliveryItemDto;

import lombok.Data;

@Data
public class DeliveryItemFormWrapper {
	private List<DeliveryItemDto> deliveryItemList = new ArrayList<>();
}
