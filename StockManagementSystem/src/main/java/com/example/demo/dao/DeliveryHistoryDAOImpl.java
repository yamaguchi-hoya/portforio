package com.example.demo.dao;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.DeliveryHistoryDto;

@Repository
public class DeliveryHistoryDAOImpl implements DeliveryHistoryDAO<DeliveryHistoryDto> {
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public DeliveryHistoryDAOImpl() {
		super();
	}

	@Override
	public List<DeliveryHistoryDto> getAll() {
		Query query = entityManager.createQuery("from DeliveryHistoryEntity");
		@SuppressWarnings("unchecked")
		List<DeliveryHistoryDto> list = query.getResultList();
		entityManager.close();
		return list;
	}

	@Override
	public List<DeliveryHistoryDto> findByConditions(String destination, LocalDate startDate, LocalDate endDate) {
		StringBuilder baseQuery = new StringBuilder("from DeliveryHistoryEntity where 1=1");
		if (destination != null) {
			baseQuery.append(" and destination like :destination");
		}
		if (startDate != null) {
			baseQuery.append(" and deliveryDate >= :startDate");
		}
		if (endDate != null) {
			baseQuery.append(" and deliveryDate <= :endDate");
		}
		String finalQuery = baseQuery.toString();
		Query query = entityManager.createQuery(finalQuery);
		if (destination != null) {
			query.setParameter("destination", "%" + destination + "%");
		}
		if (startDate != null) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null) {
			query.setParameter("endDate", endDate);
		}
		System.out.println(destination);
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(finalQuery);
		@SuppressWarnings("unchecked")
		List<DeliveryHistoryDto> list = query.getResultList();
		entityManager.close();
		return list;
	}

}
