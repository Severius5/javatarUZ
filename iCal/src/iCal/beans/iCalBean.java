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
import iCal.Model.LoadICalFile;
import iCal.data.Event;


/**
 * The ICalBean class consists of methods allowing user work on events, generate an ICal file and read from it.
 * <p>
 *
 * @author ?
 * @version ?
 * @see List
 * @see Event
 * @since ?
 */
@ManagedBean
@SessionScoped
public class iCalBean {
	
	// Data
	private List<Event> eventList = new LinkedList<>();
	
	private Event eventSample = new Event();
	
	private LoadICalFile loader = new LoadICalFile(eventList);

	// Settery Gettery Konstruktor
	/**
	 * This constructor is used to add newly created Event object at the end of <code>eventList</code>.
	 * 
	 */
	public iCalBean() {
		eventList.add(new Event());
	}
	
	/**
	 * Gets the event list.
	 *
	 * @return eventList
	 */
	public List<Event> getEventList() {
		return eventList;
	}
	
	/**
	 * Sets the event list.
	 *
	 * @param eventList
	 */
	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}
	
	/**
	 * Gets the event sample.
	 *
	 * @return eventSample
	 */
	public Event getEventSample() {
		return eventSample;
	}
	
	/**
	 * Sets the event sample.
	 *
	 * @param eventSample
	 */
	public void setEventSample(Event eventSample) {
		this.eventSample = eventSample;
	}

	/**
	 * Adds the event.
	 */
	public void addEvent() {
		if (!eventList.add(eventSample)) {
			System.out.println("[ERROR - iCalBean.addEvent] Nie dodano eventu");
		}
		clearEventSample();
	}
	
	/**
	 * This method allows to clear the event sample.
	 */
	private void clearEventSample() {
		eventSample = new Event();
	}

	/**
	 * This method allows to copy a specified event.
	 *
	 * @param event the event
	 */
	public void copyEvent(Event event) {
		eventList.add(new Event(event));
	}

	/**
	 * This method allows to delete a specified event.
	 *
	 * @param event
	 */
	public void deleteEvent(Event event) {
		eventList.remove(event);
	}

	/**
	 * This method allows to clear events.
	 */
	public void clearEvents() {
		eventList.clear();
	}

	/**
	 * This method allows to set the <code>setEditable</code> method to true in order to save an <code>event</code>.
	 *
	 * @param event
	 * @return the string
	 */
	public String saveEventEdit(Event event) {
		event.setEditable(false);
		return null;
	}

	/**
	 * This method allows to set the <code>setEditable</code> method to true in order to edit an <code>event</code>.
	 *
	 * @param event
	 * @return the string
	 */
	public String editEventEdit(Event event) {
		event.setEditable(true);
		return null;

	}

	/**
	 * This method allows to download <code>eventList</code> from a server and save it as an ICal file.
	 * <p>
	 * The <code>context</code> object is set to the current instance of {@link FacesContext} and return
	 *  the {@link ExternalContext} instance for this FacesContext instance..
	 * It tries to get response from a server, then by using a <code>setContentType</code> method it downloads
	 * specified content and sets a header using a <code>setHeader</code> method.
	 * <p>
	 * The binary data retrieved from a server is set to an <code>out</code> object and further the ICal file
	 * is built.
	 *
	 * @throws IOException if fails to receive response from an external server
	 * @see HttpServletResponse
	 */
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
	
	/**
	 * This method allows to read a content of an ICal file.
	 */
	public void readFromICalFile(){
		loader.readICal();
	}
}
