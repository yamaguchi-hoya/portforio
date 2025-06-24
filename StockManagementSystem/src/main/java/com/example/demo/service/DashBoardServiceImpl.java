package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.DashboardDto;
import com.example.demo.dto.HistoryDto;
import com.example.demo.repository.RegistCategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DashBoardServiceImpl implements DashboardService {
	private final StockHistoryService stockHistoryService;
	private final RegistCategoryRepository registCategoryRepository;

	@Override
	public DashboardDto getHistoryListMonthly() {
		DashboardDto Ddto = new DashboardDto();
		Map<Integer, Integer> shippingCountMap = new TreeMap<>();
		Map<Integer, Integer> arrivalCountMap = new TreeMap<>();
		LocalDate nowDate = LocalDate.now();
		int year = nowDate.getYear();
		int month = nowDate.getMonthValue();
		int daysInMonth = nowDate.lengthOfMonth();
		for (int i=1; i<=daysInMonth; i++) {
			shippingCountMap.put(i, 0);
			arrivalCountMap.put(i, 0);
		}
		List<HistoryDto> historyList = stockHistoryService.getSearchMonthly(nowDate);
		Map<String, Integer> shippingCategoryMap = new TreeMap<>();
		Map<String, Integer> arrivalCategoryMap = new TreeMap<>();
		List<CategoryDto> categoryList = registCategoryRepository.getAll();
		for(int i=0; i<categoryList.size(); i++) {
			shippingCategoryMap.put(categoryList.get(i).getCategoryName(), 0);
			arrivalCategoryMap.put(categoryList.get(i).getCategoryName(), 0);
		}
		Calendar cal = Calendar.getInstance();
		for (HistoryDto dto : historyList) {
			cal.setTime(dto.getRegisterDate());
			int y = cal.get(Calendar.YEAR);
			int m = cal.get(Calendar.MONTH) + 1;
			int d = cal.get(Calendar.DAY_OF_MONTH);
			String category = dto.getCategoryName();
			if (y == year && m == month) {
				if (dto.getExecute().equals("出荷")) {
					shippingCountMap.put(d, shippingCountMap.get(d)+1);	
					shippingCategoryMap.replace(category, shippingCategoryMap.get(category) + 1);
				} else {
					arrivalCountMap.put(d, arrivalCountMap.get(d)+1);
					arrivalCategoryMap.replace(category, arrivalCategoryMap.get(category) + 1);
				}
			}
		}
		List<Integer> dayList = new ArrayList<>(shippingCountMap.keySet());
		Ddto.setYear(year);
		Ddto.setMonth(month);
		Ddto.setDayList(dayList);
		Ddto.setShippingCountMap(shippingCountMap);
		Ddto.setArrivalCountMap(arrivalCountMap);
		Ddto.setShippingCategoryMap(shippingCategoryMap);
		Ddto.setArrivalCategoryMap(arrivalCategoryMap);
		return Ddto;
	}

	@Override
	public DashboardDto getHistoryListMonthlyBefore(int year, int month) {
		DashboardDto Ddto = new DashboardDto();
		Map<Integer, Integer> shippingCountMap = new TreeMap<>();
		Map<Integer, Integer> arrivalCountMap = new TreeMap<>();
		if (month == 1) {
			year = year - 1 ;			
			month = 12;
		} else {
			month = month - 1;
		}
		LocalDate newDate = LocalDate.of(year,month,15);
		int daysInMonth = newDate.lengthOfMonth();
		for (int i=1; i<=daysInMonth; i++) {
			shippingCountMap.put(i, 0);
			arrivalCountMap.put(i, 0);
		}
		List<HistoryDto> historyList = stockHistoryService.getSearchMonthly(newDate);
		Map<String, Integer> shippingCategoryMap = new TreeMap<>();
		Map<String, Integer> arrivalCategoryMap = new TreeMap<>();
		List<CategoryDto> categoryList = registCategoryRepository.getAll();
		for(int i=0; i<categoryList.size(); i++) {
			shippingCategoryMap.put(categoryList.get(i).getCategoryName(), 0);
			arrivalCategoryMap.put(categoryList.get(i).getCategoryName(), 0);
		}
		Calendar cal = Calendar.getInstance();
		for (HistoryDto dto : historyList) {
			cal.setTime(dto.getRegisterDate());
			int y = cal.get(Calendar.YEAR);
			int m = cal.get(Calendar.MONTH) + 1;
			int d = cal.get(Calendar.DAY_OF_MONTH);
			String category = dto.getCategoryName();
			if (y == year && m == month) {
				if (dto.getExecute().equals("出荷")) {
					shippingCountMap.put(d, shippingCountMap.get(d)+1);	
					shippingCategoryMap.replace(category, shippingCategoryMap.get(category) + 1);
				} else {
					arrivalCountMap.put(d, arrivalCountMap.get(d)+1);
					arrivalCategoryMap.replace(category, arrivalCategoryMap.get(category) + 1);
				}
			}
		}
		List<Integer> dayList = new ArrayList<>(shippingCountMap.keySet());
		Ddto.setYear(year);
		Ddto.setMonth(month);
		Ddto.setDayList(dayList);
		Ddto.setShippingCountMap(shippingCountMap);
		Ddto.setArrivalCountMap(arrivalCountMap);
		Ddto.setShippingCategoryMap(shippingCategoryMap);
		Ddto.setArrivalCategoryMap(arrivalCategoryMap);
		return Ddto;
	}

	@Override
	public DashboardDto getHistoryListMonthlyAfter(int year, int month) {
		DashboardDto Ddto = new DashboardDto();
		Map<Integer, Integer> shippingCountMap = new TreeMap<>();
		Map<Integer, Integer> arrivalCountMap = new TreeMap<>();
		if (month == 12) {
			year = year + 1 ;			
			month = 1;
		} else {
			month = month + 1;
		}
		LocalDate newDate = LocalDate.of(year,month,15);
		int daysInMonth = newDate.lengthOfMonth();
		for (int i=1; i<=daysInMonth; i++) {
			shippingCountMap.put(i, 0);
			arrivalCountMap.put(i, 0);
		}
		List<HistoryDto> historyList = stockHistoryService.getSearchMonthly(newDate);
		Map<String, Integer> shippingCategoryMap = new TreeMap<>();
		Map<String, Integer> arrivalCategoryMap = new TreeMap<>();
		List<CategoryDto> categoryList = registCategoryRepository.getAll();
		for(int i=0; i<categoryList.size(); i++) {
			shippingCategoryMap.put(categoryList.get(i).getCategoryName(), 0);
			arrivalCategoryMap.put(categoryList.get(i).getCategoryName(), 0);
		}
		Calendar cal = Calendar.getInstance();
		for (HistoryDto dto : historyList) {
			cal.setTime(dto.getRegisterDate());
			int y = cal.get(Calendar.YEAR);
			int m = cal.get(Calendar.MONTH) + 1;
			int d = cal.get(Calendar.DAY_OF_MONTH);
			String category = dto.getCategoryName();
			if (y == year && m == month) {
				if (dto.getExecute().equals("出荷")) {
					shippingCountMap.put(d, shippingCountMap.get(d)+1);	
					shippingCategoryMap.replace(category, shippingCategoryMap.get(category) + 1);
				} else {
					arrivalCountMap.put(d, arrivalCountMap.get(d)+1);
					arrivalCategoryMap.replace(category, arrivalCategoryMap.get(category) + 1);
				}
			}
		}
		List<Integer> dayList = new ArrayList<>(shippingCountMap.keySet());
		Ddto.setYear(year);
		Ddto.setMonth(month);
		Ddto.setDayList(dayList);
		Ddto.setShippingCountMap(shippingCountMap);
		Ddto.setArrivalCountMap(arrivalCountMap);
		Ddto.setShippingCategoryMap(shippingCategoryMap);
		Ddto.setArrivalCategoryMap(arrivalCategoryMap);
		return Ddto;
	}

}
