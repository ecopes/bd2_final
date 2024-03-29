package com.ecopes.stock.controller;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.ecopes.stock.exception.ResourceNotFoundException;
import com.ecopes.stock.exception.SubtractException;
import com.ecopes.stock.model.Item;
import com.ecopes.stock.model.Stock;
import com.ecopes.stock.model.User;
import com.ecopes.stock.payload.StockAddRequest;
import com.ecopes.stock.payload.StockSubtractRequest;
import com.ecopes.stock.repository.HistoryRepository;
import com.ecopes.stock.repository.ItemRepository;
import com.ecopes.stock.repository.StockRepository;
import com.ecopes.stock.repository.UserRepository;
import com.ecopes.stock.service.StockService;

@RestController
@RequestMapping("/api/item")
public class ItemController {

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
		loggingFilter.setIncludeClientInfo(true);
		loggingFilter.setIncludeQueryString(true);
		loggingFilter.setIncludePayload(true);
		loggingFilter.setIncludeHeaders(true);
		loggingFilter.setAfterMessagePrefix("REQUEST DATA : \n");
		return loggingFilter;
	}

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	StockRepository stockRepository;

	@Autowired
	HistoryRepository historyRepository;

	@Autowired
	UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	StockService stockService;

	@GetMapping("")
	public List<Item> getAllItems() {
		return itemRepository.findAll();
	}

	@GetMapping("/{id}")
	public Item getItemById(@PathVariable(value = "id") Long itemId) {
		return itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
	}

	@GetMapping("/history/{id}")
	public List<Stock> getHistoryByItemId(@PathVariable(value = "id") Long itemId) {
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
		return stockRepository.findAllStock(item);
	}

	@PostMapping("")
	public Item createItem(@Valid @RequestBody Item item) {
		return itemRepository.save(item);
	}

	@PutMapping("/{id}")
	public Item updateItem(@PathVariable(value = "id") Long itemId, @RequestBody Item itemDetails) {

		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

		if (itemDetails.getDescription() != null) {
			item.setDescription(itemDetails.getDescription());
		}

		Item updatedItem = itemRepository.save(item);
		return updatedItem;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable(value = "id") Long itemId) {
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

		itemRepository.delete(item);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/addStock")
	@Transactional
	public Item addStock(@Valid @RequestBody StockAddRequest stockDetails) {
		Item item = itemRepository.findById(stockDetails.getItemId())
				.orElseThrow(() -> new ResourceNotFoundException("Item", "id", stockDetails.getItemId()));
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.get();

		return this.stockService.addStock(item, stockDetails.getAmount(), stockDetails.getDate(),
				stockDetails.getPrice(), user);
	}

	@PostMapping("/subtractStock")
	@Transactional
	public ArrayList<Stock> subtractStock(@Valid @RequestBody StockSubtractRequest stockDetails) {
		Item item = itemRepository.findById(stockDetails.getItemId())
				.orElseThrow(() -> new ResourceNotFoundException("Item", "id", stockDetails.getItemId()));
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.get();

		ArrayList<Stock> stocks = this.stockService.subtractStock(item, stockDetails.getAmount(), user);
		if (stocks.size() > 0) {
			return stocks;
		}
		throw new SubtractException("No hay suficiente Stock");
	}

}
