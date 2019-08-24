package com.ecopes.stock.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "stock")
public class Stock extends DateAudit implements Serializable {

	private static final long serialVersionUID = 7289847657234566994L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(scale = 2)
	private Double totalAmount;

	@NotNull
	@Column(scale = 2)
	private Double actualAmount;

	@NotNull
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = javax.persistence.CascadeType.ALL)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "item_id")
	private Item item;

	@OneToMany(mappedBy = "stock")
	@Cascade(CascadeType.ALL)
	private List<History> history = new ArrayList<History>();

	@NotNull
	private Instant date;

	@NotNull
	@Column(scale = 2)
	private Double price;

	public Stock(@NotNull Double totalAmount, @NotNull Double actualAmount, @NotNull Item item, @NotNull Instant date,
			@NotNull Double price) {
		super();
		this.totalAmount = totalAmount;
		this.actualAmount = actualAmount;
		this.item = item;
		this.date = date;
		this.price = price;
	}

	public Stock() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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

	public List<History> getHistory() {
		return history;
	}

	public void setHistory(List<History> history) {
		this.history = history;
	}

	public boolean addHistory(History history) {
		return this.history.add(history);
	}

	public boolean removeHistory(History history) {
		return this.history.remove(history);
	}

}
