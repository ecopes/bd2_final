package com.ecopes.stock.payload;

import java.util.List;
import com.ecopes.stock.model.Item;
import com.ecopes.stock.model.Stock;

public class StockSubtractResponse {

	public Item item;
	public List<Stock> stockList;

	public StockSubtractResponse(Item item, List<Stock> stockList) {
		super();
		this.item = item;
		this.stockList = stockList;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<Stock> getStockList() {
		return stockList;
	}

	public void setStockList(List<Stock> stockList) {
		this.stockList = stockList;
	}
}
