package ua.nure.hartseva.SummaryTask4.exception;

public class MySqlDaoException extends RuntimeException {

	private static final long serialVersionUID = 7951918311571031878L;

	public MySqlDaoException(String message) {
		super(message);
	}
	
	public MySqlDaoException(String message, Throwable e) {
		super(message, e);
	}
}
