package com.epam.trainticketbooking.helper;

import java.util.List;

import com.epam.trainticketbooking.model.Passenger;
import com.epam.trainticketbooking.model.Train;

public class BookingDetail {
	private List<Passenger> passengers;
	private String source;
	private String destination;
	private String date;
	private String seatType;
	private int seatCount;
	private Train train;
	private long trainId;
	private double fare;
	private String mobile;

	public BookingDetail() {

	}

	public BookingDetail(List<Passenger> passengers, String source, String destination, String date, String seatType,
			int seatCount, long trainId) {
		super();
		this.passengers = passengers;
		this.source = source;
		this.destination = destination;
		this.date = date;
		this.seatType = seatType;
		this.seatCount = seatCount;
		this.trainId = trainId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public long getTrainId() {
		return trainId;
	}

	public void setTrainId(long trainId) {
		this.trainId = trainId;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	@Override
	public String toString() {
		return "BookingDetail [passengers=" + passengers + ", source=" + source + ", destination=" + destination
				+ ", date=" + date + ", seatType=" + seatType + ", seatCount=" + seatCount + ", train=" + train
				+ ", trainId=" + trainId + ", fare=" + fare + ", mobile=" + mobile + "]";
	}	
}
