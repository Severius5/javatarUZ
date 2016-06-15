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
	iCalGenerator iCGtest2 = new iCalGenerator();
	List<Event> eventList1 = new LinkedList<>();
	List<Event> eventList2 = new LinkedList<>();
	Event event1 = new Event("Test Title", false, new Date((2016 - 1901), 3, 3, 3, 3),
			new Date((2016 - 1901), 4, 4, 4, 4), "Test description", "Test Location");
	Event event2 = new Event("Test Title", true, new Date((2016 - 1901), 05, 05), new Date((2016 - 1901), 05, 06),
			"Test description", "Test Location");
	

	@Test
	public void testCloseCalendar() {
		iCGtest.closeCalendar();
		assertEquals(iCGtest.getBuilder().toString(), "END:VCALENDAR");

		iCGtest2.setBuilder("Test ");
		iCGtest2.closeCalendar();
		assertEquals(iCGtest2.getBuilder().toString(), "Test END:VCALENDAR");

	}

	@Test
	public void testOpenCalendar() {
		iCGtest.openCalendar();
		assertEquals(iCGtest.getBuilder().toString(),
				"BEGIN:VCALENDAR\r\nVERSION:2.0\r\nPRODID://JAVATAR UZ//\r\nBEGIN:VTIMEZONE\r\nTZID:Europe/Berlin\r\nTZURL:http://tzurl.org/zoneinfo-outlook/Europe/Berlin\r\nX-LIC- LOCATION:Europe/Berlin\r\nBEGIN:DAYLIGHT\r\nTZOFFSETFROM:+0100\r\nTZOFFSETTO:+0200\r\nTZNAME:CEST\r\nDTSTART:19700329T020000\r\nRRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=-1SU\r\nEND:DAYLIGHT\r\nBEGIN:STANDARD\r\nTZOFFSETFROM:+0200\r\nTZOFFSETTO:+0100\r\nTZNAME:CET\r\nDTSTART:19701025T030000\r\nRRULE:FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU\r\nEND:STANDARD\r\nEND:VTIMEZONE\r\n");

		iCGtest2.setBuilder("Test ");
		iCGtest2.openCalendar();
		assertEquals(iCGtest2.getBuilder().toString(),
				"Test BEGIN:VCALENDAR\r\nVERSION:2.0\r\nPRODID://JAVATAR UZ//\r\nBEGIN:VTIMEZONE\r\nTZID:Europe/Berlin\r\nTZURL:http://tzurl.org/zoneinfo-outlook/Europe/Berlin\r\nX-LIC- LOCATION:Europe/Berlin\r\nBEGIN:DAYLIGHT\r\nTZOFFSETFROM:+0100\r\nTZOFFSETTO:+0200\r\nTZNAME:CEST\r\nDTSTART:19700329T020000\r\nRRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=-1SU\r\nEND:DAYLIGHT\r\nBEGIN:STANDARD\r\nTZOFFSETFROM:+0200\r\nTZOFFSETTO:+0100\r\nTZNAME:CET\r\nDTSTART:19701025T030000\r\nRRULE:FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU\r\nEND:STANDARD\r\nEND:VTIMEZONE\r\n");

	}

	@Test
	public void testCreateEvents() {
		eventList1.add(event1);
		iCGtest.createEvents(eventList1);
		assertEquals(iCGtest.getBuilder().length(), 150);
		eventList2.add(event2);
		iCGtest2.createEvents(eventList2);
		assertEquals(iCGtest2.getBuilder().length(), 156);
	}

	@Test
	public void testBuildCalendar() {
		eventList2.add(event2);
		iCGtest.buildCalendar(eventList2);
		assertEquals(iCGtest.getICal(),
				"BEGIN:VCALENDAR\r\nVERSION:2.0\r\nPRODID://JAVATAR UZ//\r\nBEGIN:VTIMEZONE\r\nTZID:Europe/Berlin\r\nTZURL:http://tzurl.org/zoneinfo-outlook/Europe/Berlin\r\nX-LIC- LOCATION:Europe/Berlin\r\nBEGIN:DAYLIGHT\r\nTZOFFSETFROM:+0100\r\nTZOFFSETTO:+0200\r\nTZNAME:CEST\r\nDTSTART:19700329T020000\r\nRRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=-1SU\r\nEND:DAYLIGHT\r\nBEGIN:STANDARD\r\nTZOFFSETFROM:+0200\r\nTZOFFSETTO:+0100\r\nTZNAME:CET\r\nDTSTART:19701025T030000\r\nRRULE:FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU\r\nEND:STANDARD\r\nEND:VTIMEZONE\r\nBEGIN:VEVENT\r\nDTSTART;VALUE=DATE:20150604\r\nDTEND;VALUE=DATE:20150605\r\nSUMMARY:Test Title\r\nLOCATION:Test Location\r\nDESCRIPTION:Test description\r\nEND:VEVENT\r\nEND:VCALENDAR");

	}

}
