package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.entity.Order;
import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.entity.User;
import ua.nure.hartseva.SummaryTask4.service.IOrderService;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

public class OrdersCommand extends Command {

	private static final long serialVersionUID = -377054456710059497L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		ServletContext servletContext = request.getServletContext();
		IOrderService orderService = (IOrderService) servletContext.getAttribute(Attributes.ORDER_SERVICE);

		List<Order> orders;
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Attributes.USER);
		if (user.getRole() == Role.ADMIN) {
			orders = orderService.getAll();
		} else {
			orders = orderService.getAllByUserEmail(user.getEmail().toLowerCase());
		}
		request.setAttribute(Attributes.ORDERS, orders);
		return Transfer.forward(request, response, Path.PAGE_ORDERS);
	}

	@Override
	protected List<Role> allowedRoles() {
		return Arrays.asList(Role.CLIENT, Role.ADMIN);
	}
}
