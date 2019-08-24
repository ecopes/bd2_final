package com.ecopes.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecopes.stock.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
}
