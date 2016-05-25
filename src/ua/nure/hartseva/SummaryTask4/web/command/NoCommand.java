package ua.nure.hartseva.SummaryTask4.web.command;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.exception.WebNotFoundException;
import ua.nure.hartseva.SummaryTask4.web.Transfer;

public class NoCommand extends Command {

	private static final long serialVersionUID = -2785976616686657267L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response) {
		// Just throw and exception
		throw new WebNotFoundException("Command not found");
	}

	@Override
	protected List<Role> allowedRoles() {
		return Arrays.asList(Role.GUEST, Role.ADMIN, Role.CLIENT);
	}
}