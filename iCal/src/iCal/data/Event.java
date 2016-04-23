package iCal.data;

import java.util.Calendar;
import java.util.Date;

public class Event {
	private String eventTitle;
	private boolean everyDay;
	// Data format 2007-12-03T10:15:30.
	private Date dateStart;
	private Date dateEnd;
	private String description;
	private String location;
	private boolean editable;
	private Calendar currentDate;
	
	public Event() {
		setEventTitle("Title");
		setEveryDay(false);
		setDateStart(getCurrentDate());
		setDateEnd(getCurrentDate());
		setDescription("Desc about this beautiful day.");
		setLocation("Location");
	}

	public Event(String eventTittle, boolean everyDay, Date dateStart, Date dateEnd,
			String description, String location) {
		setEventTitle(eventTittle);
		setEveryDay(everyDay);
		setDateStart(dateStart);
		setDateEnd(dateEnd);
		setDescription(description);
		setLocation(location);
	}
	
	public Event(Event event){
		setEventTitle(event.getEventTitle());
		setEveryDay(event.isEveryDay());
		setDateStart(event.getDateStart());
		setDateEnd(event.getDateEnd());
		setDescription(event.getDescription());
		setLocation(event.getLocation());
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public boolean isEveryDay() {
		return everyDay;
	}

	public void setEveryDay(boolean everyDay) {
		this.everyDay = everyDay;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Date getCurrentDate() {
		currentDate = Calendar.getInstance();
		Date currDate = currentDate.getTime();
		return currDate;
	}
	
	
}
