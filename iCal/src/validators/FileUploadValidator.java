package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

@FacesValidator(value = "fileUploadValidator")
public class FileUploadValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Part file = (Part) value;
		
		FacesMessage message = null;
		
		try {
			
			if (file == null || file.getContentType().isEmpty())
				message = new FacesMessage("Wymagany plik.");

			else if ((!file.getContentType().endsWith("calendar")) && (!file.getContentType().endsWith("xml")))
				message = new FacesMessage("Tylko pliki z rozszerzeniem .ics lub .xml");

			if (message != null && !message.getDetail().isEmpty()) {
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}

		} catch (Exception ex) {
			throw new ValidatorException(new FacesMessage(ex.getMessage()));
		}
		
	}

}