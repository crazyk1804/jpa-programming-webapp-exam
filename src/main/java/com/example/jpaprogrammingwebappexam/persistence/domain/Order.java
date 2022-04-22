package com.example.jpaprogrammingwebappexam.persistence.domain;

import com.example.jpaprogrammingwebappexam.persistence.domain.enums.DeliveryStatus;
import com.example.jpaprogrammingwebappexam.persistence.domain.enums.OrderStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order {
	@Id @GeneratedValue
	@Column(name = "ORDER_ID")
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "DELIVERY_ID")
	private Delivery delivery;
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	public static Order createOrder(Member member, Delivery delivery, OrderItem...orderItems) {
		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		Arrays.asList(orderItems).forEach(order::addOrderItem);
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(new Date());
		return order;
	}

	public void cancel() {
		if(delivery.getStatus() == DeliveryStatus.COMP) {
			throw new RuntimeException("배송 완료된 상품은 취소가 불가능 합니다.");
		}

		this.setStatus(OrderStatus.CANCEL);
		orderItems.forEach(OrderItem::cancel);
	}

	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderItem orderItem : orderItems)
			totalPrice += orderItem.getTotalPrice();

		return totalPrice;
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.remove(orderItem);
		orderItems.add(orderItem);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		if(this.member!=null) this.member.getOrders().remove(this);
		if(member!=null) member.getOrders().add(this);
		this.member = member;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}
