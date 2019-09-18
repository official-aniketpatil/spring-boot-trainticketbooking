package com.epam.trainticketbooking.utility;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class DateConversion {
	@Autowired
	private static Logger logger;

	
	private DateConversion() {
		
	}
	
	public static Date convertToSqlDate(String inputDate) {
		Date date = null;
		java.util.Date utilDate = null;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		try {
			utilDate = format.parse(inputDate);
			date = new Date(utilDate.getTime());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return date;	
	}
}
