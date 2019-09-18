package com.epam.trainticketbooking.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double fare;
	private String source;
	private String destination;
	private long trainId;
	private String type;
	private String mobile;

	@ElementCollection(fetch = FetchType.LAZY)
	private List<Passenger> passengers;

	public Ticket() {

	}

	public Ticket(String type, String source, String destination, List<Passenger> passengers, long trainId, double fare,
			String mobile) {
		super();
		this.type = type;
		this.trainId = trainId;
		this.fare = fare;
		this.passengers = passengers;
		this.source = source;
		this.destination = destination;
		this.mobile = mobile;
	}

	public String getType() {
		return type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public long getTrainId() {
		return trainId;
	}

	public void setTrainId(long trainId) {
		this.trainId = trainId;
	}

	public long getId() {
		return id;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", fare=" + fare + ", source=" + source + ", destination=" + destination
				+ ", trainId=" + trainId + ", type=" + type + ", mobile=" + mobile + ", passengers=" + passengers + "]";
	}
}
