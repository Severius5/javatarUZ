package iCal.Model;

import java.util.Date;
import java.util.List;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.Description;
import biweekly.property.Location;
import biweekly.property.Summary;
import biweekly.util.Duration;
import iCal.data.Event;

/**
 * Parses a given data structures in order to add the new events to a list.
 * 
 * @author
 * @version
 * @see Date
 * @see List
 * @since
 */
public class ParseFile {

	/** The event list. */
	private List<Event> eventList;

	/**
	 * Instantiates a new parse file.
	 *
	 * @param eventList the event list
	 */
	public ParseFile(List<Event> eventList) {
		this.eventList = eventList;
	}

	/**
	 * Parses a specified ICal file to a text.
	 *<p>
	 * If parsing is successful an <code>eventList</code> method adds the new event filling it with just parsed data.
	 * 
	 * @param in the String
	 */
	public void readICal(String in) {
		List<ICalendar> ical = Biweekly.parse(in).all();
		if (!ical.isEmpty()) {
			for (ICalendar object : ical) {
				List<VEvent> event = object.getEvents();
				if (!event.isEmpty()) {
					for (VEvent prop : event) {

						String eventTittle = prop.getSummary().getValue();
						Date dateStart = prop.getDateStart().getValue();
						Date dateEnd = prop.getDateEnd().getValue();
						Boolean wholeDay = isEventAllDay(dateStart, dateEnd);
						String description = prop.getDescription().getValue();
						String location = prop.getLocation().getValue();
						eventList.add(new Event(eventTittle, wholeDay, dateStart, dateEnd, description, location));
					}
				} else
					System.err.println("No events in file");
			}
		} else
			System.err.println("No calendar in file");
	}

	/**
	 * Parses a specified ICal file to a text.
	 * <p>
	 * If parsing is successful an <code>eventList</code> method adds a new event filling it with just parsed data.
	 * 
	 * @param in the String
	 */
	public void readXCal(String in) {
		List<ICalendar> ical = Biweekly.parseXml(in).all();
		if (!ical.isEmpty()) {
			for (ICalendar object : ical) {
				List<VEvent> event = object.getEvents();
				if (!event.isEmpty()) {
					for (VEvent prop : event) {

						Summary summary = prop.getSummary();
						String eventTittle = (summary == null) ? "Title" : summary.getValue();
						Description desc = prop.getDescription();
						String description = (desc == null) ? null : desc.getValue();
						Location loc = prop.getLocation();
						String location = (loc == null) ? null : loc.getValue();

						Date dateStart = prop.getDateStart().getValue();
						Date dateEnd = null;
						if (prop.getDuration() != null) {
							Duration duration = Duration.parse(prop.getDuration().getValue().toString());
							dateEnd = duration.add(dateStart);
						} else if (prop.getDateEnd() != null) {
							dateEnd = prop.getDateEnd().getValue();
						} else {
							dateEnd = dateStart;
						}

						Boolean wholeDay = isEventAllDay(dateStart, dateEnd);

						eventList.add(new Event(eventTittle, wholeDay, dateStart, dateEnd, description, location));
					}
				} else
					System.err.println("No events in file");
			}
		} else
			System.err.println("No calendar in file");
	}

	/**
	 * Checks if dates of the start and the end are the same as specified ones.
	 *
	 * @param start the start
	 * @param end the end
	 * @return true, if variables contains a specified values
	 */
	private boolean isEventAllDay(Date start, Date end) {
		String dtStart = start.toString();
		String dtEnd = end.toString();
		return dtStart.contains("00:00:00") && dtEnd.contains("00:00:00");
	}
}