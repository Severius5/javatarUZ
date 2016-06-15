package validators;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * The Class UrlUploadValidator.
 * 
 * @author Kamil Radykowski
 * @see HttpURLConnection
 * @see URL
 */
@FacesValidator(value = "urlUploadValidator")
public class UrlUploadValidator implements Validator {

	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		FacesMessage message = null;
		String url = (String) value;
		try {
			if (!exists(url)) {
				message = new FacesMessage("B³êdny URL: " + url);
			}
			
			else if (!url.startsWith("http://plan.uz.zgora.pl")) {
				message = new FacesMessage("Tylko witryna http://plan.uz.zgora.pl");
			}

			if (message != null && !message.getDetail().isEmpty()) {
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}

		} catch (Exception e) {
			throw new ValidatorException(new FacesMessage(e.getMessage()));
		}
	}

	/**
	 * Checks if a website under given url responds.
	 *
	 * @param URLName the URL name
	 * @return true, if successful
	 */
	static boolean exists(String URLName) {
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}



