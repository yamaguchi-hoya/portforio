package com.example.demo.spec;

import java.time.LocalDate;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.demo.entity.StockEntity;

@Component
public class StockSpecifications {
	public Specification<StockEntity> withFetchJoins() {
	    return (root, query, cb) -> {
	        root.fetch("item",JoinType.INNER);
	        root.fetch("category",JoinType.LEFT);
	        root.fetch("size",JoinType.LEFT);
	        return null;
	    };
	}
	public Specification<StockEntity> hasCategoryId(Integer categoryId) {
		return (root, query, cb) -> {
			if (categoryId == null || categoryId == 0) return null;
//			Join<HistoryFormEntity, Category> category = root.join("category");
			return cb.equal(root.get("category").get("categoryId"), categoryId);
		};
	}
	public Specification<StockEntity> hasItemId(Integer itemId) {
		return (root, query, cb) -> {
			if (itemId == null || itemId == 0) return null;
//			Join<HistoryFormEntity, Item> item = root.join("item");
			return cb.equal(root.get("item").get("itemId"), itemId);
		};
	}
	public Specification<StockEntity> hasSizeId(Integer sizeId) {
		return (root, query, cb) -> {
			if (sizeId == null || sizeId == 0) return null;
//			if (sizeId == 0) {
//				return cb.isNull(root.get("size"));
//			}
//			Join<HistoryFormEntity, SizeFormEntity> size = root.join("size");
			return cb.equal(root.get("size").get("sizeId"), sizeId);
		};
	}
	public Specification<StockEntity> betweenDate(LocalDate startDate, LocalDate endDate) {
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
	public Specification<StockEntity> hasExecute(String execute) {
		return (root, query, cb) -> {
			if (execute == null || execute.equals("all")) return null;
			return cb.equal(root.get("execute"), execute);
		};
	}
}
