package com.example.demo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.DeliveryDashboardDtoPre;
import com.example.demo.dto.DeliveryListDto;
import com.example.demo.entity.DeliveryListEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class DeliveryDashboardDAOImpl implements DeliveryDashboardDAO<DeliveryDashboardDtoPre> {
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public DeliveryDashboardDAOImpl() {
		super();
	}
	
	@Override
	public Map<String, Object> findForDashboard(LocalDate startDate, LocalDate endDate) {
		StringBuilder baseQuery = new StringBuilder("from DeliveryListEntity where 1=1");
		if (startDate != null) {
			baseQuery.append(" and deliveryDate >= :startDate");
		}
		if (endDate != null) {
			baseQuery.append(" and deliveryDate <= :endDate");
		}
		String finalQuery = baseQuery.toString();
		Query query = entityManager.createQuery(finalQuery);
		if (startDate != null) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null) {
			query.setParameter("endDate", endDate);
		}
		@SuppressWarnings("unchecked")
		List<DeliveryListEntity> list = query.getResultList();
		entityManager.close();
		ObjectMapper objectMapper = new ObjectMapper();
		List<DeliveryListDto> dtoList = new ArrayList<>();
		List<DeliveryListEntity> entitylist = new ArrayList<>();
		for (DeliveryListEntity listItem : list) {
			Map<Integer, Integer> itemList;
			try {
				itemList = objectMapper.readValue(listItem.getItemList(), new TypeReference<Map<Integer, Integer>>() {});
				DeliveryListDto dto = new DeliveryListDto(listItem.getDeliveryDate(), itemList);
				dtoList.add(dto);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		List<DeliveryDashboardDtoPre> dashboardList = new ArrayList<>();
		for (DeliveryListDto dto : dtoList) {
			LocalDate date = dto.getDeliveryDate();
			Map<Integer, Integer> itemMap = dto.getItemList();
			if (dto.getItemList() == null) {
				DeliveryDashboardDtoPre pre = new DeliveryDashboardDtoPre(date, null, null);
				dashboardList.add(pre);
			} else {
				for (Map.Entry<Integer, Integer> entry : itemMap.entrySet()) {
					Integer itemId = entry.getKey();
					Integer amount = entry.getValue();
					DeliveryDashboardDtoPre pre = new DeliveryDashboardDtoPre(date, itemId, amount);
					dashboardList.add(pre);
				}	
			}
		}
		Map<String,Object> map = new TreeMap<>();
		map.put("deliveryDashboardDtoPre", dashboardList);
		map.put("deliveryListEntity", list);
		return map;
	}

}
