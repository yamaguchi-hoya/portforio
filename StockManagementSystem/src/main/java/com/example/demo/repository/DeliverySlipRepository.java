package com.example.demo.repository;

import com.example.demo.form.DeliveryForm;

public interface DeliverySlipRepository {
	void saveDocument(byte[] byteData);
	Integer getLatestId();
	void updateDeliveryRecord(byte[] byteData, DeliveryForm form, int latestId);
	byte[] getDeliverySlipById(Integer id);
}
