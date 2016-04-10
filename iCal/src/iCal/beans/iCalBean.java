package iCal.beans;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import iCal.data.Event;

@ManagedBean
@SessionScoped
public class iCalBean {
	//Data
	private List<Event> eventList = new LinkedList<>();
	private Event eventSample = new Event();

	//Settery Gettery Konstruktor
	public iCalBean(){
		
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
		
	//Akcje
	
}
