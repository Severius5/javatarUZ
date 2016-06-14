package iCal.beans;

import java.io.IOException;
import java.io.PrintWriter;
//import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import iCal.Model.LoadFile;
import iCal.Model.iCalGenerator;
import iCal.data.Event;

/**
 * The ICalBean class consists of methods allowing user work on events, generate
 * an ICal file and read from it.
 * <p>
 *
 * @author ?
 * @version ?
 * @see List
 * @see Collections
 * @see Comparator
 * @since ?
 */
@ManagedBean
@SessionScoped
public class iCalBean {

	// Data
	private List<Event> eventList = new LinkedList<>();

	private Event eventSample = new Event();
	private LoadFile loadFile = new LoadFile(eventList);

	private boolean sortTitleAsc = true;
	private boolean sortDateStartAsc = true;
	private boolean sortDateEndAsc = true;

	// Settery Gettery Konstruktor
	/**
	 * This constructor is used to add newly created Event object at the end of
	 * <code>eventList</code>.
	 * 
	 */
	public iCalBean() {
		eventList.add(new Event());
	}

	/**
	 * This method allows to get the event list.
	 *
	 * @return eventList
	 */
	public List<Event> getEventList() {
		return eventList;
	}

	/**
	 * This method allows to set the event list.
	 *
	 * @param eventList
	 */
	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	/**
	 * This method allows to get the event sample.
	 *
	 * @return eventSample
	 */
	public Event getEventSample() {
		return eventSample;
	}

	/**
	 * This method allows to return loaded file.
	 * 
	 * @return loadFile
	 */
	public LoadFile getLoadFile() {
		return loadFile;
	}

	/**
	 * This method allows to set file to be loaded.
	 * 
	 * @param loadFile
	 */
	public void setLoadFile(LoadFile loadFile) {
		this.loadFile = loadFile;
	}

	/**
	 * This method allows to set the event sample.
	 *
	 * @param eventSample
	 */
	public void setEventSample(Event eventSample) {
		this.eventSample = eventSample;
	}

	/**
	 * This method allows to add the event.
	 * <p>
	 * Method checks ff an event was added. If it was not an appropriate message
	 * would be shown. Then the method <code>clearEventSample</code> is called
	 * in order to empty the sample. At the end events are sorted by
	 * <code>sortingDateStartByAsc</code> method.
	 * 
	 */
	public void addEvent() {
		if (!eventList.add(eventSample)) {
			System.err.println("[iCalBean.addEvent] Nie dodano eventu");
		}
		clearEventSample();
		sortingDateStartByAsc();
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
	 * @param event
	 *            the event
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
	 * This method allows to set the <code>setEditable</code> method to true in
	 * order to save an <code>event</code>.
	 *
	 * @param event
	 */
	public void saveEventEdit(Event event) {
		event.setEditable(false);
	}

	/**
	 * This method allows to set the <code>setEditable</code> method to true in
	 * order to edit an <code>event</code>.
	 *
	 * @param event
	 */
	public void editEventEdit(Event event) {
		event.setEditable(true);

	}

	/**
	 * This method allows to download <code>eventList</code> from a server and
	 * save it as an ICal file.
	 * <p>
	 * The <code>context</code> object is set to the current instance of
	 * {@link FacesContext} and return the {@link ExternalContext} instance for
	 * this FacesContext instance.. It tries to get response from a server, then
	 * by using a <code>setContentType</code> method it downloads specified
	 * content and sets a header using a <code>setHeader</code> method.
	 * <p>
	 * The binary data retrieved from a server is set to an <code>out</code>
	 * object and further the ICal file is built.
	 *
	 * @throws IOException
	 *             if fails to receive response from an external server
	 * @see HttpServletResponse
	 */
	public void generateICal() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.responseReset();
		context.setResponseContentType("application/force-download");
		context.setResponseHeader("Content-Disposition", "attachment;filename=iCal.ics");
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		try (PrintWriter pr = response.getWriter()) {
			// ServletOutputStream out = response.getOutputStream();
			iCalGenerator generator = new iCalGenerator();
			generator.buildCalendar(eventList);

			// out.write(generator.getICal().getBytes("UTF-8"));
			pr.println(generator.getICal());

			// out.println(generator.getICal());
			FacesContext.getCurrentInstance().responseComplete();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method allows to sort events located in <code>eventList</code> by a
	 * title.
	 * <p>
	 * List of events is sorted by getting titles of two objects of the
	 * <code>Event</code> class and comparing them using {@link Comparator}. If
	 * comparison return a positive value then events change places in the list.
	 */
	public void sortByTitle() {
		if (sortTitleAsc) {
			Collections.sort(eventList, new Comparator<Event>() {

				@Override
				public int compare(Event o1, Event o2) {
					return o1.getEventTitle().compareTo(o2.getEventTitle());
				}

			});
			sortTitleAsc = false;
		} else {
			Collections.sort(eventList, new Comparator<Event>() {

				@Override
				public int compare(Event o1, Event o2) {
					return o2.getEventTitle().compareTo(o1.getEventTitle());
				}

			});
			sortTitleAsc = true;
		}
	}

	/**
	 * This method allows to sort events located in <code>eventList</code> by
	 * starting date.
	 * <p>
	 * List of events is sorted by getting date of the start of two objects of
	 * the <code>Event</code> class and comparing them using {@link Comparator}.
	 * If comparison return a positive value then events change places in the
	 * list.
	 */
	public void sortByDateStart() {
		if (sortDateStartAsc) {
			sortingDateStartByAsc();
			sortDateStartAsc = false;
		} else {
			Collections.sort(eventList, new Comparator<Event>() {

				@Override
				public int compare(Event o1, Event o2) {
					return o2.getDateStart().compareTo(o1.getDateStart());
				}

			});
			sortDateStartAsc = true;
		}
	}

	/**
	 * This method allows to sort events located in <code>eventList</code> by
	 * ascending starting date.
	 * <p>
	 * List of events is sorted by getting date of the start of two objects of
	 * the <code>Event</code> class and comparing them using {@link Comparator}.
	 * If comparison return a positive value then the event with an older date
	 * changes place in the list with the event with a younger date.
	 */
	private void sortingDateStartByAsc() {
		Collections.sort(eventList, new Comparator<Event>() {

			@Override
			public int compare(Event o1, Event o2) {
				return o1.getDateStart().compareTo(o2.getDateStart());
			}

		});
	}

	/**
	 * This method allows to sort events located in <code>eventList</code> by
	 * ending date.
	 * <p>
	 * List of events is sorted by getting starting date of two objects of the
	 * <code>Event</code> class and comparing them using {@link Comparator}. If
	 * comparison return a positive value then events change places in the list.
	 */
	public void sortByDateEnd() {
		if (sortDateEndAsc) {
			Collections.sort(eventList, new Comparator<Event>() {

				@Override
				public int compare(Event o1, Event o2) {
					return o1.getDateEnd().compareTo(o2.getDateEnd());
				}

			});
			sortDateEndAsc = false;
		} else {
			Collections.sort(eventList, new Comparator<Event>() {

				@Override
				public int compare(Event o1, Event o2) {
					return o2.getDateEnd().compareTo(o1.getDateEnd());
				}

			});
			sortDateEndAsc = true;
		}
	}
}
