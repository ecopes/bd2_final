package com.ecopes.stock.service;

import java.time.Instant;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopes.stock.model.History;
import com.ecopes.stock.model.Item;
import com.ecopes.stock.model.Stock;
import com.ecopes.stock.model.User;
import com.ecopes.stock.repository.ItemRepository;

@Service
public class StockService {
	@Autowired
	ItemRepository itemRepository;
	@PersistenceContext
	private EntityManager entityManager;

	public Item addStock(Item item, Double amountToAdd, Instant date, Double price, User user) {
		Stock stock = new Stock(amountToAdd, amountToAdd, item, date, price);
		item.addStock(stock);
		stock.addHistory(new History(user, stock, amountToAdd));
		itemRepository.save(item);
		entityManager.refresh(item);
		return item;
	}

	public ArrayList<Stock> subtractStock(Item item, Double amountToSubtract, User user) {
		ArrayList<Stock> stocks = new ArrayList<Stock>();
		for (Stock stock : item.getStock()) {
			stocks.add(stock);
			if (stock.getActualAmount() >= amountToSubtract) {
				stock.setActualAmount(stock.getActualAmount() - amountToSubtract);
				stock.addHistory(new History(user, stock, amountToSubtract * -1));
				amountToSubtract = 0.0;
				break;
			} else {
				stock.addHistory(new History(user, stock, stock.getActualAmount() * -1));
				amountToSubtract = amountToSubtract - stock.getActualAmount();
				stock.setActualAmount(0.0);
			}
		}
		if (amountToSubtract == 0.0) {
			itemRepository.saveAndFlush(item);
			entityManager.refresh(item);
			return stocks;
		}
		return new ArrayList<Stock>();
	}
}
