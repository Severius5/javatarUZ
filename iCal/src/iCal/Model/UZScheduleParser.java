package iCal.Model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import iCal.data.Event;

/**
 * Parses the timetable's data from a specified website (in this case UZ timetable's homepage) and creates a new event using that data.
 * 
 * @author Sebastian Perek
 * @version
 * @see SimpleDateFormat
 * @see List
 * @see Calendar
 * @see Date
 * @since
 */
public class UZScheduleParser {
	
	/** The document. */
	// private String urlAddress;
	private Document document;
	
	/** The day. */
	private String day;
	
	/** The uz classes start. */
	private String uzClassesStart;
	
	/** The uz classes end. */
	private String uzClassesEnd;
	
	/** The uz classes name. */
	private String uzClassesName;
	
	/** The uz classes type. */
	private String uzClassesType;
	
	/** The uz week type. */
	private String uzWeekType;
	
	/** The uz group. */
	private String uzGroup;
	
	/** The uz lecturer. */
	private String uzLecturer;
	
	/** The uz room number. */
	private String uzRoomNumber;
	
	/** The uz events list. */
	private List<Event> uzEventsList;

	/** The uz classes list. */
	private ArrayList<Classes> uzClassesList = new ArrayList<>();

	/**
	 * Instantiates a new UZ schedule parser.
	 *
	 * @param uzEventsList the uz events list
	 */
	public UZScheduleParser(List<Event> uzEventsList) {
		this.uzEventsList = uzEventsList;
	}

	/**
	 * Connects to a website using given url in order to parse data located there.
	 * <p>
	 * Data parsed by the method <code>parseSite</code> is ready for being used in creating event process
	 * and then ready for users to use.
	 *
	 * @param uzUrl the String
	 * @throws IOException for exception in class
	 */
	public void connect(String uzUrl) {
		try {
			document = Jsoup.connect(uzUrl).get();
			parseSite();
			createEvents(uzClassesList);
		} catch (IOException e) {
			System.err.println("Exception in class: " + e.getClass());
		}
	}

	/**
	 * Parses and formats the data structure located on a website to a text being able to use in further processing.
	 * 
	 */
	public void parseSite() {
		// Elements elements = document.select("table[border=1");
		Elements rows = document.select("table[border=1").select("tr");
		int a = 2;
		for (Element row : rows) {
			if (a == 2) {
				a++;
				continue;
			}
			if (countElements(row) == 1) {
				day = row.select("td[align=left]").text();
				day = day.toLowerCase();
			} else {
				uzGroup = row.select("td[align=center]").first().previousElementSibling().text();
				uzClassesStart = row.select("td[align=center]").first().text();
				uzClassesEnd = row.select("td[align=center]").last().text();
				uzClassesName = row.select("td[align=center]").last().nextElementSibling().text();
				uzClassesType = row.select("td[align=center]").last().nextElementSibling().nextElementSibling().text();
				uzWeekType = row.select("td[align=center]").last().nextElementSibling().nextElementSibling()
						.nextElementSibling().nextElementSibling().nextElementSibling().text();
				uzLecturer = row.select("td[align=center]").last().nextElementSibling().nextElementSibling()
						.nextElementSibling().text();
				uzRoomNumber = row.select("td[align=center]").last().nextElementSibling().nextElementSibling()
						.nextElementSibling().nextElementSibling().text();

				uzClassesList.add(new Classes(day, uzClassesStart, uzClassesEnd, uzClassesName, uzClassesType,
						uzWeekType, uzGroup, uzLecturer, uzRoomNumber));
			}
		}
	}

	/**
	 * Increments the calendar's date each time this method is called.
	 *
	 * @param date the date
	 * @return the date
	 */
	public Date incrementDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	/**
	 * Formats and creates an event from timetable's data.
	 *
	 * @param uzClassesList the ArrayList
	 * @throws ParseException
	 */
	public void createEvents(ArrayList<Classes> uzClassesList) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy; / HH:mm");
		for (Classes uzClass : uzClassesList) {
			String eventTitle = uzClass.uzName;
			boolean wholeDay = false;
			Date dateStart = null;
			Date dateEnd = null;
			String description = uzClass.uzName + " " + " Typ zajec: " + uzClass.uzType + " Prowadzacy: "
					+ uzClass.uzLecturer + " Grupa: " + uzClass.uzGroup;
			String location = uzClass.uzRoomNumber;
			String dayfromDate = sdf.format(date);
			if (uzClass.uzWeek.length() > 5) {
				try {
					dateStart = sdf2.parse(uzClass.uzWeek + " " + uzClass.uzStart);
					dateEnd = sdf2.parse(uzClass.uzWeek + " " + uzClass.uzEnd);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				while (!uzClass.day.equals(dayfromDate)) {
					date = incrementDate(date);
					dayfromDate = sdf.format(date);
				}
				dateStart = addTimeToDate(date, uzClass.uzStart);
				dateEnd = addTimeToDate(date, uzClass.uzEnd);
			}
			uzEventsList.add(new Event(eventTitle, wholeDay, dateStart, dateEnd, description, location));
		}
	}

	/**
	 * Adds the time to calendar's date.
	 *
	 * @param date the date
	 * @param time the time
	 * @return the date
	 */
	public Date addTimeToDate(Date date, String time) {
		String hrs = time.substring(0, 2);
		String mins = time.substring(3, 5);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hrs));
		cal.set(Calendar.MINUTE, Integer.parseInt(mins));
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Counts and returns the number of elements.
	 *
	 * @param elem the elem
	 * @return the int
	 */
	private int countElements(Element elem) {
		return elem.select("td").size();
	}

	/**
	 * The Class Classes.
	 */
	public class Classes {

		/** The day. */
		private String day;
		
		/** The uz start. */
		private String uzStart;
		
		/** The uz end. */
		private String uzEnd;
		
		/** The uz name. */
		private String uzName;
		
		/** The uz type. */
		private String uzType;
		
		/** The uz week. */
		private String uzWeek;
		
		/** The uz group. */
		private String uzGroup;
		
		/** The uz lecturer. */
		private String uzLecturer;
		
		/** The uz room number. */
		private String uzRoomNumber;

		/**
		 * Instantiates a new classes.
		 *
		 * @param day the day
		 * @param uzStart the uz start
		 * @param uzEnd the uz end
		 * @param uzName the uz name
		 * @param uzType the uz type
		 * @param uzWeek the uz week
		 * @param uzGroup the uz group
		 * @param uzLecturer the uz lecturer
		 * @param uzRoomNumber the uz room number
		 */
		public Classes(String day, String uzStart, String uzEnd, String uzName, String uzType, String uzWeek,
				String uzGroup, String uzLecturer, String uzRoomNumber) {
			super();
			this.day = day;
			this.uzStart = uzStart;
			this.uzEnd = uzEnd;
			this.uzName = uzName;
			this.uzType = uzType;
			this.uzWeek = uzWeek;
			this.uzGroup = uzGroup;
			this.uzLecturer = uzLecturer;
			this.uzRoomNumber = uzRoomNumber;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Classes [day=" + day + ", uzStart=" + uzStart + ", uzEnd=" + uzEnd + ", uzName=" + uzName
					+ ", uzType=" + uzType + ", uzWeek=" + uzWeek + ", uzGroup=" + uzGroup + ", uzLecturer="
					+ uzLecturer + ", uzRoomNumber=" + uzRoomNumber + "]";
		}

		/**
		 * Gets the day.
		 *
		 * @return the day
		 */
		public String getDay() {
			return day;
		}

		/**
		 * Gets the uz start.
		 *
		 * @return the uz start
		 */
		public String getUzStart() {
			return uzStart;
		}

		/**
		 * Gets the uz end.
		 *
		 * @return the uz end
		 */
		public String getUzEnd() {
			return uzEnd;
		}

		/**
		 * Gets the uz name.
		 *
		 * @return the uz name
		 */
		public String getUzName() {
			return uzName;
		}

		/**
		 * Gets the uz type.
		 *
		 * @return the uz type
		 */
		public String getUzType() {
			return uzType;
		}

		/**
		 * Gets the uz week.
		 *
		 * @return the uz week
		 */
		public String getUzWeek() {
			return uzWeek;
		}

		/**
		 * Gets the uz group.
		 *
		 * @return the uz group
		 */
		public String getUzGroup() {
			return uzGroup;
		}

		/**
		 * Gets the uz lecturer.
		 *
		 * @return the uz lecturer
		 */
		public String getUzLecturer() {
			return uzLecturer;
		}

		/**
		 * Gets the uz room number.
		 *
		 * @return the uz room number
		 */
		public String getUzRoomNumber() {
			return uzRoomNumber;
		}

	}

	/**
	 * Gets the uz events list.
	 *
	 * @return the uz events list
	 */
	public List<Event> getUzEventsList() {
		return uzEventsList;
	}

	/**
	 * Gets the uz classes list.
	 *
	 * @return the uz classes list
	 */
	public ArrayList<Classes> getUzClassesList() {
		return uzClassesList;
	}

	/**
	 * Gets the document.
	 *
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * Sets the document.
	 *
	 * @param document the new document
	 */
	public void setDocument(Document document) {
		this.document = document;
	}
}
