package com.epam.trainticketbooking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason="seat not available for booking")
public class SeatNotAvailableException extends RuntimeException{
	
	private static final long serialVersionUID = 6643086150242934286L;

	public SeatNotAvailableException(String message) {
		super(message);
	}
}
