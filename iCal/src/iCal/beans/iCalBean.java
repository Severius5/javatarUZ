package iCal.beans;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import iCal.Model.iCalGenerator;
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

	public void addEvent() {
		if (!eventList.add(eventSample)) {
			System.out.println("[ERROR - iCalBean.addEvent] Nie dodano eventu");
		}
		clearEventSample();
	}

	private void clearEventSample() {
		eventSample = new Event();
	}

	public void copyEvent(Event event) {
		eventList.add(new Event(event));
	}

	public void deleteEvent(Event event) {
		eventList.remove(event);
	}

	public void clearEvents() {
		eventList.clear();
	}

	// Edit button methods
	public String saveEventEdit(Event event) {
		event.setEditable(false);
		return null;
	}

	public String editEventEdit(Event event) {
		event.setEditable(true);
		return null;

	}

	public void generateICal() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;filename=iCal.ics");

		ServletOutputStream out;
		try {
			out = response.getOutputStream();

			iCalGenerator generator = new iCalGenerator();
			generator.buildCalendar(eventList);

			out.println(generator.getICal());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
