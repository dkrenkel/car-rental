package br.com.carrental.service.dto.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Class that Deserialize a Calendar that is given when the User is created, it converts and prevents invalid dates.
 *
 * @author David
 */
@Component
public class CalendarDeserializer extends JsonDeserializer<Calendar> {
	private final Logger LOGGER = LoggerFactory.getLogger(CalendarDeserializer.class);

	/**
	 * Method that Deserialize the Calendar
	 *
	 * @param p
	 * @param ctxt
	 * @return Calendar - A calendar with the valid date and converted.
	 * @throws IOException             - An I/O Excepiton of some sort has occured.
	 * @throws JsonProcessingException - If there is an error with the JSON.
	 */
	@Override
	public Calendar deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
		LOGGER.info("m=deserialize: Trying to deserialize the date from JSON.");

		final TimeZone timeZone = TimeZone.getTimeZone("UTC-3");
		final Calendar calendar = Calendar.getInstance(timeZone);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);

		try {
			calendar.setTime(df.parse(p.getText()));
		} catch (ParseException e) {
			LOGGER.warn("m=deserialize: Error while deserializing date, date is invalid");
			throw new IOException("Date is invalid");
		}

		return calendar;
	}
}
