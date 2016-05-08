package iCal.beans;

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
		if (! eventList.add(eventSample)) {
			System.out.println("[ERROR - iCalBean.addEvent] Nie dodano eventu");
		}
		clearEventSample();
	}
	
	private void clearEventSample() {
		eventSample = new Event();
	}
	
	public void copyEvent(Event event){
		eventList.add(new Event(event));
	}
	
	public void deleteEvent(Event event){
		eventList.remove(event);
	}
	
	public void clearEvents(){
		eventList.clear();
	}
	
	//Edit button methods
	public String saveEventEdit(Event event) {
		event.setEditable(false);
		return null;
	}
	
	public String editEventEdit(Event event) {
		event.setEditable(true);
		return null;
	}
	
}
