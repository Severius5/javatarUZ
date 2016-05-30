package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import iCal.beans.iCalBean;
import iCal.data.Event;



public class InterfaceTest {
	Event event2 = new Event("EventTitle",true, new Date((2016-1901), 05, 05), new Date((2016-1901), 05, 06), "Desc", "Location");	
	Event event = new Event("AEventTitle",false, new Date((2016-1901), 04, 05), new Date((2016-1901), 04, 06), "Desc", "Location");
	iCalBean bean = new iCalBean();
	List<Event> eventList = bean.getEventList();
	
	@Before
	public void setUp(){
		eventList = new LinkedList<Event>();		
		bean.setEventList(eventList);
		eventList.add(event2);
		eventList.add(event);
	}
	
	@Test
	public void testAddEvent() {
		Event event3 = new Event("EventNumber3",true, new Date((2016-1901), 05, 05), new Date((2016-1901), 05, 06), "Desc", "Location");
		eventList.add(event3);
		assertTrue(eventList.contains(event3));
	}
	
	@Test
	public void testCopyEvent() {
		bean.copyEvent(event);
		assertEquals(3, eventList.size());
	}
	
	@Test
	public void testDeleteEvent(){
		bean.deleteEvent(event);
		assertFalse(eventList.contains(event));
	}
	
	@Test
	public void testClearEvents() {
		eventList.add(event);
		bean.clearEvents();
		assertEquals(0, eventList.size());
	}	
	
	@Test
	public void testSaveEventEdit() {
		event.setEditable(true);
		bean.saveEventEdit(event);
		assertFalse(event.isEditable());
	}
	
	@Test
	public void editTest() {
		event.setEditable(false);
		bean.editEventEdit(event);
		assertTrue(event.isEditable());
	}
}
