package com.example.jpaprogrammingwebappexam.web;

import com.example.jpaprogrammingwebappexam.persistence.domain.Book;
import com.example.jpaprogrammingwebappexam.persistence.domain.Item;
import com.example.jpaprogrammingwebappexam.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "/items", method = RequestMethod.GET)
	public String list(Model model) {
		List<Item> items = itemService.findItems();
		model.addAttribute("items", items);
		return "items/item-list";
	}

	@RequestMapping(value = "/items/new", method = RequestMethod.GET)
	public String createForm() {
		return "items/create-item-form";
	}

	@RequestMapping(value = "/items/new", method = RequestMethod.POST)
	public String create(Book item) {
		itemService.saveItem(item);
		return "redirect:/items";
	}

	@RequestMapping(value = "/items/{itemId}/edit", method = RequestMethod.GET)
	public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
		Item item = itemService.findOne(itemId);
		model.addAttribute("item", item);
		return "items/update-item-form";
	}

	@RequestMapping(value = "/items/{itemId}/edit", method = RequestMethod.POST)
	public String updateItem(@ModelAttribute("item")Book item) {
		itemService.saveItem(item);
		return "redirect:/items";
	}
}
