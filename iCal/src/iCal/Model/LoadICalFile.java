package iCal.Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import iCal.data.Event;

public class LoadICalFile {
	
	private List<Event> eventList;
	
	public LoadICalFile(List<Event> eventList){
		this.eventList = eventList;
	}

	public void readICal() {
		try {
			FileInputStream in = new FileInputStream("C:/test.ics");
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
					eventList.add(new Event(eventTittle, wholeDay, dateStart, dateEnd, description, location));
				}
			}

		} catch (FileNotFoundException e) {
			System.err.println("[LoadICalFile] File not foud");
		} catch (IOException e) {
			System.err.println("Exception in class: LoadICalFile");
		}
	}
	
	private boolean isEventAllDay(VEvent event){
		//System.out.println(event.getDateStart().getParameter("VALUE"));
        return event.getDateStart().toString().indexOf("VALUE=DATE") != -1;
    }
}