package com.epam.trainticketbooking.helper;

import org.springframework.stereotype.Component;

import com.epam.trainticketbooking.model.Availability;
import com.epam.trainticketbooking.model.Train;

@Component
public class TrainWithAvailability {
	private Train train;
	private Availability availability;

	public TrainWithAvailability() {

	}

	public TrainWithAvailability(Train train, Availability availability) {
		super();
		this.train = train;
		this.availability = availability;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public Availability getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	@Override
	public String toString() {
		return "TrainWithAvailability [train=" + train + ", availability=" + availability + "]";
	}

}
