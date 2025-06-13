package com.example.demo.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.HistoryDto;
import com.example.demo.entity.HistoryFormEntity;
import com.example.demo.entity.SizeFormEntity;
import com.example.demo.entity.StockEntity;
import com.example.demo.repository.StockHistoryRepository;
import com.example.demo.repository.StockHistorySearchRepository;
import com.example.demo.repository.StockSearchRepository;
import com.example.demo.spec.StockRecordSpecifications;
import com.example.demo.spec.StockSpecifications;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockHistoryServiceImpl implements StockHistoryService {
	private final StockHistoryRepository repository;
	private final StockHistorySearchRepository stockHistorySearchRepository;
	private final StockSearchRepository stockSearchRepository;
	private final StockRecordSpecifications stockRecordSpecifications;
	private final StockSpecifications stockSpecifications;
	
	@Override
	public List<HistoryDto> getAllHistory() {
		List<HistoryDto> list = repository.getAllHistory();
		return list;
	}

	@Override
	public List<HistoryDto> getSearch(@RequestParam(required = false) Integer categoryId,Integer itemId,Integer sizeId, LocalDate startDate, LocalDate endDate, String execute) {
		try {
			Specification<StockEntity> stockSpec = stockSpecifications.withFetchJoins();
			if (categoryId != null && categoryId != 0) {
				stockSpec = stockSpec.and(stockSpecifications.hasCategoryId(categoryId));	
			}
			if (itemId != null && itemId != 0) {
				stockSpec = stockSpec.and(stockSpecifications.hasItemId(itemId));		
			}
			if (sizeId != null && sizeId != 0) {
				stockSpec = stockSpec.and(stockSpecifications.hasSizeId(sizeId));				
			}
			
			List<StockEntity> stockList = stockSearchRepository.findAll(stockSpec);
			List<Integer> stockIdList = new ArrayList<>();
			for (StockEntity entity : stockList) {
				stockIdList.add(entity.getStock_id());
			}
			
			Specification<HistoryFormEntity> recordSpec = stockRecordSpecifications.withFetchJoins()
			.and(stockRecordSpecifications.hasStockId(stockIdList));
			recordSpec = recordSpec.and(stockRecordSpecifications.betweenDate(startDate, endDate));
			if (execute != null && !execute.equals("all")) {
				recordSpec = recordSpec.and(stockRecordSpecifications.hasExecute(execute));
			}
			List<HistoryFormEntity> recordList = stockHistorySearchRepository.findAll(recordSpec);
//			for ( HistoryFormEntity entity : recordList) {
//				if (entity.getSize() == null) {
//					SizeFormEntity dummySize = new SizeFormEntity();
//					dummySize.setSizeName("-");
//					entity.setSize(dummySize);
//				}
//			}
			List<HistoryDto> historyList = new ArrayList<>();
			for ( HistoryFormEntity list : recordList) {
				StockEntity stock = list.getStock();
				if (stock.getSize() == null) {
					SizeFormEntity dummySize = new SizeFormEntity();
					dummySize.setSizeName("-");
					stock.setSize(dummySize);
				}
				historyList.add(new HistoryDto(
						(String)stock.getCategory().getCategoryName(),
						(String)stock.getItem().getItemName(),
						(String)stock.getSize().getSizeName(),
						(Integer)list.getAmount(),
						(Date)list.getRegisterDate(),
						(String)list.getPerson(),
						(String)list.getComment(),
						(String)list.getExecute()
						));
			}
			return historyList;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
		
	}


}
