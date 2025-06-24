package com.example.demo.repository;

import java.util.List;

import com.example.demo.dto.DeliveryDashboardDto;
import com.example.demo.dto.DeliveryDashboardDtoPre;
import com.example.demo.form.DeliveryForm;

public interface DeliverySlipRepository {
	void saveDocument(byte[] byteData);
	Integer getLatestId();
	void updateDeliveryRecord(byte[] byteData, DeliveryForm form, int latestId, String itemList);
	byte[] getDeliverySlipById(Integer id);
	List<DeliveryDashboardDto> getForDashBoard(List<DeliveryDashboardDtoPre> list);
}
