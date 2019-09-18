package com.epam.trainticketbooking.services;

import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.trainticketbooking.dao.TicketDao;
import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.exceptions.SeatNotAvailableException;
import com.epam.trainticketbooking.helper.BookingDetail;
import com.epam.trainticketbooking.model.Availability;
import com.epam.trainticketbooking.model.Station;
import com.epam.trainticketbooking.model.Ticket;
import com.epam.trainticketbooking.model.Train;
import com.epam.trainticketbooking.utility.DateConversion;

@Component
public class BookingService {

	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private TrainDao trainDao;
	private static final Double AC_FARE = 4.5;
	private static final Double SLEEPER_FARE = 3.0;
	@Autowired
	private Logger logger;


	public BookingService(TicketDao ticketDao, TrainDao trainDao) {
		this.ticketDao = ticketDao;
		this.trainDao = trainDao;
	}
	public synchronized Ticket bookTicket(BookingDetail bookingDetail) {
		long trainId = bookingDetail.getTrainId();
		Date date = DateConversion.convertToSqlDate(bookingDetail.getDate());
		String seatType = bookingDetail.getSeatType();
		int seatCount = bookingDetail.getSeatCount();
		String source = bookingDetail.getSource();
		String destination = bookingDetail.getDestination();
		Availability availability = trainDao.getAvailability(trainId, date);
		if (isSeatAvailableToBook(availability, seatType, seatCount)) {
			Train train = trainDao.getById(trainId);
			long distance = computeDistanceBetweenStations(train, source, destination);
			double fare = computeFare(distance, seatType);
			bookingDetail.setFare(fare);
			bookingDetail.setTrain(train);
			Ticket ticket = ticketDao.book(bookingDetail);
			updateSeatCount(availability, seatType, seatCount);
			trainDao.updateAvailability(availability);
			logger.trace("seat booked");
			return ticket;
		} else {
			logger.trace("not able to book seat");
			throw new SeatNotAvailableException("seat not available to book");
		}
	}

	private boolean isSeatAvailableToBook(Availability availability, String seatType, int seatCount) {
		if (seatType.equalsIgnoreCase("AC")) {
			return availability.getAcSeats() >= seatCount;
		}
		return availability.getSleeperSeats() >= seatCount;
	}

	private void updateSeatCount(Availability availability, String seatType, int seatCount) {
		if (seatType.equalsIgnoreCase("AC")) {
			int newSeatCount = availability.getAcSeats() - seatCount;
			availability.setAcSeats(newSeatCount);
		} else if (seatType.equalsIgnoreCase("SLEEPER")) {
			int newSeatCount = availability.getSleeperSeats() - seatCount;
			availability.setSleeperSeats(newSeatCount);
		}
	}

	private double computeFare(long distance, String seatType) {
		if (seatType.equalsIgnoreCase("AC")) {
			return distance * AC_FARE;
		}
		return distance * SLEEPER_FARE;
	}

	private long computeDistanceBetweenStations(Train train, String source, String destination) {
		List<Station> stations = train.getStations();
		long distanceToSource = 0;
		long distanceToDestination = 0;

		for (Station station : stations) {
			if (station.getName().equalsIgnoreCase(source)) {
				distanceToSource = station.getDistance();
			} else if (station.getName().equalsIgnoreCase(destination)) {
				distanceToDestination = station.getDistance();
			}
		}
		return distanceToDestination - distanceToSource;
	}

	public Ticket getTicketById(long ticketId) {
		return this.ticketDao.getById(ticketId);
	}

	public List<Ticket> getTicketByTrain(long trainId) {
		return this.ticketDao.getByTrain(trainId);
	}
}
