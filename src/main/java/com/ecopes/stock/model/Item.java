package com.ecopes.stock.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "item")
public class Item extends DateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String description;
	
	@Formula("(select coalesce(sum(s.actual_amount),0) from Stock s where s.item_id = id and s.actual_amount > 0)")
	private Double totalAmount;
	
	@OneToMany(mappedBy = "item")
	@Cascade(CascadeType.ALL)
	private List<Stock> stock = new ArrayList<Stock>();

	public Item() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public List<Stock> getStock() {
		return stock;
	}

	public void setStock(List<Stock> stock) {
		this.stock = stock;
	}

}
