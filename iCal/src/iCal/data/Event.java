package iCal.data;

import java.util.Calendar;
import java.util.Date;

/**
 * The Class Event.
 * 
 * @author Sebastian Pieni¹¿ek
 * @author Tomasz Cichocki
 * @author Sebastian Perek
 * @see Calendar
 * @see Date
 */
public class Event {

	/** The event title. */
	private String eventTitle;
	
	/** The whole day. */
	private boolean wholeDay;
	
	/** The date start. */
	private Date dateStart;
	
	/** The date end. */
	private Date dateEnd;
	
	/** The description. */
	private String description;
	
	/** The location. */
	private String location;
	
	/** The editable. */
	private boolean editable;
	
	/** The current date. */
	private Calendar currentDate;

	/**
	 * Instantiates a new event.
	 */
	public Event() {
		setEventTitle("Title");
		setWholeDay(false);
		setDateStart(getCurrentDate());
		setDateEnd(getCurrentDate());
		setDescription("Desc about this beautiful day.");
		setLocation("Location");
	}

	/**
	 * Instantiates a new event.
	 *
	 * @param eventTittle the event tittle
	 * @param wholeDay the whole day
	 * @param dateStart the date start
	 * @param dateEnd the date end
	 * @param description the description
	 * @param location the location
	 */
	public Event(String eventTittle, boolean wholeDay, Date dateStart, Date dateEnd, String description,
			String location) {
		setEventTitle(eventTittle);
		setWholeDay(wholeDay);
		setDateStart(dateStart);
		setDateEnd(dateEnd);
		setDescription(description);
		setLocation(location);
	}

	/**
	 * Instantiates a new event.
	 *
	 * @param event the event
	 */
	public Event(Event event) {
		setEventTitle(event.getEventTitle());
		setWholeDay(event.isWholeDay());
		setDateStart(event.getDateStart());
		setDateEnd(event.getDateEnd());
		setDescription(event.getDescription());
		setLocation(event.getLocation());
	}

	/**
	 * Gets the event title.
	 *
	 * @return the event title
	 */
	public String getEventTitle() {
		return eventTitle;
	}

	/**
	 * Sets the event title.
	 *
	 * @param eventTitle the new event title
	 */
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	/**
	 * Checks if is whole day.
	 *
	 * @return true, if is whole day
	 */
	public boolean isWholeDay() {
		return wholeDay;
	}

	/**
	 * Sets the whole day.
	 *
	 * @param wholeDay the new whole day
	 */
	public void setWholeDay(boolean wholeDay) {
		this.wholeDay = wholeDay;
	}

	/**
	 * Gets the date start.
	 *
	 * @return the date start
	 */
	public Date getDateStart() {
		return dateStart;
	}

	/**
	 * Sets the date start.
	 *
	 * @param dateStart the new date start
	 */
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	/**
	 * Gets the date end.
	 *
	 * @return the date end
	 */
	public Date getDateEnd() {
		return dateEnd;
	}

	/**
	 * Sets the date end.
	 *
	 * @param dateEnd the new date end
	 */
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Checks if is editable.
	 *
	 * @return true, if is editable
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Sets the editable.
	 *
	 * @param editable the new editable
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * Gets the current date.
	 *
	 * @return the current date
	 */
	public Date getCurrentDate() {
		currentDate = Calendar.getInstance();
		Date currDate = currentDate.getTime();
		return currDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Event [eventTitle=" + eventTitle + ", wholeDay=" + wholeDay + ", dateStart=" + dateStart + ", dateEnd="
				+ dateEnd + ", description=" + description + ", location=" + location + ", editable=" + editable
				+ ", currentDate=" + currentDate + "]";
	}
	
	
}
