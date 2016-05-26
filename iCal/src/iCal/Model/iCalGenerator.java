package iCal.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import iCal.data.Event;

public class iCalGenerator {
	private final String NEW_FORMAT = "yyyyMMdd'T'HHmmss'Z'";
	private String version =    "VERSION:2.0\r\n";
	private String prodid =     "PRODID://JAVATAR UZ//\r\n";
	private String calBegin =   "BEGIN:VCALENDAR\r\n";
	private String calEnd =     "END:VCALENDAR";
	private String eventBegin = "BEGIN:VEVENT\r\n";
	private String eventEnd =   "END:VEVENT\r\n";
	
	String timeZone = "BEGIN:VTIMEZONE\r\n"+
			"TZID:Europe/Berlin\r\n"+
			"TZURL:http://tzurl.org/zoneinfo-outlook/Europe/Berlin\r\n"+
			"X-LIC- LOCATION:Europe/Berlin\r\n"+
			"BEGIN:DAYLIGHT\r\n"+
			"TZOFFSETFROM:+0100\r\n"+
			"TZOFFSETTO:+0200\r\n"+
			"TZNAME:CEST\r\n"+
			"DTSTART:19700329T020000\r\n"+
			"RRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=-1SU\r\n"+
			"END:DAYLIGHT\r\n"+
			"BEGIN:STANDARD\r\n"+
			"TZOFFSETFROM:+0200\r\n"+
			"TZOFFSETTO:+0100\r\n"+
			"TZNAME:CET\r\n"+
			"DTSTART:19701025T030000\r\n"+
			"RRULE:FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU\r\n"+
			"END:STANDARD\r\n"+
			"END:VTIMEZONE\r\n";
	
	
	private String iCal;
	private StringBuilder builder = new StringBuilder();
	
	private void openCalendar() { 
		builder.append(calBegin);
		builder.append(version);
		builder.append(prodid);	
		builder.append(timeZone);
	}
	
//	olewa true w ifie
	private void createEvents(List<Event> list) { 
		for(Event e: list) {
			if(e.isWholeDay()) {
//				Date endDate = incrementDate(e.getDateEnd());
				builder.append(eventBegin);
				builder.append("DTSTART;VALUE=DATE:" + resetHours(e.getDateStart())+"\r\n");
				builder.append("DTEND;VALUE=DATE:" + resetHours(e.getDateEnd())+"\r\n");
				builder.append("SUMMARY:"+e.getEventTitle()+"\r\n");
				builder.append("LOCATION:"+e.getLocation()+"\r\n");
				builder.append("DESCRIPTION:"+e.getDescription()+"\r\n");
				builder.append(eventEnd);
					
				
			} else {
				builder.append(eventBegin);
				builder.append("DTSTART:" + formatDate(e.getDateStart())+"\r\n");
				builder.append("DTEND:" + formatDate(e.getDateEnd())+"\r\n");
				builder.append("SUMMARY:"+e.getEventTitle()+"\r\n");
				builder.append("LOCATION:"+e.getLocation()+"\r\n");
				builder.append("DESCRIPTION:"+e.getDescription()+"\r\n");
				builder.append(eventEnd);
				
			}
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
	

	private String formatDate(Date date) {
		String newDateString;
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(NEW_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		newDateString = sdf.format(date);	
		return newDateString;
	}
	
	private Date incrementDate(Date date) {
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1); 
        return cal.getTime();
	}
	
	private String resetHours(Date date) {
		String dateString = formatDate(date);
		int startIndex = dateString.indexOf("T");
		int endIndex = dateString.indexOf("Z");
		String replacement = "000000";
		String toBeReplaced = dateString.substring(startIndex + 1, endIndex);
		dateString = dateString.replace(toBeReplaced, replacement);
		return dateString;
	}
	public String getICal() { 
		return iCal;
	}
	
}
