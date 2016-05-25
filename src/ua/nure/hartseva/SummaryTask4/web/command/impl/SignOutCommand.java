package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

public class SignOutCommand extends Command {

	private static final long serialVersionUID = -8276426075742100871L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		session.removeAttribute(Attributes.USER);
		session.removeAttribute(Attributes.CART);
		return Transfer.redirect(response, Path.COMMAND_MAIN);
	}
	
	@Override
	protected List<Role> allowedRoles() {
		return Arrays.asList(Role.ADMIN, Role.CLIENT);
	}
}
