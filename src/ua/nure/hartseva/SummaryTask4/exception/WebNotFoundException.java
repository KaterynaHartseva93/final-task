package ua.nure.hartseva.SummaryTask4.exception;

public class WebNotFoundException extends WebException {

	private static final long serialVersionUID = 5507468255086447796L;

	public WebNotFoundException(String message) {
		super(404, message);
	}
}
