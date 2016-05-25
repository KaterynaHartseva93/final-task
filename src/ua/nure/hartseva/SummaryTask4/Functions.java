package ua.nure.hartseva.SummaryTask4;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.entity.Status;
import ua.nure.hartseva.SummaryTask4.util.WebUtils;

/**
 * Custom tag function class.
 * 
 * @author Kateryna Hartseva
 *
 */
public class Functions {

	private static final String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss";

	/**
	 * Retrieves user's role from request.
	 */
	public static Role getRole(HttpServletRequest request) {
		return WebUtils.getRole(request);
	}

	/**
	 * Returns the user's status opposite to given.
	 */
	public static Status getOppositeStatus(Status currentStatus) {
		return Status.getOppositeStatus(currentStatus);
	}

	/**
	 * Checks if specified object is in the specified array.
	 */
	public static boolean containsInArray(List<Object> list, Object item) {
		if (list == null || list.isEmpty()) {
			return false;
		}
		return list.contains(item);
	}

	/**
	 * Returns a date string in appropriate format: dd-MM-yyyy hh:mm:ss.
	 */
	public static String printDate(Date date) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(DATE_FORMAT).format(date);
	}

	/**
	 * Rounds double value. For example, if it was 12.00000001, then it will be 12.00.
	 */
	public static double round(double value) {
		return Math.rint(100.0 * value) / 100.0;
	}
}
