package com.example.jpaprogrammingwebappexam.exceptions;

public class NotEnoughStockException extends IllegalStateException {
	public NotEnoughStockException(String message) {
		super(message);
	}
}
