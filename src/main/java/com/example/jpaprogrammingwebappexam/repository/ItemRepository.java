package com.example.jpaprogrammingwebappexam.repository;

import com.example.jpaprogrammingwebappexam.persistence.domain.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository {
	@PersistenceContext
	private EntityManager em;

	public void save(Item item) {
		if(item.getId() == null) {
			em.persist(item);
			return;
		}
		em.merge(item);
	}

	public Item findOne(Long id) {
		return em.find(Item.class, id);
	}

	public List<Item> findAll() {
		return em.createQuery("select i from Item i", Item.class).getResultList();
	}

}
