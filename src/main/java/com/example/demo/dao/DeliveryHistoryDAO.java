package com.example.demo.dao;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public interface DeliveryHistoryDAO <T> extends Serializable {
	public List<T> getAll();
	public List<T> findByConditions(String distination, LocalDate startDate, LocalDate endDate);
}
