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

public class UZScheduleParser {
	// private String urlAddress;
	private Document document;
	private String day;
	private String uzClassesStart;
	private String uzClassesEnd;
	private String uzClassesName;
	private String uzClassesType;
	private String uzWeekType;
	private String uzGroup;
	private String uzLecturer;
	private String uzRoomNumber;
	private List<Event> uzEventsList;

	private ArrayList<Classes> uzClassesList = new ArrayList<>();

	public UZScheduleParser(List<Event> uzEventsList) {
		this.uzEventsList = uzEventsList;
	}

	public void connect(String uzUrl) {
		try {
			document = Jsoup.connect(uzUrl).get();
			parseSite();
			createEvents(uzClassesList);
		} catch (IOException e) {
			System.err.println("Exception in class: " + e.getClass());
		}
	}

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

	public Date incrementDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

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

	private int countElements(Element elem) {
		return elem.select("td").size();
	}

	public class Classes {

		private String day;
		private String uzStart;
		private String uzEnd;
		private String uzName;
		private String uzType;
		private String uzWeek;
		private String uzGroup;
		private String uzLecturer;
		private String uzRoomNumber;

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

		@Override
		public String toString() {
			return "Classes [day=" + day + ", uzStart=" + uzStart + ", uzEnd=" + uzEnd + ", uzName=" + uzName
					+ ", uzType=" + uzType + ", uzWeek=" + uzWeek + ", uzGroup=" + uzGroup + ", uzLecturer="
					+ uzLecturer + ", uzRoomNumber=" + uzRoomNumber + "]";
		}

		public String getDay() {
			return day;
		}

		public String getUzStart() {
			return uzStart;
		}

		public String getUzEnd() {
			return uzEnd;
		}

		public String getUzName() {
			return uzName;
		}

		public String getUzType() {
			return uzType;
		}

		public String getUzWeek() {
			return uzWeek;
		}

		public String getUzGroup() {
			return uzGroup;
		}

		public String getUzLecturer() {
			return uzLecturer;
		}

		public String getUzRoomNumber() {
			return uzRoomNumber;
		}

	}

	public List<Event> getUzEventsList() {
		return uzEventsList;
	}

	public ArrayList<Classes> getUzClassesList() {
		return uzClassesList;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
}
