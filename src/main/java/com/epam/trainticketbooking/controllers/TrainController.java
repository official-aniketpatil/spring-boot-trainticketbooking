package com.epam.trainticketbooking.controllers;

import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.trainticketbooking.helper.BookingDetail;
import com.epam.trainticketbooking.helper.DateEditor;
import com.epam.trainticketbooking.helper.TrainWithAvailability;
import com.epam.trainticketbooking.model.Ticket;
import com.epam.trainticketbooking.services.ConsumerService;

@Controller
public class TrainController {

	@Autowired
	private Logger logger;

	@Autowired
	private ConsumerService consumerService;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, "date", new DateEditor());
	}

	@GetMapping("/")
	public String showHome() {
		return "index";
	}

	@GetMapping("/trains")
	public ModelAndView getAvailableTrains(@RequestParam String source, @RequestParam String destination,
			@RequestParam String date) {
		List<TrainWithAvailability> trainsWithAvailability = consumerService.findTrains(source, destination, date);
		ModelAndView view = new ModelAndView();
		view.setViewName("show-trains");
		view.addObject("trainsWithAvailability", trainsWithAvailability);
		return view;
	}

	@PostMapping("/book")
	public ModelAndView bookTicket(@ModelAttribute BookingDetail bookingDetail) {
		ModelAndView view = new ModelAndView();
		ConsumerService service = new ConsumerService();
		Ticket ticket = service.bookTicket(bookingDetail);
		view.setViewName("success");
		view.addObject("ticket", ticket.toString());
		logger.trace(bookingDetail.toString());
		return view;
	}

	@PostMapping("/add-passengers")
	public String addPassenger() {
		return "add-passengers";
	}
}
