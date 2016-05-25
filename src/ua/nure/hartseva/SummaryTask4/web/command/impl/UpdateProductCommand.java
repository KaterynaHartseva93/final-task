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

public class UpdateProductCommand extends Command {

	private static final long serialVersionUID = 8367194381698544238L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();

		ServletContext servletContext = request.getServletContext();
		IProductService productService = (IProductService) servletContext.getAttribute(Attributes.PRODUCT_SERVICE);

		String productId = request.getParameter("product-identifier");
		String productName = request.getParameter("product-name");
		String productPrice = request.getParameter("product-price");
		String productCategory = request.getParameter("product-category");
		String productBrand = request.getParameter("product-brand");
		
		String[] sizes = request.getParameterValues("product-size-radio");
		List<Size> sizesList = getSizesList(sizes);

		Product product = productService.getProductById(Integer.parseInt(productId));
		if (product == null) {
			session.setAttribute(Attributes.PAGE_ERROR_MESSAGE, "Such product not found.");
			return Transfer.redirect(response, Path.COMMAND_CLOTHES);
		}

		ProductUpdateCriteria criteria = new ProductUpdateCriteria();
		criteria.setBrandName(productBrand);
		criteria.setCategoryName(productCategory);
		criteria.setName(productName);
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
