package com.example.demo.dao;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

public interface DeliveryDashboardDAO <T> extends Serializable {
	public Map<String,Object> findForDashboard(LocalDate startDate, LocalDate endDate);
}
