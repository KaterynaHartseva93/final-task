package ua.nure.hartseva.SummaryTask4.exception;

public class WebBadRequestException extends WebException {

	private static final long serialVersionUID = 8039091806830751785L;
	
	public WebBadRequestException(String message) {
		super(400, message);
	}
	
	public WebBadRequestException(String message, Throwable e) {
		super(400, message, e);
	}
}
