package com.example.demo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.form.DeliveryForm;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeliverySlipRepositoryImpl implements DeliverySlipRepository {
	private final JdbcTemplate jdbcTemplate;

	@Override
	public void saveDocument(byte[] document) {
		String sql = "INSERT INTO delivery_slip (delivery_slip) VALUES (?) ";
		jdbcTemplate.update(sql, document);
	}

	@Override
	public Integer getLatestId() {
		String sql = "SELECT MAX(document_id) FROM delivery_slip";
		Integer latestId = jdbcTemplate.queryForObject(sql,Integer.class);
		return latestId;
	}

	@Override
	public void updateDeliveryRecord(byte[] byteData, DeliveryForm form, int latestId) {
		String sql = "INSERT INTO delivery_record (delivery_date, delivery_destination, delivery_slip_id) VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, form.getDeliveryDate(), form.getDestination(), latestId);
	}

	@Override
	public byte[] getDeliverySlipById(Integer id) {
		String sql = "SELECT delivery_slip FROM delivery_slip where document_id = ?";
		byte[] document = jdbcTemplate.queryForObject(sql, byte[].class, id);
		return document;
	}

}

