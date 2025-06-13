package com.example.demo.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.HistoryDto;

import lombok.RequiredArgsConstructor;

@Repository
@Primary
@RequiredArgsConstructor
public class StockHistoryRepositoryImpl implements StockHistoryRepository {
	private final JdbcTemplate jdbcTemplate;
	
	@Override
	public List<HistoryDto> getAllHistory() {
//		String sql = "SELECT * FROM record";
		String sql = "category_id, category_name, item_id, item_name, size_id, size_name. amount, register_date, person, domment"
					 + "FROM record"
				 	 + "LEFT JOIN category ON stock.category_id = category.category_id "
				 	 + "LEFT JOIN item ON stock.item_id = item.item_id "
					 + "LEFT JOIN size ON stock.size_id =  size.size_id";	
		List<Map<String, Object>> historyList = jdbcTemplate.queryForList(sql);
		List<HistoryDto> list = new ArrayList<>();
		for (Map<String, Object> history : historyList) {
			list.add(new HistoryDto(
//					(Integer) history.get("category_id"),
					(String) history.get("category_name"),
//					(Integer) history.get("item_id"),
					(String) history.get("item_name"),
//					(Integer) history.get("size_id"),
					(String) history.get("size_name"),
					(Integer) history.get("amount"),
					(Date) history.get("register_date"),
					(String) history.get("person"),
					(String) history.get("comment"),
					(String) history.get("execute")
					));
		}
		return list;
	}
}
