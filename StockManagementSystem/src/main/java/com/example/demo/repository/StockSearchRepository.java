package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.StockEntity;

public interface StockSearchRepository extends JpaRepository<StockEntity, Integer>, JpaSpecificationExecutor<StockEntity>{
	
}
