package iCal.beans;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import iCal.data.Event;

@ManagedBean
@SessionScoped
public class iCalBean {
	// Data
	private List<Event> eventList = new LinkedList<>();
	private Event eventSample = new Event();
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		System.out.println(date);
		this.date = date;
	}

	// Settery Gettery Konstruktor
	public iCalBean() {
		eventList.add(new Event());
	}

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	public Event getEventSample() {
		return eventSample;
	}

	public void setEventSample(Event eventSample) {
		this.eventSample = eventSample;
	}

	// Akcje

	public void addEvent() {
		if (eventList.add(eventSample)) {
			clearEventSample();
		} else {
			System.out.println("[ERROR] Nie dodano eventu");
			clearEventSample();
		}
	}

	private void clearEventSample() {
		eventSample = new Event();
	}
	
	public void copyEvent(Event event){
		eventList.add(new Event(event));
		System.out.println("czesc");
		
	}
}
