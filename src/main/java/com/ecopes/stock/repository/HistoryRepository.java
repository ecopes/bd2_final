package com.ecopes.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecopes.stock.model.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

}