package com.epam.trainticketbooking.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.epam.trainticketbooking.dao.TicketDao;
import com.epam.trainticketbooking.exceptions.DataAccessException;
import com.epam.trainticketbooking.exceptions.DataWriteException;
import com.epam.trainticketbooking.helper.BookingDetail;
import com.epam.trainticketbooking.model.Passenger;
import com.epam.trainticketbooking.model.Ticket;

@Component
public class TicketDaoImpl implements TicketDao {

	private static final String GET_TICKET_BY_TRAIN_ID = "select ticket from Ticket ticket "
			+ "where trainId = :trainId";
	@Autowired
	private Logger logger;

	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Override
	public synchronized Ticket book(BookingDetail bookingDetail) {

		String source = bookingDetail.getSource();
		String destination = bookingDetail.getDestination();
		String seatType = bookingDetail.getSeatType();
		String mobile = bookingDetail.getMobile();
		List<Passenger> passengers = bookingDetail.getPassengers();
		long trainId = bookingDetail.getTrainId();
		double fare = bookingDetail.getFare();

		Ticket ticket = context.getBean(Ticket.class);
		ticket.setTrainId(trainId);
		ticket.setType(seatType);
		ticket.setSource(source);
		ticket.setDestination(destination);
		ticket.setPassengers(passengers);
		ticket.setFare(fare);
		ticket.setMobile(mobile);
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.persist(ticket);
			entityTransaction.commit();
			return ticket;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new DataWriteException("Unable to book ticket");
		} finally {
			entityManager.close();
		}
	}

	@Override
	public Ticket getById(long ticketId) {
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityTransaction = entityManager.getTransaction();

			entityTransaction.begin();
			Ticket ticket = entityManager.find(Ticket.class, ticketId);
			entityTransaction.commit();
			return ticket;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new DataAccessException("Problem Occured while fetching Ticket");
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}

	@Override
	public List<Ticket> getByTrain(long trainId) {
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityTransaction = entityManager.getTransaction();

			entityTransaction.begin();
			Query query = entityManager.createQuery(GET_TICKET_BY_TRAIN_ID);
			query.setParameter("trainId", trainId);
			@SuppressWarnings("unchecked")
			List<Ticket> tickets = query.getResultList();
			entityTransaction.commit();
			return tickets;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new DataAccessException("Problem Occured while fetching Ticket");
		} finally {
			entityManager.close();
		}
	}

	@Override
	public boolean cancel() {
		return false;
	}

}
