package iCal.beans;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import iCal.data.Event;

@ManagedBean
@SessionScoped
public class iCalBean {
	// Data
	private List<Event> eventList = new LinkedList<>();
	private Event eventSample = new Event();

	// Settery Gettery Konstruktor
	public iCalBean() {

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
	public String doAction() throws IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;filename=downloadfilename.txt");
		ServletOutputStream out = response.getOutputStream();
		out.println(request.getRequestedSessionId());
		out.println(eventSample.getEventTitle());
		out.println(eventSample.getDescription());
		out.println(eventSample.getLocation());
		out.close();
		return "#";
	}
}
