package com.epam.trainticketbooking.helper;

import java.beans.PropertyEditorSupport;
import java.sql.Date;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.trainticketbooking.utility.DateConversion;

public class DateEditor extends PropertyEditorSupport {
	@Autowired
	private Logger logger;

	@Override
	public void setAsText(String text) {
		try {
			Date date = DateConversion.convertToSqlDate(text);
			setValue(date);
		} catch (IllegalArgumentException ex) {
			logger.error(ex.getMessage());
		}
	}
}
