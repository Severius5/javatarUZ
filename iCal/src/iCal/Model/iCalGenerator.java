package iCal.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import iCal.data.Event;

/**
 * The iCalGenerator Class consist of methods allowing user to generate an ICal
 * file.
 * <p>
 * A user will be able to download his own ICal file whenever he presses a
 * specified button which is located on a website. Once it's pressed methods
 * using {@link StringBuilder} will start to build a String containing desired
 * data.
 * 
 * @author Sebastian Perek
 * @author Sebastian Pieni¹¿ek
 * @version
 * @see SimpleDateFormat
 * @see StringBuilder
 * @see Date
 * @see Calendar
 * @see TimeZone
 * @since ?
 */
public class iCalGenerator {

	/** The new format variable. */
	private final String NEW_FORMAT = "yyyyMMdd'T'HHmmss'Z'";

	/** The version variable. */
	private String version = "VERSION:2.0\r\n";

	/** The prodid variable. */
	private String prodid = "PRODID://JAVATAR UZ//\r\n";

	/** The calendar begin variable. */
	private String calBegin = "BEGIN:VCALENDAR\r\n";

	/** The calendar end variable. */
	private String calEnd = "END:VCALENDAR";

	/** The event begin variable. */
	private String eventBegin = "BEGIN:VEVENT\r\n";

	/** The event end variable. */
	private String eventEnd = "END:VEVENT\r\n";

	/** The time zone variable. */
	String timeZone = "BEGIN:VTIMEZONE\r\n" + "TZID:Europe/Berlin\r\n"
			+ "TZURL:http://tzurl.org/zoneinfo-outlook/Europe/Berlin\r\n" + "X-LIC- LOCATION:Europe/Berlin\r\n"
			+ "BEGIN:DAYLIGHT\r\n" + "TZOFFSETFROM:+0100\r\n" + "TZOFFSETTO:+0200\r\n" + "TZNAME:CEST\r\n"
			+ "DTSTART:19700329T020000\r\n" + "RRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=-1SU\r\n" + "END:DAYLIGHT\r\n"
			+ "BEGIN:STANDARD\r\n" + "TZOFFSETFROM:+0200\r\n" + "TZOFFSETTO:+0100\r\n" + "TZNAME:CET\r\n"
			+ "DTSTART:19701025T030000\r\n" + "RRULE:FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU\r\n" + "END:STANDARD\r\n"
			+ "END:VTIMEZONE\r\n";

	/** The iCal variable. */
	private String iCal;

	/** The builder object. */
	private StringBuilder builder = new StringBuilder();

	/**
	 * This method opens new calendar and adds first data to it, that is
	 * <code>version</code>, <code>prodid</code>, and <code>timeZone</code>
	 * using <code>append</code> method from {@link StringBuilder}.
	 * <p>
	 */
	public void openCalendar() {
		builder.append(calBegin);
		builder.append(version);
		builder.append(prodid);
		builder.append(timeZone);
	}

	/**
	 * This method builds a string from a given list.
	 * <p>
	 * The method checks what <code>isWholeDay</code> method returns. If it's
	 * true then date start and date end are reseted using
	 * <code>resetHours</code> method. If it's not then that step is skipped.
	 * The rest for both blocks is the same. String is being built by appending
	 * following Strings. At the end of an event's String en
	 * <code>eventEnd</code> variable is appended to finish it.
	 * 
	 * @param list
	 */
	// olewa true w ifie
	public void createEvents(List<Event> list) {
		for (Event e : list) {
			if (e.isWholeDay()) {
				// Date endDate = incrementDate(e.getDateEnd());
				builder.append(eventBegin);
				builder.append("DTSTART;VALUE=DATE:" + removeHours(e.getDateStart()) + "\r\n");
				builder.append("DTEND;VALUE=DATE:" + removeHours(e.getDateEnd()) + "\r\n");
				builder.append("SUMMARY:" + e.getEventTitle() + "\r\n");
				builder.append("LOCATION:" + e.getLocation() + "\r\n");
				builder.append("DESCRIPTION:" + e.getDescription() + "\r\n");
				builder.append(eventEnd);

			} else {
				builder.append(eventBegin);
				builder.append("DTSTART:" + formatDate(e.getDateStart()) + "\r\n");
				builder.append("DTEND:" + formatDate(e.getDateEnd()) + "\r\n");
				builder.append("SUMMARY:" + e.getEventTitle() + "\r\n");
				builder.append("LOCATION:" + e.getLocation() + "\r\n");
				builder.append("DESCRIPTION:" + e.getDescription() + "\r\n");
				builder.append(eventEnd);

			}
		}
	}

	/**
	 * This method ends calendar String by appending <code>calEnd</code> String
	 * to the end of it.
	 */
	public void closeCalendar() {
		builder.append(calEnd);
	}

	/**
	 * This method builds a calendar by calling methods defined above.
	 * <p>
	 * At first an <code>openCalendar</code> method is called, which allows to
	 * open calendar and adds basic information, for example
	 * <code>version</code>. Then a <code>createEvents</code> method is called.
	 * It appends various informations to a String, for example title or
	 * description. At the end a <code>closeCalendar</code> method is called and
	 * it finishes building the String.
	 * 
	 * @param list
	 */
	public void buildCalendar(List<Event> list) {
		openCalendar();
		createEvents(list);
		closeCalendar();
		iCal = builder.toString();
	}

	/**
	 * This method changes the date format to the format given as a String
	 * variable.
	 * <p>
	 *
	 * @param date
	 * @return the date
	 */
	private String formatDate(Date date) {
		String newDateString;
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(NEW_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		newDateString = sdf.format(date);
		return newDateString;
	}

	/**
	 * This method sets calendar's time and adds value 1 to calendar's date each
	 * time it's called.
	 * <p>
	 * 
	 * @param date
	 * @return the date
	 */

	/**
	 * This method resets date String by changing replacing it with a different
	 * one.
	 *
	 * @param date
	 * @return date the date
	 */
	private String removeHours(Date date) {
		String dateString = formatDate(date);
		int startIndex = dateString.indexOf("T");
		String replacement = "";
		String toBeReplaced = dateString.substring(startIndex, dateString.length());
		dateString = dateString.replace(toBeReplaced, replacement);
		return dateString;
	}

	/**
	 * This method allows return the <code>iCal</code> variable.
	 *
	 * @return the iCal
	 */
	public String getICal() {
		return iCal;
	}

	public StringBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(String string) {
		builder.append(string);
	}

}
