package ua.nure.hartseva.SummaryTask4.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.exception.WebException;
import ua.nure.hartseva.SummaryTask4.util.WebUtils;
import ua.nure.hartseva.SummaryTask4.web.command.Command;
import ua.nure.hartseva.SummaryTask4.web.command.impl.CommandContainer;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 2, maxRequestSize = 1024 * 1024 * 5)
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 2423353715955164816L;

	private static final Logger LOG = Logger.getLogger(Controller.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * Main method of this controller.
	 */
	private void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		LOG.debug("Controller starts");

		String commandKey = getCommandKey(request);

		// obtain command object by its name, never equal to null
		Command command = CommandContainer.get(commandKey);
		LOG.trace("Obtained command --> " + command);

		Role role = WebUtils.getRole(request);
		if (!command.accept(role)) {
			response.sendError(403);
			return;
		}

		try {
			Transfer transfer = command.execute(request, response);
			transfer.proceed();
		} catch (WebException e) {
			response.sendError(e.getStatusCode());
		}
	}

	private String getCommandKey(HttpServletRequest request) {
		String uri = normalize(request.getRequestURI(), request.getContextPath());
		return request.getMethod() + "::" + uri;
	}

	private String normalize(String uri, String contextPath) {
		String uriPart = uri.substring(contextPath.length());
		if (uriPart != null && uriPart.endsWith("/")) {
			return uriPart.substring(0, uriPart.length() - 1);
		}
		return uriPart;
	}
}