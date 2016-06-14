package tests;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import iCal.Model.iCalGenerator;
import iCal.beans.iCalBean;
import iCal.data.Event;

public class iCalGeneratorTest {

	iCalBean bean = new iCalBean();

	iCalGenerator iCGtest = new iCalGenerator();
	List<Event> eventList2 = new LinkedList<>();
	Event event2 = new Event("Test Title", true, new Date((2016 - 1901), 05, 05), new Date((2016 - 1901), 05, 06),
			"Test description", "Test Location");

	@Test
	public void test() {
		eventList2.add(event2);
		iCGtest.buildCalendar(eventList2);
		assertEquals(iCGtest.getICal(),
				"BEGIN:VCALENDAR\r\nVERSION:2.0\r\nPRODID://JAVATAR UZ//\r\nBEGIN:VTIMEZONE\r\nTZID:Europe/Berlin\r\nTZURL:http://tzurl.org/zoneinfo-outlook/Europe/Berlin\r\nX-LIC- LOCATION:Europe/Berlin\r\nBEGIN:DAYLIGHT\r\nTZOFFSETFROM:+0100\r\nTZOFFSETTO:+0200\r\nTZNAME:CEST\r\nDTSTART:19700329T020000\r\nRRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=-1SU\r\nEND:DAYLIGHT\r\nBEGIN:STANDARD\r\nTZOFFSETFROM:+0200\r\nTZOFFSETTO:+0100\r\nTZNAME:CET\r\nDTSTART:19701025T030000\r\nRRULE:FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU\r\nEND:STANDARD\r\nEND:VTIMEZONE\r\nBEGIN:VEVENT\r\nDTSTART;VALUE=DATE:20150604\r\nDTEND;VALUE=DATE:20150605\r\nSUMMARY:Test Title\r\nLOCATION:Test Location\r\nDESCRIPTION:Test description\r\nEND:VEVENT\r\nEND:VCALENDAR");

	}

}
