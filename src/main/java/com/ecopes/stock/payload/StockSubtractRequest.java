package com.ecopes.stock.payload;

public class StockSubtractRequest {
	private Long itemId;
	private Double amount;

	public StockSubtractRequest(Long itemId, Double amount) {
		super();
		this.itemId = itemId;
		this.amount = amount;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
