package com.example.jpaprogrammingwebappexam.persistence.domain;

import javax.persistence.*;

@Entity
public class OrderItem {
	@Id @GeneratedValue
	@Column(name = "ORDER_ITEM_ID")
	private Long id;
	private int orderPrice;
	private int count;
	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private Order order;
	@ManyToOne
	@JoinColumn(name = "ITEM_ID")
	private Item item;

	public static OrderItem createOrderItem(Item item, int orderPrice, int itemCount) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(itemCount);
		item.removeStock(itemCount);
		return orderItem;
	}

	public void cancel() {
//		if(order==null) return;
//		order.getOrderItems().remove(this);
		item.addStock(count);
	}

	public int getTotalPrice() {
		return getOrderPrice() * count;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		if(this.order!=null) this.order.getOrderItems().remove(this);
		if(order!=null) order.getOrderItems().add(this);

		this.order = order;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
