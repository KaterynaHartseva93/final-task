package ua.nure.hartseva.SummaryTask4.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.hartseva.SummaryTask4.bean.TransferType;

public class Transfer {

	private static final Logger LOG = Logger.getLogger(Transfer.class);
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private String path;
	private TransferType type;
	
	public static Transfer redirect(HttpServletResponse response, String path) {
		Transfer transfer = new Transfer();
		transfer.path = path;
		transfer.response = response;
		transfer.type = TransferType.REDIRECT;
		return transfer;
	}
	
	public static Transfer forward(HttpServletRequest request, HttpServletResponse response, String path) {
		Transfer transfer = new Transfer();
		transfer.path = path;
		transfer.request = request;
		transfer.response = response;
		transfer.type = TransferType.FORWARD;
		return transfer;
	}
	
	public static Transfer redirectToReferer(HttpServletRequest request, HttpServletResponse response) {
		Transfer transfer = new Transfer();
		transfer.request = request;
		transfer.response = response;
		transfer.type = TransferType.REDIRECT_TO_REFERER;
		return transfer;
	}
	
	public void proceed() throws ServletException, IOException {
		if (type == TransferType.FORWARD) {
			LOG.info("Forwarding to " + path);
			request.getRequestDispatcher(path).forward(request, response);
		} else if (type == TransferType.REDIRECT) {
			LOG.info("Redirecting to " + path);
			response.sendRedirect(path);
		} else {
			String referrer = request.getHeader("referer");
			LOG.info("Redirecting to referer " + referrer);
		    response.sendRedirect(referrer);
		}
	}
}
