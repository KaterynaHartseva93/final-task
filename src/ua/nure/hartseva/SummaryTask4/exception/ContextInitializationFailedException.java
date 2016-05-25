package ua.nure.hartseva.SummaryTask4.exception;

public class ContextInitializationFailedException extends RuntimeException {

	private static final long serialVersionUID = 6180709793029029651L;

	public ContextInitializationFailedException(String message, Throwable e) {
		super(message, e);
	}
}
