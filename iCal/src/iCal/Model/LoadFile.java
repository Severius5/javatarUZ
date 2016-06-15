package iCal.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

import iCal.data.Event;

/**
 * Processes file to a calendar event loaded by user.
 * 
 * @author Sebastian Pieni¹¿ek
 * @author Marcin Marynowski
 * @author Sebastian Perek
 * @version
 * @see BufferedReader
 * @see List
 * @since 
 */
@ManagedBean
@SessionScoped
public class LoadFile {

	/** The parse file. */
	private ParseFile parseFile;
	
	/** The uz parser. */
	private UZScheduleParser uzParser;
	
	/** The file. */
	private Part file;
	
	/** The url. */
	private String url;

	/**
	 * Instantiates a new load file.
	 *
	 * @param list the list
	 */
	public LoadFile(List<Event> list) {
		parseFile = new ParseFile(list);
		uzParser = new UZScheduleParser(list);
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public Part getFile() {
		return file;
	}

	/**
	 * Sets the file.
	 *
	 * @param file the new file
	 */
	public void setFile(Part file) {
		this.file = file;

	}

	/**
	 * Builds a string from given input stream.
	 * <p>
	 * Once the input stream is changed to the string it's parsed by a correct parser.
	 * 
	 * @throws IOException if fails to get an input stream
	 */
	public void load() {
		if (file != null) {
			try (InputStream input = file.getInputStream()) {

				BufferedReader br = new BufferedReader(new InputStreamReader(input));
				String line;
				StringBuffer sb = new StringBuffer();
				while ((line = br.readLine()) != null) {
					sb.append(line);
					sb.append("\r\n");
				}
				String stringFile = sb.toString();

				if (file.getSubmittedFileName().endsWith("ics")) {
					parseFile.readICal(stringFile);
				} else {
					parseFile.readXCal(stringFile);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Connects to a parser using a specified url.
	 */
	public void loadUrl() {
		if (url != null) {
			uzParser.connect(url);
		}
	}

}