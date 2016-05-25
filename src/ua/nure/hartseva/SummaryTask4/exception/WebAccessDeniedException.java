package ua.nure.hartseva.SummaryTask4.exception;

public class WebAccessDeniedException extends WebException {

	private static final long serialVersionUID = -21919966099490596L;

	public WebAccessDeniedException(String message) {
		super(403, message);
	}
}
