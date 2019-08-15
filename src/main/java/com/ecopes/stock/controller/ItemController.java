package com.ecopes.stock.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecopes.stock.exception.ResourceNotFoundException;
import com.ecopes.stock.model.Item;
import com.ecopes.stock.repository.ItemRepository;

@RestController
@RequestMapping("/api/item")
public class ItemController {

	@Autowired
	ItemRepository itemRepository;

	@GetMapping("")
	public List<Item> getAllItems() {
		return itemRepository.findAll();
	}

	@GetMapping("/{id}")
	public Item getItemById(@PathVariable(value = "id") Long itemId) {
		return itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
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
		if (itemDetails.getAmount() != null) {
			item.setAmount(itemDetails.getAmount());
		}

		Item updatedItem = itemRepository.save(item);
		return updatedItem;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable(value = "id") Long itemId) {
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

		itemRepository.delete(item);

		return ResponseEntity.ok().build();
	}
}
