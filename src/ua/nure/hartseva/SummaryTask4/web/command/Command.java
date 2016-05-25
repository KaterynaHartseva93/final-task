package ua.nure.hartseva.SummaryTask4.web.command;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.web.Transfer;


public abstract class Command implements Serializable {
	
	private static final long serialVersionUID = 8879403039606311780L;

	/**
	 * Execution method for command.
	 * 
	 * @return Address to go once the command is executed.
	 */
	public abstract Transfer execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException;

	public boolean accept(Role role) {
		return allowedRoles().contains(role);
	}
	
	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}
	
	protected List<Role> allowedRoles() {
		return Collections.singletonList(Role.ADMIN);
	}
}