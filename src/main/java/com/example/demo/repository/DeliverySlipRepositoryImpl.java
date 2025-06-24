package com.example.demo.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.DeliveryDashboardDto;
import com.example.demo.dto.DeliveryDashboardDtoPre;
import com.example.demo.form.DeliveryForm;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeliverySlipRepositoryImpl implements DeliverySlipRepository {
	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
	public void updateDeliveryRecord(byte[] byteData, DeliveryForm form, int latestId, String itemList) {
		String sql = "INSERT INTO delivery_record (delivery_date, delivery_destination, delivery_slip_id, item_list) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, form.getDeliveryDate(), form.getDestination(), latestId, itemList);
	}

	@Override
	public byte[] getDeliverySlipById(Integer id) {
		String sql = "SELECT delivery_slip FROM delivery_slip where document_id = ?";
		byte[] document = jdbcTemplate.queryForObject(sql, byte[].class, id);
		return document;
	}

	@Override
	public List<DeliveryDashboardDto> getForDashBoard(List<DeliveryDashboardDtoPre> list) {
		List<Integer> itemIds = new ArrayList<>();
		for (DeliveryDashboardDtoPre dto : list) {
			Integer itemId = dto.getItemId();
			if (itemId != null) {
				itemIds.add(itemId);				
			} else {
				itemIds.add(0);	
			}
		}
		if (itemIds.isEmpty()) {
			return Collections.emptyList(); // 安全にスキップ
		}
		String sql = "SELECT item_name, item_id,  price, category.category_name FROM item JOIN category ON item.category_id = category.category_id WHERE item_id IN (:itemIds)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("itemIds", itemIds);
		List<Map<String, Object>> dbList = namedParameterJdbcTemplate.queryForList(sql, params);
		List<Map<String, Object>> itemList = new ArrayList<>();
		for (DeliveryDashboardDtoPre dto : list) {
			Map<String, Object> item = new TreeMap<>();
			item.put("itemName", null);
			item.put("price",0);
			item.put("categoryName", null);
			for (Map<String, Object> map : dbList) {
				if (dto.getItemId() == map.get("item_id")) {
					item.replace("itemName", map.get("item_name"));
					item.replace("price", map.get("price"));
					item.replace("categoryName", map.get("category_name"));
				}
			}
			itemList.add(item);
		}
		List<DeliveryDashboardDto> dashboardList = new ArrayList<>();
		for (int i=0; i<list.size(); i++) {
			Integer amount = 0;
			Integer unitPrice = (int)itemList.get(i).get("price");
			int price = 0; 
			if (list.get(i).getAmount() != null) {
				amount = list.get(i).getAmount();
				price = amount * unitPrice;				
			}
			DeliveryDashboardDto dto = new DeliveryDashboardDto((LocalDate)list.get(i).getDeliveryDate(), (String)itemList.get(i).get("categoryName"), (Integer)price);
			dashboardList.add(dto);
		}
		return dashboardList;
	}

}

