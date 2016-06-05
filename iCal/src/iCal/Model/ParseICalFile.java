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

public class ParseICalFile {

	private List<Event> eventList;

	public ParseICalFile(List<Event> eventList) {
		this.eventList = eventList;
	}

	public void readICal(String in) {
		List<ICalendar> ical = Biweekly.parse(in).all();

		for (ICalendar object : ical) {
			List<VEvent> event = object.getEvents();
			for (VEvent prop : event) {

				String eventTittle = prop.getSummary().getValue();
				Date dateStart = prop.getDateStart().getValue();
				Date dateEnd = prop.getDateEnd().getValue();
				Boolean wholeDay = isEventAllDay(dateStart, dateEnd);
				String description = prop.getDescription().getValue();
				String location = prop.getLocation().getValue();
				eventList.add(new Event(eventTittle, wholeDay, dateStart, dateEnd, description, location));
			}
		}
	}
	
	public void readXCal(String in){
		List<ICalendar> ical = Biweekly.parseXml(in).all();

		for (ICalendar object : ical) {
			List<VEvent> event = object.getEvents();
			for (VEvent prop : event) {

				Summary summary = prop.getSummary();
	            String eventTittle = (summary == null) ? "Title" : summary.getValue();
	            Description desc = prop.getDescription();
				String description = (desc == null ) ? "Description" : desc.getValue();
				Location loc = prop.getLocation();
				String location = (loc == null) ? "Location" : loc.getValue();
	            
				Duration duration = Duration.parse(prop.getDuration().getValue().toString());
				Date dateStart = prop.getDateStart().getValue();
				Date dateEnd = duration.add(dateStart);
				Boolean wholeDay = isEventAllDay(dateStart, dateEnd);
				
				eventList.add(new Event(eventTittle, wholeDay, dateStart, dateEnd, description, location));
			}
		}
	}
	
	private boolean isEventAllDay(Date start, Date end) {
		String dtStart = start.toString();
		String dtEnd = end.toString();
		return dtStart.contains("00:00:00") && dtEnd.contains("00:00:00");
	}
}