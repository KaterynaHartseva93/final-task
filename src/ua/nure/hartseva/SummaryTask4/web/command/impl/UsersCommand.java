package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.entity.User;
import ua.nure.hartseva.SummaryTask4.service.IUserService;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

public class UsersCommand extends Command {

	private static final long serialVersionUID = -7663926288539458111L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		ServletContext context = request.getServletContext();
		IUserService usersService = (IUserService) context.getAttribute(Attributes.USER_SERVICE);

		request.setAttribute(Attributes.USERS, usersService.getAllUsersExceptAdmin(((User) session.getAttribute(Attributes.USER)).getId()));
		return Transfer.forward(request, response, Path.PAGE_USERS);
	}
}
