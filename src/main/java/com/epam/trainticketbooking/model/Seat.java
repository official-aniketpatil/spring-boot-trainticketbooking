package com.epam.trainticketbooking.model;

public class Seat {
	private String type;
	private int seatCount;

	public Seat(String type, int seatCount) {
		super();
		this.type = type;
		this.seatCount = seatCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

}
