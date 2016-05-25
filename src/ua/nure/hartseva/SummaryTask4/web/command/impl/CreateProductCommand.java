package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Parameters;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.entity.Brand;
import ua.nure.hartseva.SummaryTask4.entity.Category;
import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.entity.Size;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;
import ua.nure.hartseva.SummaryTask4.entity.product.ProductInfo;
import ua.nure.hartseva.SummaryTask4.exception.WebBadRequestException;
import ua.nure.hartseva.SummaryTask4.service.IFileService;
import ua.nure.hartseva.SummaryTask4.service.IProductService;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

public class CreateProductCommand extends Command {

	private static final Logger LOG = Logger.getLogger(CreateProductCommand.class);

	private static final long serialVersionUID = -123587732809824694L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();

		ServletContext servletContext = request.getServletContext();
		IProductService productService = (IProductService) servletContext.getAttribute(Attributes.PRODUCT_SERVICE);

		String productName = getProductName(session, request);
		String productCategory = getProductCategory(session, request);
		String productBrand = getProductBrand(session, request);
		double productPrice = getPrice(session, request);
		List<Size> sizes = getSizes(request);
		String productImageIdentifier = getProductImageIdentifier(request);

		Product product = new Product();
		product.setAvailableSizes(new LinkedHashSet<Size>(sizes));
		
		product.setBrand(new Brand(0, productBrand));
		product.setCategory(new Category(0, productCategory));
		product.setName(productName);
		product.setPrice(productPrice);
		
		ProductInfo productInfo = new ProductInfo();
		productInfo.setCreationDate(new Date());
		productInfo.setImageIdentifier(productImageIdentifier);
		productInfo.setSellCount(0);
		product.setInfo(productInfo);
		
		productService.create(product);
		return Transfer.redirect(response, Path.COMMAND_CLOTHES);
	}

	@Override
	protected List<Role> allowedRoles() {
		return Collections.singletonList(Role.ADMIN);
	}

	private String getProductName(HttpSession session, HttpServletRequest request) {
		String productName = request.getParameter(Attributes.PRODUCT_NAME);
		LOG.info("Product name --> " + productName);
		if (productName == null || productName.isEmpty()) {
			session.setAttribute(Attributes.PAGE_ERROR_MESSAGE, "Product name cannot be empty.");
			throw new WebBadRequestException("Product name cannot be empty.");
		}
		return productName;
	}

	private String getProductCategory(HttpSession session, HttpServletRequest request) {
		String productCategory = request.getParameter(Attributes.PRODUCT_CATEGORY);
		if (productCategory == null || productCategory.isEmpty()) {
			session.setAttribute(Attributes.PAGE_ERROR_MESSAGE, "Product category cannot be empty.");
			throw new WebBadRequestException("Product category cannot be empty.");
		}
		return productCategory;
	}

	private String getProductBrand(HttpSession session, HttpServletRequest request) {
		String productBrand = request.getParameter(Attributes.PRODUCT_BRAND);
		if (productBrand == null || productBrand.isEmpty()) {
			session.setAttribute(Attributes.PAGE_ERROR_MESSAGE, "Product brand cannot be empty.");
			throw new WebBadRequestException("Product brand cannot be empty.");
		}
		return productBrand;
	}

	private double getPrice(HttpSession session, HttpServletRequest request) {
		String priceString = request.getParameter(Attributes.PRODUCT_PRICE);
		if (priceString == null || priceString.isEmpty()) {
			session.setAttribute(Attributes.PAGE_ERROR_MESSAGE, "Product price cannot be empty.");
			throw new WebBadRequestException("Product price cannot be empty.");
		}

		try {
			return Double.parseDouble(priceString);
		} catch (NumberFormatException e) {
			session.setAttribute(Attributes.PAGE_ERROR_MESSAGE, "Product price should be a number.");
			throw new WebBadRequestException("Product price should be a number.", e);
		}
	}

	private List<Size> getSizes(HttpServletRequest request) {
		String[] sizesArray = request.getParameterValues(Attributes.PRODUCT_SIZE);
		List<Size> sizes = new ArrayList<>();
		if (sizesArray != null && sizesArray.length != 0) {
			for (String sizeString : sizesArray) {
				Size size = Size.valueOf(sizeString);
				sizes.add(size);
			}
		}
		return sizes;
	}

	private String getProductImageIdentifier(HttpServletRequest request) {
		IFileService fileService = (IFileService) request.getServletContext().getAttribute(Attributes.IMAGES_SERVICE);
		String productImageIdentifier = null;
		Part part;
		try {
			part = request.getPart(Parameters.PRODUCT_IMAGE);
			if (part != null && part.getSize() != 0) {
				productImageIdentifier = fileService.saveFile(part);
			}
		} catch (Exception e) {
			LOG.error("Failed to get image part.", e);
		} 
		return productImageIdentifier;
	}
}
