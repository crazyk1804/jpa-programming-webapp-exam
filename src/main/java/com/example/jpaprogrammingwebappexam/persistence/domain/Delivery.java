package com.example.jpaprogrammingwebappexam.persistence.domain;

import com.example.jpaprogrammingwebappexam.persistence.domain.enums.DeliveryStatus;

import javax.persistence.*;

@Entity
public class Delivery {
	@Id @GeneratedValue
	@Column(name="DELIVERY_ID")
	private Long id;
	private Address address;
	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;
	@OneToOne(mappedBy = "delivery")
	private Order order;

	public Delivery() {}

	public Delivery(Address address) {
		this.address = address.clone();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public DeliveryStatus getStatus() {
		return status;
	}

	public void setStatus(DeliveryStatus status) {
		this.status = status;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
