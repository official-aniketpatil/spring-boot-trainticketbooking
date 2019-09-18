package com.epam.trainticketbooking.services;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.epam.trainticketbooking.exceptions.UnableToBookTicketException;
import com.epam.trainticketbooking.helper.BookingDetail;
import com.epam.trainticketbooking.helper.TrainWithAvailability;
import com.epam.trainticketbooking.model.Ticket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConsumerService {
	public static final String FIND_TRAINS = "http://localhost:8080/spring-rest-trainticketbooking/rest/trains";
	public static final String BOOK_TRAIN = "http://localhost:8080/spring-rest-trainticketbooking/rest/book";
	@Autowired
	private Logger logger;


	public List<TrainWithAvailability> findTrains(String source, String destination, String date) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(FIND_TRAINS).queryParam("source", source)
				.queryParam("destination", destination).queryParam("date", date);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<TrainWithAvailability>> response = restTemplate.exchange(builder.toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<List<TrainWithAvailability>>() {
				});
		return response.getBody();
	}

	public Ticket bookTicket(BookingDetail bookingDetail) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", MediaType.APPLICATION_JSON_VALUE);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String bookingDetailJson = mapper.writeValueAsString(bookingDetail);
			HttpEntity<?> entity = new HttpEntity<>(bookingDetailJson, headers);
			return restTemplate.postForObject(BOOK_TRAIN, entity, Ticket.class);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			throw new UnableToBookTicketException("json parse error for bookingDetails");
		}
		
	}
}
