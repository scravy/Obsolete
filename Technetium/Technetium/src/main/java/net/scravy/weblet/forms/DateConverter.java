package net.scravy.weblet.forms;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.scravy.technetium.util.value.IllegalDate;
import net.scravy.weblet.Weblet;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * A converter for dates and datetimes.
 * 
 * Accepts the following formats:
 * 
 * <pre>
 * yyyy-MM-dd HH:mm:ss
 * MM/dd/yyyy HH:mm:ss
 * dd.MM.yyyy HH:mm:ss
 * yyyy-MM-dd HH:mm
 * MM/dd/yyyy HH:mm
 * dd.MM.yyyy HH:mm
 * yyyy-MM-dd
 * MM/dd/yyyy
 * dd.MM.yyyy
 * </pre>
 * 
 * @see SimpleDateFormat for the format codes.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class DateConverter implements Converter<Date> {

	private static final DateTimeFormatter[] formatters = {
			DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"),
			DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss"),
			DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss"),
			DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"),
			DateTimeFormat.forPattern("MM/dd/yyyy HH:mm"),
			DateTimeFormat.forPattern("dd.MM.yyyy HH:mm"),
			DateTimeFormat.forPattern("yyyy-MM-dd"),
			DateTimeFormat.forPattern("MM/dd/yyyy"),
			DateTimeFormat.forPattern("dd.MM.yyyy")
	};

	@Override
	public Date convert(Weblet weblet, Form form, String from) {

		if (from == null || from.isEmpty()) {
			return null;
		}

		DateTimeZone timeZone = DateTimeZone.forTimeZone(form.getTimeZone());

		for (DateTimeFormatter formatter : formatters) {
			try {
				return formatter
						.withZone(timeZone)
						.parseDateTime(from)
						.toDate();
			} catch (Exception exc) {
			}
		}
		return new IllegalDate(from);
	}
}