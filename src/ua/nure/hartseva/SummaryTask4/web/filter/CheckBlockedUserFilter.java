package ua.nure.hartseva.SummaryTask4.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.entity.User;
import ua.nure.hartseva.SummaryTask4.service.IUserStatusService;

/**
 * 
 * Forcibly removes user from session if they have been blocked.
 * 
 * @author Kateryna Hartseva
 *
 */
public class CheckBlockedUserFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(CheckBlockedUserFilter.class);

	@Override
	public void destroy() {
		LOG.info("Filter is to be destroyed.");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		ServletContext context = request.getServletContext();
		IUserStatusService userStatusService = (IUserStatusService) context.getAttribute(Attributes.USER_STATUS_SERVICE);

		// do not process if session is null
		if (session == null) {
			filterChain.doFilter(request, response);
			return;
		}

		// do not process if user is GUEST
		User user = (User) session.getAttribute(Attributes.USER);
		if (user == null) {
			filterChain.doFilter(request, response);
			return;
		}

		// allow active user
		if (!userStatusService.isBlocked(user.getId())) {
			filterChain.doFilter(request, response);
			return;
		}

		// force signing out
		session.removeAttribute(Attributes.USER);
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		LOG.info("Filter initialization starts");
	}
}
