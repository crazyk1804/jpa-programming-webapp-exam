package com.example.jpaprogrammingwebappexam;

import com.example.jpaprogrammingwebappexam.persistence.domain.Album;
import com.example.jpaprogrammingwebappexam.persistence.domain.Book;
import com.example.jpaprogrammingwebappexam.persistence.domain.Item;
import com.example.jpaprogrammingwebappexam.persistence.domain.Movie;
import com.example.jpaprogrammingwebappexam.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
	@Autowired
	private ItemService itemService;


	private void setItemBaseInfo(Item item, int idx) {
		item.setName(String.format("%s %d", item.getClass().getSimpleName(), idx));
		item.setPrice(1000 * idx);
		item.setStockQuantity((int) (Math.random() * 10 * idx));
	}

	@EventListener
	public void setItems(ApplicationStartedEvent event) {
		for (int i = 0; i < 30; i++) {
			Item item;

			if (i < 10) {
				Album album = new Album();
				setItemBaseInfo(album, i);
				album.setArtist(String.format("Artist %d", i));
				album.setEtc(String.format("ETC %d", i));
				item = album;
			} else if (i < 20) {
				Book book = new Book();
				setItemBaseInfo(book, i);
				book.setAuthor(String.format("Author %d", i));
				book.setIsbn(String.format("ISBN %d", i));
				item = book;
			} else {
				Movie movie = new Movie();
				setItemBaseInfo(movie, i);
				movie.setDirector(String.format("Director %d", i));
				movie.setActor(String.format("Actor %d", i));
				item = movie;
			}

			itemService.saveItem(item);
		}
	}
}
