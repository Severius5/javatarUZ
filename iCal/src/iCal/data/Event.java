package iCal.data;

import java.time.LocalDateTime;

public class Event {
	private String eventTitle;
	private boolean everyDay;
	// Data format 2007-12-03T10:15:30.
	private LocalDateTime dateStart;
	private LocalDateTime dateEnd;
	private String description;
	private String location;

	public Event() {
		setEventTitle("Title");
		setEveryDay(false);
		setDateStart(LocalDateTime.now());
		setDateEnd(LocalDateTime.of(2099, 1, 1, 1, 11, 1, 1));
		setDescription("Desc about this beautiful day.");
		setLocation("Location");
	}

	public Event(String eventTittle, boolean everyDay, LocalDateTime dateStart, LocalDateTime dateEnd,
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

	public LocalDateTime getDateStart() {
		return dateStart;
	}

	public void setDateStart(LocalDateTime dateStart) {
		this.dateStart = dateStart;
	}

	public LocalDateTime getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(LocalDateTime dateEnd) {
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

}
