package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.bean.ProductUpdateCriteria;
import ua.nure.hartseva.SummaryTask4.entity.Size;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;
import ua.nure.hartseva.SummaryTask4.service.IProductService;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

/**
 * 
 * Command is available only for administrator.
 * 
 * @author Kateryna Hartseva
 *
 */
public class EditProductCommand extends Command {

	private static final long serialVersionUID = -405399084703271909L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		
		ServletContext servletContext = request.getServletContext();
		IProductService productService = (IProductService) servletContext.getAttribute(Attributes.PRODUCT_SERVICE);
		
		String productId = request.getParameter(Attributes.ID_PRODUCT);
		String productPrice = request.getParameter(Attributes.PRODUCT_PRICE);
		
		String[] sizes = request.getParameterValues(Attributes.PRODUCT_SIZE_RADIO);
		List<Size> sizesList = getSizesList(sizes);
		
		Product product = productService.getProductById(Integer.parseInt(productId));
		if (product == null) {
			session.setAttribute(Attributes.PAGE_ERROR_MESSAGE, "Such product not found.");
			return Transfer.redirect(response, Path.COMMAND_CLOTHES);
		}
		
		ProductUpdateCriteria criteria = new ProductUpdateCriteria();
		criteria.setPrice(Double.parseDouble(productPrice));
		criteria.setSizesList(sizesList);

		productService.update(product, criteria);
		return Transfer.redirect(response, Path.COMMAND_CLOTHES);
	}

	private List<Size> getSizesList(String[] sizes) {
		List<Size> sizesList = new ArrayList<>();
		if (sizes == null || sizes.length == 0) {
			return sizesList;
		}
		for (String size : sizes) {
			sizesList.add(Size.valueOf(size));
		}
		return sizesList;
	}
}
