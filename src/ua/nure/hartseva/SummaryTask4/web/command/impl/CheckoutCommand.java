package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

public class CheckoutCommand extends Command{

	private static final long serialVersionUID = -668263595739970367L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		return Transfer.forward(request, response, Path.PAGE_CHECKOUT);
	}
	
	@Override
	protected List<Role> allowedRoles() {
		return Arrays.asList(Role.CLIENT);
	}
}
