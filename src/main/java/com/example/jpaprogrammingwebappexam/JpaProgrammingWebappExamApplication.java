package com.example.jpaprogrammingwebappexam;

import com.example.jpaprogrammingwebappexam.persistence.domain.Album;
import com.example.jpaprogrammingwebappexam.persistence.domain.Book;
import com.example.jpaprogrammingwebappexam.persistence.domain.Item;
import com.example.jpaprogrammingwebappexam.persistence.domain.Movie;
import com.example.jpaprogrammingwebappexam.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class JpaProgrammingWebappExamApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaProgrammingWebappExamApplication.class, args);
	}

}
