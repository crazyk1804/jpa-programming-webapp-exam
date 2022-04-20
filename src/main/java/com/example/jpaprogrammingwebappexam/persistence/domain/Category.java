package com.example.jpaprogrammingwebappexam.persistence.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
	@Id @GeneratedValue
	@Column(name = "CATEGORY_ID")
	private Long id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private Category parent;
	@OneToMany(mappedBy = "parent")
	private List<Category> children = new ArrayList<>();
	@ManyToMany
	@JoinTable(
		name = "CATEGORY_ITEM",
		joinColumns = @JoinColumn(name = "CATEGORY_ID"),
		inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
	)
	private List<Item> items = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		if(this.parent != null) this.parent.children.remove(this);
		if(parent != null) parent.getChildren().add(this);

		this.parent = parent;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
