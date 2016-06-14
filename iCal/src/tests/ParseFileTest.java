package tests;


import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import iCal.Model.ParseFile;
import iCal.data.Event;

public class ParseFileTest {
	String in = "";
	ParseFile parsefile;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadXCal() {
		List<Event> eventList = new LinkedList<Event>();
		ParseFile parsefile = new ParseFile(eventList);
		// TESTUJE 4 PLIKI .xml//
		setFile("XMLFiles/test1.xml"); // PLIK
																	// test1.xml
																	// POSIADA 2
																	// EVENTY
		assertEquals(0, eventList.size());
		parsefile.readXCal(in);
		assertEquals(2, eventList.size());
		parsefile.readXCal(in);
		assertEquals(4, eventList.size());
		assertNotEquals(3, eventList.size());
		eventList.clear();

		setFile("XMLFiles/test2.xml"); // PLIK
																	// test2.xml
																	// POSIADA 1
																	// EVENT
		assertEquals(0, eventList.size());
		parsefile.readXCal(in);
		assertEquals(1, eventList.size());
		assertNotEquals(3, eventList.size());
		eventList.clear();

		setFile("XMLFiles/test3.xml"); // PLIK
																	// test3.xml
																	// POSIADA 1
																	// EVENT
		assertEquals(0, eventList.size());
		parsefile.readXCal(in);
		assertEquals(1, eventList.size());
		assertNotEquals(3, eventList.size());
		eventList.clear();

		setFile("XMLFiles/test4.xml"); // PLIK
																	// test4.xml
																	// POSIADA 1
																	// EVENT
		assertEquals(0, eventList.size());
		parsefile.readXCal(in);
		assertEquals(1, eventList.size());
		assertNotEquals(3, eventList.size());
		eventList.clear();
	}

	public void setFile(String file) {
		in = "";
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				in += sCurrentLine + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
