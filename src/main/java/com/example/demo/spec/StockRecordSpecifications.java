package com.example.demo.spec;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.demo.entity.HistoryFormEntity;

@Component
public class StockRecordSpecifications {
	public Specification<HistoryFormEntity> withFetchJoins() {
	    return (root, query, cb) -> {
	        root.fetch("stock",JoinType.INNER);
	        return null;
	    };
	}
	public Specification<HistoryFormEntity> hasStockId(List<Integer> stockIdList) {
		return (root, query, cb) -> {
//			if (stockId == null || stockId == 0) return null;
//			Join<HistoryFormEntity, Category> category = root.join("category");
			return root.get("stock").get("stock_id").in(stockIdList);
		};
	}
	
	public Specification<HistoryFormEntity> betweenDate(LocalDate startDate, LocalDate endDate) {
		return (root, query, cb) -> {
			Path<LocalDate> datePath = root.get("registerDate");
			if (startDate != null && endDate != null) {
				return cb.between(datePath, startDate, endDate);
			} else if (startDate != null) {
				return cb.greaterThanOrEqualTo(datePath, startDate);
			} else if (endDate != null) {
				return cb.lessThanOrEqualTo(datePath, endDate);
			} else {
				return cb.conjunction();
			}
		};
	}
	public Specification<HistoryFormEntity> hasExecute(String execute) {
		return (root, query, cb) -> {
			if (execute == null || execute.equals("all")) return null;
			return cb.equal(root.get("execute"), execute);
		};
	}
}
