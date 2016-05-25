package ua.nure.hartseva.SummaryTask4.exception;

public abstract class WebException extends RuntimeException {

	private static final long serialVersionUID = 4537193977704872834L;
	
	private final int statusCode;
	
	public WebException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}
	
	public WebException(int statusCode, String message, Throwable e) {
		super(message, e);
		this.statusCode = statusCode;
		
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
