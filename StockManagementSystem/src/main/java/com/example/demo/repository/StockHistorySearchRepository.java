package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.HistoryFormEntity;

public interface StockHistorySearchRepository extends JpaRepository<HistoryFormEntity, Integer>, JpaSpecificationExecutor<HistoryFormEntity>{
	
}
