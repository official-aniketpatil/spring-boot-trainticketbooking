package com.epam.trainticketbooking.exceptions;

public class UnableToBookTicketException extends RuntimeException {
	
	private static final long serialVersionUID = 2193175334731597563L;

	public UnableToBookTicketException(String message) {
		super(message);
	}
}
