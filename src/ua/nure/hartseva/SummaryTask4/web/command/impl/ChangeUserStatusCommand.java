package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.entity.Status;
import ua.nure.hartseva.SummaryTask4.service.IUserService;
import ua.nure.hartseva.SummaryTask4.service.IUserStatusService;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

public class ChangeUserStatusCommand extends Command{

	private static final long serialVersionUID = 6917727668687069200L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ServletContext context = request.getServletContext();
		IUserService userService = (IUserService) context.getAttribute(Attributes.USER_SERVICE);
		IUserStatusService userStatusService = (IUserStatusService) context.getAttribute(Attributes.USER_STATUS_SERVICE);
		
		String newStatus = request.getParameter(Attributes.STATUS).toUpperCase();
		int userId = Integer.parseInt(request.getParameter(Attributes.USER_ID));
		
		userService.changeUserStatus(userId, newStatus);
		userStatusService.changeUserStatus(userId, Status.valueOf(newStatus));
		
		return Transfer.redirect(response, Path.COMMAND_USERS);
	}

}
