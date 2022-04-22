package com.example.jpaprogrammingwebappexam;

import com.example.jpaprogrammingwebappexam.exceptions.NotEnoughStockException;
import com.example.jpaprogrammingwebappexam.persistence.domain.*;
import com.example.jpaprogrammingwebappexam.persistence.domain.enums.OrderStatus;
import com.example.jpaprogrammingwebappexam.repository.OrderRepository;
import com.example.jpaprogrammingwebappexam.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class OrderServiceTest extends TestTemplate {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderRepository orderRepository;

	private Member createMember() {
		Member member = new Member();
		member.setName("MEMBER1");
		member.setAddress(new Address("SEOUL", "RIVER", "123"));
		em.persist(member);
		return member;
	}

	private Book createBook(String name, int price, int stockQuantity) {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		em.persist(book);
		return book;
	}

	@Test @org.junit.jupiter.api.Order(1)
	public void orderItem() {
		__(() -> {
			Member member = createMember();
			Item item = createBook("COUNTRY JPA", 10000, 10);
			int orderCount = 2;

			Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
			Order getOrder = orderRepository.findOne(orderId);

			assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
			assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
			assertEquals("주문가격은 가격 * 수량이다", 10000 * 2, getOrder.getTotalPrice());
			assertEquals("주문수량만큼 재고가 줄어야 한다.", 8, item.getStockQuantity());
		});
	}

	@Test @org.junit.jupiter.api.Order(2)
	void orderOverStockTest() {
		__(() -> {
			// Given
			Member member = createMember();
			Item item = createBook("Country JPA", 10000, 10);
			int orderCount = 11;

			// When, Then
			assertThrows(NotEnoughStockException.class, () -> {
				orderService.order(member.getId(), item.getId(), orderCount);
			});
		});
	}

	@Test @org.junit.jupiter.api.Order(3)
	void cancelOrderTest() {
		__(() -> {
			// Given
			Member member = createMember();
			Item item = createBook("Country JPA", 10000, 10);
			int orderCount = 2;

			Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

			// When
			orderService.cancelOrder(orderId);

			// then
			Order order = orderRepository.findOne(orderId);


			assertEquals("주문 취소시 상태는 CANCEL이다", OrderStatus.CANCEL, order.getStatus());
			assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());
		});
	}
}
