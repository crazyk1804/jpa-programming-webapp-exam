package com.example.jpaprogrammingwebappexam.service;

import com.example.jpaprogrammingwebappexam.dto.OrderSearch;
import com.example.jpaprogrammingwebappexam.persistence.domain.*;
import com.example.jpaprogrammingwebappexam.repository.MemberRepository;
import com.example.jpaprogrammingwebappexam.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
	@Autowired
	private OrderRepository repository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ItemService itemService;

	public Long order(Long memberId, Long itemId, int count) {
		Member member = memberRepository.findOne(memberId);
		Item item = itemService.findOne(itemId);

		Delivery delivery = new Delivery(member.getAddress());
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
		Order order = Order.createOrder(member, delivery, orderItem);
		repository.save(order);
		return order.getId();
	}

	public void cancelOrder(Long orderId) {
		Order order = repository.findOne(orderId);
		order.cancel();
	}

	public List<Order> findOrders(OrderSearch orderSearch) {
		return repository.findAll(orderSearch);
	}
}
