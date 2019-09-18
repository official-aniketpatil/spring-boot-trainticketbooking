package com.epam.trainticketbooking.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.trainticketbooking.exceptions.SeatNotAvailableException;
import com.epam.trainticketbooking.helper.BookingDetail;
import com.epam.trainticketbooking.helper.DateEditor;
import com.epam.trainticketbooking.helper.TrainWithAvailability;
import com.epam.trainticketbooking.model.Availability;
import com.epam.trainticketbooking.model.Ticket;
import com.epam.trainticketbooking.model.Train;
import com.epam.trainticketbooking.services.BookingService;
import com.epam.trainticketbooking.services.TrainService;

@RestController
@RequestMapping("/rest")
public class TrainRestController {

	@Autowired
	private Logger logger;

	@Autowired
	private TrainService trainService;
	@Autowired
	private BookingService bookingService;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, "date", new DateEditor());
	}

	@GetMapping(value = "/trains", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<TrainWithAvailability>> getAvailableTrains(@RequestParam String source,
			@RequestParam String destination, @RequestParam("date") String date) {
		List<Train> trains = trainService.findTrains(source, destination, date);
		List<TrainWithAvailability> trainsWithAvailability = new ArrayList<>();
		for (Train train : trains) {
			Availability availability = trainService.getAvailability(train.getId(), date);
			trainsWithAvailability.add(new TrainWithAvailability(train, availability));
		}
		return new ResponseEntity<>(trainsWithAvailability, HttpStatus.OK);
	}

	@PostMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Ticket> book(@RequestBody BookingDetail bookingDetail) {
		Ticket ticket = bookingService.bookTicket(bookingDetail);
		logger.trace(ticket);
		return new ResponseEntity<>(ticket, HttpStatus.CREATED);
	}

	@ExceptionHandler({ SeatNotAvailableException.class })
	public void handleError() {
		throw new SeatNotAvailableException("seat not available for booking");
	}
}
