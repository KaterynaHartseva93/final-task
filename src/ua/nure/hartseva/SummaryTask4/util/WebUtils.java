package ua.nure.hartseva.SummaryTask4.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.entity.User;

public class WebUtils {

	public static Role getRole(HttpServletRequest request) {
		Role role = Role.GUEST;

		HttpSession session = request.getSession();
		if (session == null) {
			return role;
		}

		User user = (User) session.getAttribute(Attributes.USER);
		if (user == null) {
			return role;
		}
		return user.getRole();
	}
}
