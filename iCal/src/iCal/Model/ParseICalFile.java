package iCal.Model;

import java.util.Date;
import java.util.List;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import iCal.data.Event;

public class ParseICalFile {
	
	private List<Event> eventList;
	
	public ParseICalFile(List<Event> eventList){
		this.eventList = eventList;
	}

	public void readICal(String in) {
		List<ICalendar> ical = Biweekly.parse(in).all();

		for (ICalendar object : ical) {
			List<VEvent> event = object.getEvents();
			for (VEvent prop : event) {
				
				String eventTittle = prop.getSummary().getValue();
				Boolean wholeDay = isEventAllDay(prop);
				Date dateStart = prop.getDateStart().getValue();
				Date dateEnd = prop.getDateEnd().getValue();
				String description = prop.getDescription().getValue();
				String location = prop.getLocation().getValue();
				System.out.println(dateStart);
				eventList.add(new Event(eventTittle, wholeDay, dateStart, dateEnd, description, location));
			}
		}
	}
	
	private boolean isEventAllDay(VEvent event){
		String dtStart = event.getDateStart().toString();
		String dtEnd = event.getDateEnd().toString();
        return dtStart.contains("00:00:00") && dtEnd.contains("00:00:00");
    }
}