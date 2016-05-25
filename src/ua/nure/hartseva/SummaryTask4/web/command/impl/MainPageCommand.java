package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;
import ua.nure.hartseva.SummaryTask4.service.IProductService;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

public class MainPageCommand extends Command {

	private static final long serialVersionUID = 6489532500942033054L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		ServletContext context = request.getServletContext();
		IProductService productService = (IProductService) context.getAttribute(Attributes.PRODUCT_SERVICE);
		
		List<Product> newest = productService.findNewest(3);
		List<Product> popular = productService.findPopular(3);
		
		request.setAttribute(Attributes.NEWEST, newest);
		request.setAttribute(Attributes.POPULAR, popular);
		return Transfer.forward(request, response, Path.PAGE_MAIN);
	}

	@Override
	protected List<Role> allowedRoles() {
		return Arrays.asList(Role.GUEST, Role.CLIENT);
	}
}
