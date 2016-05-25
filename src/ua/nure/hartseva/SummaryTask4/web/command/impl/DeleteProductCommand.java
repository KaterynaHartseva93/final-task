package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;
import ua.nure.hartseva.SummaryTask4.service.IProductService;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

public class DeleteProductCommand extends Command{

	private static final long serialVersionUID = 5379907624654613403L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
        HttpSession session = request.getSession();
		
		ServletContext servletContext = request.getServletContext();
		IProductService productService = (IProductService) servletContext.getAttribute(Attributes.PRODUCT_SERVICE);
		String productId = request.getParameter(Attributes.ID_PRODUCT);
		
		Product product = productService.getProductById(Integer.parseInt(productId));
		if (product == null) {
			session.setAttribute(Attributes.PAGE_ERROR_MESSAGE, "Such product not found.");
			return Transfer.redirect(response, Path.COMMAND_CLOTHES);
		}
		productService.delete(Integer.parseInt(productId));
		return Transfer.redirect(response, Path.COMMAND_CLOTHES);
	}
}
