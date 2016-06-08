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

@ManagedBean
@SessionScoped
public class LoadFile {

	private ParseFile parseFile;
	private Part file;
	private String url;

	public LoadFile(List<Event> list) {
		parseFile = new ParseFile(list);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;

	}

	public void load() {
		try (InputStream input = file.getInputStream()) {
			int dot = file.getSubmittedFileName().lastIndexOf('.');

			BufferedReader br = new BufferedReader(new InputStreamReader(input));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
			String stringFile = sb.toString();

			if (file.getSubmittedFileName().substring(dot + 1).equals("ics")) {
				parseFile.readICal(stringFile);
			} else {
				parseFile.readXCal(stringFile);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}