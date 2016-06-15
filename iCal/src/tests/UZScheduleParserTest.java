package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.junit.Test;

import iCal.Model.UZScheduleParser;
import iCal.data.Event;

public class UZScheduleParserTest {

	@Test
	public void testConnect() {
		String url = "http://plan.uz.zgora.pl/grupy_plan.php?pId_Obiekt=16670";
		ArrayList<Event> events = new ArrayList<>();
		UZScheduleParser parserTest = new UZScheduleParser(events);

		try {
			parserTest.connect(url);
			assertTrue(true);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void parseSiteTest() {
		String url = "http://plan.uz.zgora.pl/grupy_plan.php?pId_Obiekt=16670";
		ArrayList<Event> events = new ArrayList<>();
		UZScheduleParser parserTest = new UZScheduleParser(events);
		try {
			parserTest.setDocument(Jsoup.connect(url).get());
			parserTest.parseSite();
		} catch (IOException e) {
			System.err.println("Exception in class: " + e.getClass());
		}
		assertEquals(14, parserTest.getUzClassesList().size());
	}

	@Test
	public void createEventsTest() {
		String url = "http://plan.uz.zgora.pl/grupy_plan.php?pId_Obiekt=16670";
		ArrayList<Event> events = new ArrayList<>();
		UZScheduleParser parserTest = new UZScheduleParser(events);
		try {
			parserTest.setDocument(Jsoup.connect(url).get());
			parserTest.parseSite();
			parserTest.createEvents(parserTest.getUzClassesList());
		} catch (IOException e) {
			System.err.println("Exception in class: " + e.getClass());
		}
		assertEquals(parserTest.getUzClassesList().get(0).getUzRoomNumber(),
				parserTest.getUzEventsList().get(0).getLocation());
		assertEquals(parserTest.getUzClassesList().get(1).getUzRoomNumber(),
				parserTest.getUzEventsList().get(1).getLocation());
		assertEquals(parserTest.getUzClassesList().get(0).getUzName(),
				parserTest.getUzEventsList().get(0).getEventTitle());
		assertEquals(parserTest.getUzClassesList().get(1).getUzName(),
				parserTest.getUzEventsList().get(1).getEventTitle());
	}
	
	@Test
	public void incrementDateTest() {
		ArrayList<Event> events = new ArrayList<>();
		UZScheduleParser parserTest = new UZScheduleParser(events);
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date date = new Date();
		String dateExpected = sdf.format(date);
		String dateActual = sdf.format(parserTest.incrementDate(date));
		Integer day = Integer.parseInt(dateExpected);
		day++;
		dateExpected = Integer.toString(day);
		assertEquals(dateExpected, dateActual);
		
	}
	
	@Test
	public void addTimeToDateTest() {
		ArrayList<Event> events = new ArrayList<>();
		UZScheduleParser parserTest = new UZScheduleParser(events);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		assertEquals("17:10", sdf.format(parserTest.addTimeToDate(new Date(), "17:10")) );
		assertEquals("22:53", sdf.format(parserTest.addTimeToDate(new Date(), "22:53")) );
		assertEquals("10:01", sdf.format(parserTest.addTimeToDate(new Date(), "10:01")) );
	}

}
