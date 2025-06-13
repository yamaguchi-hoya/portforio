package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.ItemDto;
import com.example.demo.entity.ItemFormEntity;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class RegistItemRepositoryImpl implements RegistItemRepository {
	private final JdbcTemplate jdbcTemplate;

	@Override
	public void regist(ItemFormEntity form) {
		String registItemSql = 
				"INSERT INTO item " +
				"(item_name, category_id, price)" +
				"VALUES(?,?,?)";
		jdbcTemplate.update(registItemSql, 
								form.getItemName(), 
								form.getCategoryId(),
								form.getPrice()
							);
	}

	@Override
	public void remove(ItemFormEntity form) {
		String sql =
				"DELETE FROM item " +
				"WHERE item_id = ?";
		jdbcTemplate.update(sql,form.getItemId());

	}
	
	@Override
	public void change(ItemFormEntity form) {
		String sql = 
				"UPDATE item SET item_name = ?, price = ? WHERE item_id = ?";
		jdbcTemplate.update(sql, form.getItemName(), 
								 form.getPrice(),
								 form.getItemId());
	}
	
	@Override
	public void registToStock(ItemFormEntity form) {
		String sql = "SELECT LAST_INSERT_ID()";
		Integer latestItemId = jdbcTemplate.queryForObject(sql,Integer.class);
		if (latestItemId == null) {
			throw new IllegalStateException("LAST_INSERT_ID()の取得に失敗しました");
		}
		String registStockSql = 
				"INSERT INTO stock (category_id, item_id, amount,price) VALUES(?, ?, 0, ?)";
		jdbcTemplate.update(registStockSql,
							form.getCategoryId(),
							latestItemId,
							form.getPrice()
		);
	}

	@Override
	public List<ItemDto> getAll() {
		String sql = "select item_id, item_name, category_id, price from item";
		List<Map<String,Object>> itemList = jdbcTemplate.queryForList(sql);
		List<ItemDto> list = new ArrayList<>();
		for (Map<String,Object> item: itemList) {
			list.add(new ItemDto(
					(int)item.get("item_id"),
					(String)item.get("item_name"),
					(int)item.get("category_id"),
					(int)item.get("price")
					));
		}
		return list;
	}

	@Override
	public List<ItemDto> getSelectByItemId(int id) {
		String sql = "select item_id, item_name, category_id, price from item where item_Id = ?";
		List<Map<String,Object>> itemList = jdbcTemplate.queryForList(sql,id);
		List<ItemDto> list = new ArrayList<>();
		for (Map<String,Object> item:itemList) {
			list.add(new ItemDto(
					(int)item.get("item_id"),
					(String)item.get("item_name"),
					(int)item.get("category_id"),
					(int)item.get("price")
					));
		}
		return list;
	}
	public ItemDto getSelectDtoByItemId(int id) {
		String sql = "select item_id, item_name, category_id, price from item where item_Id = ?";
		Map<String,Object> itemList = jdbcTemplate.queryForMap(sql,id);
		if (itemList == null) {
			return null;
		}
		ItemDto item = new ItemDto(
				(int)itemList.get("item_id"),
				(String)itemList.get("item_name"),
				(int)itemList.get("category_id"),
				(int)itemList.get("price")	
				); 
		return item;
	}

	@Override
	public List<ItemDto> getSelectByCategoryId(int id) {
		String sql = "select item_id, item_name, category_id, price from item where category_id = ?";
		List<Map<String,Object>> itemList = jdbcTemplate.queryForList(sql,id);
		List<ItemDto> list = new ArrayList<>();
		for (Map<String,Object> item:itemList) {
			list.add(new ItemDto(
					(int)item.get("item_id"),
					(String)item.get("item_name"),
					(int)item.get("category_id"),
					(int)item.get("price")
					));
		}
		return list;
	}

	@Override
	public List<ItemDto> getSelectByLatestId() {
		String sql = "select item_id, item_name, category_id, price from item where item_id = (select max(item_id) from item)";
		List<Map<String,Object>> itemList = jdbcTemplate.queryForList(sql);
		List<ItemDto> list = new ArrayList<>();
		for (Map<String,Object> item : itemList) {
			list.add(new ItemDto(
					(int)item.get("item_id"),
					(String)item.get("item_name"),
					(int)item.get("category_id"),
					(int)item.get("price")
					));
		}
		return list;
	}

}
