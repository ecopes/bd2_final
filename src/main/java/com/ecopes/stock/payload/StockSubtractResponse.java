package com.ecopes.stock.payload;

import java.util.List;

import com.ecopes.stock.model.History;
import com.ecopes.stock.model.Item;

public class StockSubtractResponse {

	public Item item;
	public List<History> historyList;

	public StockSubtractResponse(Item item, List<History> historyList) {
		super();
		this.item = item;
		this.historyList = historyList;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<History> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<History> historyList) {
		this.historyList = historyList;
	}

}
