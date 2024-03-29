package com.ecopes.stock.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "history")
public class History extends DateAudit implements Serializable {

	private static final long serialVersionUID = -6995850806931827823L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@JsonIgnore
	@Cascade(CascadeType.DETACH)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, cascade = javax.persistence.CascadeType.ALL)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "stock_id")
	@JsonIgnore
	private Stock stock;

	@NotNull
	@Column(scale = 2)
	private Double amount;

	public History(@NotNull User user, @NotNull Stock stock, @NotNull Double amount) {
		super();
		this.user = user;
		this.stock = stock;
		this.amount = amount;
	}

	public History() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
