package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.StockListDto;
import com.example.demo.entity.ItemFormEntity;
import com.example.demo.entity.SizeFormEntity;
import com.example.demo.entity.StockFormEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockRecordRepositoryImpl implements StockRecordRepository {
	private final JdbcTemplate jdbcTemplate;

	@Override
	public void wrightRecordShipping(StockFormEntity form, int stockId) {
		String sql = 
				"INSERT INTO record " +
				"(stock_id, amount, register_date, person, comment, execute)" +
				"VALUES(?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, stockId,
								 form.getAmount(),
								 form.getRegisterDate(),
								 form.getPerson(),
								 form.getComment(),
								 "出荷"
							);
	}

	@Override
	public void wrightRecordArrival(StockFormEntity form, int stockId) {
		String sql = 
				"INSERT INTO record " +
				"(stock_id, amount, register_date, person, comment, execute)" +
				"VALUES(?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, stockId,
								 form.getAmount(),
								 form.getRegisterDate(),
								 form.getPerson(),
								 form.getComment(),
								 "入荷"
							);
	}

	@Override
	public void stockShipping(int updateAmount, int stockId) {
			String updateSql = "UPDATE stock SET amount = ? WHERE stock_id = ?";
			jdbcTemplate.update(updateSql, updateAmount, stockId);	
	}
	@Override
	public int serchStockId(StockFormEntity form) {
		int stockId = jdbcTemplate.queryForObject("SELECT stock_id FROM stock WHERE item_id = ? AND size_id = ?", 
				int.class,
				form.getItemId(),
				form.getSizeId());
		return stockId;
	}
	@Override
	public int serchStockId(int itemId, int sizeId) {
		int stockId = jdbcTemplate.queryForObject("SELECT stock_id FROM stock WHERE item_id = ? AND size_id = ?", 
				int.class,
				itemId,
				sizeId);
		return stockId;
	}
	@Override
	public int currentAmount(int stockId) {
		int currentAmount = jdbcTemplate.queryForObject("SELECT amount FROM stock WHERE stock_id = ? ", int.class, stockId);
		return currentAmount;
	}

	@Override
	public void stockArrival(int updateAmount, int stockId) {
		String updateSql = "UPDATE stock SET amount = ? WHERE stock_id = ?";
		jdbcTemplate.update(updateSql, updateAmount, stockId);
	}

	@Override
	public void registToStockByItem(ItemFormEntity form, int latestItemId) {
		String registStockSql = 
				"INSERT INTO stock (category_id, item_id, size_id, amount, price) VALUES(?, ?, 0, 0, ?)";
		jdbcTemplate.update(registStockSql,
							form.getCategoryId(),
							latestItemId,
							form.getPrice()
		);	
	}

	@Override
	public void registToStockBySize(ItemFormEntity form, List<String> sizes, int latestItemId) {
		for (String size : sizes) {
			String sizeSql = "SELECT size_id FROM size WHERE size_name = ? and item_id = ?";
			int sizeId = jdbcTemplate.queryForObject(sizeSql, Integer.class, size, latestItemId);
			String registStockSql =
					"INSERT INTO stock (category_id, item_id, size_id, amount, price) VALUES(?, ?, ?, 0, ?) ";
			jdbcTemplate.update(registStockSql,
								form.getCategoryId(),
								latestItemId,
								sizeId,
								form.getPrice()
					);
			SizeFormEntity fSize = new SizeFormEntity();
			fSize.setItemId(latestItemId);
			fSize.setSizeName(size);
		}
	}
	
	@Override
	public void deleteStockByItemId(int itemId) {
		String sql = "DELETE FROM stock WHERE item_id = ?";
		jdbcTemplate.update(sql,itemId);
		
	}

	@Override
	public List<StockListDto> searchStockByCategoryId(int categoryId) {
		String sql = "SELECT stock_id, category_name, item_name, size_name, stock.amount, stock.price "
					+ "FROM stock "
					+ "LEFT JOIN category ON stock.category_id = category.category_id "
					+ "LEFT JOIN item ON stock.item_id = item.item_id "
					+ "LEFT JOIN size ON stock.size_id =  size.size_id"
					+ " WHERE stock.category_id = ?";
		List<Map<String, Object>> stockList = jdbcTemplate.queryForList(sql, categoryId);
		List<StockListDto> list = new ArrayList<>();
		for (Map<String, Object> stock : stockList) {
			if(stock.get("item_name") == null) {
				continue;
			}
			String size = (String)stock.get("size_name");
			if (size == null) {
				size = "-";
			}
			list.add(new StockListDto(
					(String)stock.get("category_name"),
					(String)stock.get("item_name"),
					size,
					(Integer)stock.get("amount"),
					(Integer)stock.get("price")
					));
		}
		return list;
	}

}
