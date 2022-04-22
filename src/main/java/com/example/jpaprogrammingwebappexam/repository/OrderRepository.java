package com.example.jpaprogrammingwebappexam.repository;

import com.example.jpaprogrammingwebappexam.dto.OrderSearch;
import com.example.jpaprogrammingwebappexam.persistence.domain.Member;
import com.example.jpaprogrammingwebappexam.persistence.domain.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
	@PersistenceContext
	private EntityManager em;

	public void save(Order order) {
		em.persist(order);
	}

	public Order findOne(Long id) {
		return em.find(Order.class, id);
	}

	public List<Order> findAll(OrderSearch orderSearch) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> o = query.from(Order.class);

		List<Predicate> criteria = new ArrayList<>();
		if(orderSearch.getOrderStatus()!=null)
			criteria.add(builder.equal(o.get("status"), orderSearch.getOrderStatus()));
		if(orderSearch.getMemberName()!=null) {
			Join<Order, Member> m = o.join("member", JoinType.INNER);
			criteria.add(builder.like(m.get("name"), String.format("%%%s%%", orderSearch.getMemberName())));
		}
		query.where(builder.and(criteria.toArray(new Predicate[0])));

		return em.createQuery(query).getResultList();
	}
}
