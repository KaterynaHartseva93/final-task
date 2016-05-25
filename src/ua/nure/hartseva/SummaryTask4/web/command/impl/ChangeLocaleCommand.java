package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

public class ChangeLocaleCommand extends Command{
	
	private static final long serialVersionUID = 2969399180089337138L;
	
	private static final Logger LOG = Logger.getLogger(ChangeLocaleCommand.class);

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		LOG.debug("Command starts");
		String localeLanguge = request.getParameter("lang");
		PrintWriter printWriter = response.getWriter();
		Locale locale = new Locale(localeLanguge);
		HttpSession session = request.getSession();
		Config.set(session, Config.FMT_LOCALE, locale);
		printWriter.print(false);
		LOG.debug("Command finished");
		
		return Transfer.redirectToReferer(request, response);
	}
	
	@Override
	protected List<Role> allowedRoles() {
		return Arrays.asList(Role.ADMIN, Role.GUEST, Role.CLIENT);
	}

}
