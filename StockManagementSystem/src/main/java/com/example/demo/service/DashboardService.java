package com.example.demo.service;

import com.example.demo.dto.DashboardDto;

public interface DashboardService {
	public DashboardDto getHistoryListMonthly();
	public DashboardDto getHistoryListMonthlyBefore(int year, int month);
	public DashboardDto getHistoryListMonthlyAfter(int year, int month);
}
