package iCal.Model;

import java.util.LinkedList;
import java.util.List;

import iCal.data.Event;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

public class iCalGenerator {
	private Calendar calendar = new Calendar();
	LinkedList<VEvent> events = new LinkedList<>();
	
	private void setProperties() { 
		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//Co tu sie dzieje//iCal4j 1.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
	}
	private void createEvents(List<Event> list) { 
		for(Event e: list) {
		
			net.fortuna.ical4j.model.Date dateStart = new net.fortuna.ical4j.model.Date(e.getDateStart());
			net.fortuna.ical4j.model.Date dateEnd = new net.fortuna.ical4j.model.Date(e.getDateEnd());
			
			VEvent event = new VEvent(dateStart,dateEnd,e.getEventTitle());
			Description description = event.getDescription();
			description.setValue(e.getDescription());
			events.add(event);
			Location location = event.getLocation();
			location.setValue(e.getLocation());
		}
	}
	
	private void addEventsToCal() {
		for (VEvent event : events) {
			calendar.getComponents().add(event);
		}
	}
	// Add events, etc..
}
