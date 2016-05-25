package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.bean.PageView;
import ua.nure.hartseva.SummaryTask4.bean.Presentation;
import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.entity.Status;
import ua.nure.hartseva.SummaryTask4.entity.User;
import ua.nure.hartseva.SummaryTask4.service.IUserService;
import ua.nure.hartseva.SummaryTask4.util.PasswordEncoder;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

public class SignInCommand extends Command {

	private static final long serialVersionUID = 2627850056516044882L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ServletContext context = request.getServletContext();
		IUserService userService = (IUserService) context.getAttribute("userService");
		Presentation presentation = (Presentation) context.getAttribute(Attributes.ACCESS);

		String email = request.getParameter("email");
		if (email == null) {
			throw new IllegalArgumentException("Email is empty.");
		}

		String password = request.getParameter("password");
		if (password == null) {
			throw new IllegalArgumentException("Password is empty.");
		}

		String encodedPassword = PasswordEncoder.encode(password);

		HttpSession session = request.getSession();

		User user = userService.getUser(email, encodedPassword);
		if (user == null) {
			session.setAttribute(Attributes.ERROR_MESSAGE, "You are not registered.");
			return Transfer.redirect(response, Path.COMMAND_MAIN);
		}

		if (user.getStatus().is(Status.BLOCKED)) {
			session.setAttribute(Attributes.ERROR_MESSAGE, "Sorry, your account is blocked.");
			return Transfer.redirect(response, Path.COMMAND_MAIN);
		}

		session.setAttribute(Attributes.USER, user);
		PageView pageView = presentation.getByRole(user.getRole());
		return Transfer.redirect(response, pageView.getHomePage());
	}

	@Override
	protected List<Role> allowedRoles() {
		return Collections.singletonList(Role.GUEST);
	}
}
