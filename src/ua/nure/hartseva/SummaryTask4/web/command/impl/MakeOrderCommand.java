		package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.bean.Cart;
import ua.nure.hartseva.SummaryTask4.bean.ProductItem;
import ua.nure.hartseva.SummaryTask4.entity.Order;
import ua.nure.hartseva.SummaryTask4.entity.OrderUnit;
import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.entity.Size;
import ua.nure.hartseva.SummaryTask4.entity.User;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;
import ua.nure.hartseva.SummaryTask4.service.IOrderService;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

public class MakeOrderCommand extends Command {

	private static final long serialVersionUID = -2214102743222324987L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ServletContext servletContext = request.getServletContext();
		IOrderService orderService = (IOrderService) servletContext.getAttribute(Attributes.ORDER_SERVICE);
		
		Transfer transfer = Transfer.redirect(response, Path.COMMAND_ORDERS);
		
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute(Attributes.CART);
		if (cart == null || cart.isEmpty()) {
			session.setAttribute(Attributes.PAGE_ERROR_MESSAGE, "Your cart is empty.");
			return Transfer.redirect(response, Path.COMMAND_MAIN);
		}

		User user = (User) session.getAttribute(Attributes.USER);
		Order order = getOrderFromCart(user, cart);
		orderService.create(order);
		
		session.removeAttribute(Attributes.CART);
		session.setAttribute(Attributes.PAGE_SUCCESS_MESSAGE, "Your order successfully created.");
		return transfer;
	}

	@Override
	protected List<Role> allowedRoles() {
		return Collections.singletonList(Role.CLIENT);
	}
	
	private Order getOrderFromCart(User user, Cart cart) {
		Order order = new Order();
		order.setDate(new Date());
		order.setOwnerEmail(user.getEmail());
		
		List<OrderUnit> orderUnits = getOrderUnits(cart);
		order.setOrderUnitList(orderUnits);
		return order;
	}

	private List<OrderUnit> getOrderUnits(Cart cart) {
		List<OrderUnit> orderUnits = new ArrayList<>();
		Map<ProductItem, Integer> productItemsMap = cart.getItems();
		for (Map.Entry<ProductItem, Integer> entry : productItemsMap.entrySet()) {
			ProductItem productItem = entry.getKey();
			
			Product product = productItem.getProduct();
			Size size = productItem.getSize();
			
			Integer count = entry.getValue();
			OrderUnit orderUnit = new OrderUnit();
			orderUnit.setProductId(product.getId());
			orderUnit.setCount(count);
			orderUnit.setProductBrand(product.getBrand().getName());
			orderUnit.setProductCategory(product.getCategory().getName());
			orderUnit.setProductSize(size);
			orderUnit.setProductName(product.getName());
			orderUnit.setPrice(product.getPrice());
			orderUnits.add(orderUnit);
		}
		return orderUnits;
	}
}
