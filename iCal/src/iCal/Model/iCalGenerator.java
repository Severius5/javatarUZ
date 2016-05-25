package iCal.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import iCal.data.Event;

public class iCalGenerator {
	private String version =    "VERSION:2.0\n";
	private String prodid =     "PRODID://JAVATAR UZ//\n";
	private String calBegin =   "BEGIN:VCALENDAR\n";
	private String calEnd =     "END:VCALENDAR";
	private String eventBegin = "BEGIN:VEVENT\n";
	private String eventEnd =   "END:VEVENT\n";
	
	String timeZone = "BEGIN:VTIMEZONE\n"+
			"TZID:Europe/Berlin\n"+
			"TZURL:http://tzurl.org/zoneinfo-outlook/Europe/Berlin\n"+
			"X-LIC- LOCATION:Europe/Berlin\n"+
			"BEGIN:DAYLIGHT\n"+
			"TZOFFSETFROM:+0100\n"+
			"TZOFFSETTO:+0200\n"+
			"TZNAME:CEST\n"+
			"DTSTART:19700329T020000\n"+
			"RRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=-1SU\n"+
			"END:DAYLIGHT\n"+
			"BEGIN:STANDARD\n"+
			"TZOFFSETFROM:+0200\n"+
			"TZOFFSETTO:+0100\n"+
			"TZNAME:CET\n"+
			"DTSTART:19701025T030000\n"+
			"RRULE:FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU\n"+
			"END:STANDARD\n"+
			"END:VTIMEZONE\n";
	
	
	private String iCal;
	private StringBuilder builder = new StringBuilder();
	
	private void openCalendar() { 
		builder.append(calBegin);
		builder.append(version);
		builder.append(prodid);	
		builder.append(timeZone);
	}
	private void createEvents(List<Event> list) { 
		for(Event e: list) {
			builder.append(eventBegin);
			builder.append("DTSTART:" + formatDate(e.getDateStart())+"\n");
			builder.append("DTEND:" + formatDate(e.getDateEnd())+"\n");
			builder.append("SUMMARY:"+e.getEventTitle()+"\n");
			builder.append("LOCATION:"+e.getLocation()+"\n");
			builder.append("DESCRIPTION:"+e.getDescription()+"\n");
			builder.append(eventEnd);
		}
	}
	private void closeCalendar() {
		builder.append(calEnd);
	}
	
	public void buildCalendar(List<Event> list) {
		openCalendar();
		createEvents(list);
		closeCalendar();
		iCal = builder.toString();
	}
	

	public String formatDate(Date date) {
		String newDateString;
		final String NEW_FORMAT = "yyyyMMdd'T'HHMMSS'Z'";
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(NEW_FORMAT);
		newDateString = sdf.format(date);	
		return newDateString;
	}
	
	public String getICal() { 
		return iCal;
	}
	
}
