package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.entity.OrderUnit;
import ua.nure.hartseva.SummaryTask4.entity.Size;
import ua.nure.hartseva.SummaryTask4.service.IOrderService;
import ua.nure.hartseva.SummaryTask4.service.IProductService;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

public class ChangeOrderStatusCommand extends Command {

	private static final long serialVersionUID = -8790916325012086270L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession();
		IOrderService orderService = (IOrderService) context.getAttribute(Attributes.ORDER_SERVICE);
		IProductService productService = (IProductService) context.getAttribute(Attributes.PRODUCT_SERVICE);
		int orderId = Integer.parseInt(request.getParameter(Attributes.ID_ORDER));

		List<OrderUnit> orderUnitsList = orderService.getOrderUnits(orderId);
		String newStatus = request.getParameter(Attributes.STATUS).toUpperCase();
		if (newStatus.equals("PAID")) {
			for (OrderUnit orderUnit : orderUnitsList) {
				int productId = orderUnit.getProductId();
				Size size = orderUnit.getProductSize();
				if (!productService.doesProductExist(productId, size.toString())) {
					session.setAttribute(Attributes.PAGE_ERROR_MESSAGE,
							orderUnit.getProductName() + " with size " + size + " was not found.");
					return Transfer.redirect(response, Path.COMMAND_ORDERS);
				}
			}
			for (OrderUnit orderUnit : orderUnitsList) {
				productService.updateSellCount(orderUnit.getProductId());
			}
		}
		orderService.changeOrderStatus(orderId, newStatus);
		session.setAttribute(Attributes.PAGE_SUCCESS_MESSAGE, "Order status successfully changed");
		return Transfer.redirect(response, Path.COMMAND_ORDERS);
	}

}
