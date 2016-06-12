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

public class ParseFile {

	private List<Event> eventList;

	public ParseFile(List<Event> eventList) {
		this.eventList = eventList;
	}

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

	private boolean isEventAllDay(Date start, Date end) {
		String dtStart = start.toString();
		String dtEnd = end.toString();
		return dtStart.contains("00:00:00") && dtEnd.contains("00:00:00");
	}
}