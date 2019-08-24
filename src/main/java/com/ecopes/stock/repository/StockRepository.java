package com.ecopes.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecopes.stock.model.Item;
import com.ecopes.stock.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

	List<Stock> findByItem(Item item);

	public final static String ITEM_FOR_SUBTRACT = "SELECT s FROM Stock s WHERE s.actualAmount > 0 AND s.item = :item ORDER BY s.date ASC";

	@Query(ITEM_FOR_SUBTRACT)
	List<Stock> findForSubtract(@Param("item") Item item);
	
	public final static String STOCK_ALL = "SELECT s FROM Stock s WHERE s.item = :item ORDER BY s.date ASC";

	@Query(STOCK_ALL)
	List<Stock> findAllStock(@Param("item") Item item);
}