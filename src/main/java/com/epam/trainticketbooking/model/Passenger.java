package com.epam.trainticketbooking.model;

import javax.persistence.Embeddable;

@Embeddable
public class Passenger {
	private String name;
	private String gender;

	public Passenger() {

	}

	public Passenger(String name, String gender) {
		super();
		this.name = name;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Passenger [name=" + name + ", gender=" + gender + "]";
	}
}
