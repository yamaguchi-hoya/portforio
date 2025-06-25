package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import com.example.demo.dto.CompanyDto;
import com.example.demo.dto.DeliveryDashboardDtoWrapper;
import com.example.demo.dto.DeliveryItemDto;
import com.example.demo.form.DeliveryForm;
import com.example.demo.form.DeliveryItemForm;
import com.example.demo.form.DeliveryItemFormWrapper;

public interface DeliverySlipService {
	public void createDeliverySlip(DeliveryForm deliveryForm, DeliveryItemFormWrapper deliveryItemFormWrapper, CompanyDto company, List<String> check, HttpServletResponse response);
	public byte[] previewDeliverySlip(DeliveryForm deliveryForm, DeliveryItemFormWrapper list, CompanyDto company);
	public byte[] viewDeliveryHistory(Integer id);
	public void downloadDocument(Integer id,  HttpServletResponse response);
	public List<DeliveryItemDto> deliveryItemAddToList(DeliveryItemForm form, List<DeliveryItemDto> list);
	public DeliveryItemFormWrapper deliveryItemAddToWrapper(DeliveryItemForm form, DeliveryItemFormWrapper list);
	public DeliveryItemFormWrapper deliveryItemRemoveFromWrapper(DeliveryItemFormWrapper list, int itemNo);
	public DeliveryDashboardDtoWrapper getDeliveryDashboard(LocalDate nowDate);
}
