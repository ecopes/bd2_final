package com.ecopes.stock.payload;

import java.time.Instant;

public class StockAddRequest {
	private Long itemId;
	private Double amount;
	private Instant date;
	private Double price;

	public StockAddRequest(Long itemId, Double amount, Instant date, Double price) {
		super();
		this.itemId = itemId;
		this.amount = amount;
		this.date = date;
		this.price = price;
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

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
