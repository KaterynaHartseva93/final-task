package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.bean.OrderName;
import ua.nure.hartseva.SummaryTask4.bean.OrderType;
import ua.nure.hartseva.SummaryTask4.bean.ProductSearchCriteria;
import ua.nure.hartseva.SummaryTask4.bean.SortingParameter;
import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;
import ua.nure.hartseva.SummaryTask4.exception.WebBadRequestException;
import ua.nure.hartseva.SummaryTask4.service.IBrandService;
import ua.nure.hartseva.SummaryTask4.service.ICategoryService;
import ua.nure.hartseva.SummaryTask4.service.IProductService;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;

public class ClothesCommand extends Command {

	private static final long serialVersionUID = 3866907015019511713L;
	private static final String CHOSEN_BRANDS = "chosenBrands";
	private static final String CHOSEN_CATEGORY = "chosenCategory";
	private static final String CHOSEN_ORDER_NAME = "chosenOrderName";
	private static final String CHOSEN_ORDER_TYPE = "chosenOrderType";
	private static final String CHOSEN_MIN_PRICE = "chosenMinPrice";
	private static final String CHOSEN_MAX_PRICE = "chosenMaxPrice";
	private static final String CHOSEN_NAME_PART= "chosenNamePart";
	private static final String PAGES_COUNT = "pagesCount";

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ServletContext context = request.getServletContext();
		IProductService productService = (IProductService) context.getAttribute(Attributes.PRODUCT_SERVICE);
		IBrandService brandService = (IBrandService) context.getAttribute(Attributes.BRAND_SERVICE);
		ICategoryService categoryService = (ICategoryService) context.getAttribute(Attributes.CATEGORY_SERVICE);

		ProductSearchCriteria criteria = getCriteriaFromRequest(request);
		
		List<Product> products = productService.findByCriteria(criteria);
		int productsCount = productService.getProductsCount(criteria);
		int pagesCount = getPagesCount(productsCount);
		
		request.setAttribute(Attributes.CLOTHES, products);
		request.setAttribute(Attributes.BRANDS, brandService.getAllBrands());
		request.setAttribute(Attributes.CATEGORIES, categoryService.getAllCategories());
		request.setAttribute(CHOSEN_BRANDS, getBrandIds(request.getParameterValues(Attributes.BRAND)));
		request.setAttribute(CHOSEN_CATEGORY, getCategoryId(request.getParameter(Attributes.CATEGORY)));
		request.setAttribute(CHOSEN_ORDER_NAME, request.getParameter(Attributes.ORDER_BY));
		request.setAttribute(CHOSEN_ORDER_TYPE, request.getParameter(Attributes.TYPE));
		request.setAttribute(CHOSEN_MIN_PRICE, (criteria.getMinPrice() != 0 ? criteria.getMinPrice() : null));
		request.setAttribute(CHOSEN_MAX_PRICE, (criteria.getMaxPrice() != 0 ? criteria.getMaxPrice() : null));
		request.setAttribute(CHOSEN_NAME_PART, (criteria.getProductNamePart() != null ? criteria.getProductNamePart() : null));
		request.setAttribute(PAGES_COUNT, pagesCount);
		return Transfer.forward(request, response, Path.PAGE_CLOTHES);
	}

	private int getPagesCount(int productsCount) {
		if (productsCount % ProductSearchCriteria.PRODUCTS_LIMIT_ON_PAGE == 0) {
			return productsCount / ProductSearchCriteria.PRODUCTS_LIMIT_ON_PAGE;
		}
		return productsCount / ProductSearchCriteria.PRODUCTS_LIMIT_ON_PAGE + 1;
	}

	@Override
	protected List<Role> allowedRoles() {
		return Arrays.asList(Role.GUEST, Role.CLIENT, Role.ADMIN);
	}

	private ProductSearchCriteria getCriteriaFromRequest(HttpServletRequest request) {
		ProductSearchCriteria criteria = new ProductSearchCriteria();
		String page = request.getParameter(Attributes.PAGE);
		String categoryId = request.getParameter(Attributes.CATEGORY);
		String[] brandsId = request.getParameterValues(Attributes.BRAND);
		String minPrice = request.getParameter(Attributes.MIN_PRICE);
		String maxPrice = request.getParameter(Attributes.MAX_PRICE);
		String orderName = request.getParameter(Attributes.ORDER_BY);
		String orderType = request.getParameter(Attributes.TYPE);
		String namePart = request.getParameter(Attributes.NAME_PART);
		if (page != null) {
			try {
				criteria.setPageNumber(Integer.parseInt(page));
			} catch (NumberFormatException ex) {
				criteria.setPageNumber(1);
			}
		}
		if (orderName != null && orderType != null && !(orderName.length() == 0) && !(orderType.length() == 0)) {
			SortingParameter parameters = getSortingParameterObject(orderName, orderType);
			criteria.setSortingParameters(parameters);
		}
		if (namePart != null) {
			criteria.setProductNamePart(namePart);
		}
		try {
			int id = getCategoryId(categoryId);
			if (id != 0) {
				criteria.setCategoryId(id);
			}
			List<Integer> brandIds = getBrandIds(brandsId);
			if (brandIds != null) {
				criteria.setBrandIds(brandIds);
			}
			if (minPrice != null && minPrice.length() != 0) {
				criteria.setMinPrice(Integer.parseInt(minPrice));
			}
			if (maxPrice != null && maxPrice.length() != 0) {
				criteria.setMaxPrice(Integer.parseInt(maxPrice));
			}
		} catch (NumberFormatException ex) {
			throw new WebBadRequestException("Invalid search parameters", ex);
		}
		return criteria;
	}

	private List<Integer> getBrandIds(String[] brandsArray) {
		if (brandsArray == null || brandsArray.length == 0) {
			return null;
		}
		List<Integer> brandIds = new ArrayList<>();
		for (String brand : brandsArray) {
			brandIds.add(Integer.parseInt(brand));
		}
		return brandIds;
	}

	private int getCategoryId(String categoryId) {
		if (categoryId != null && categoryId.length() != 0) {
			return Integer.parseInt(categoryId);
		}
		return 0;
	}
	
	private SortingParameter getSortingParameterObject(String orderName, String orderType) {
		OrderName orderNameValue = OrderName.fromValue(orderName);
		OrderType orderTypeValue = OrderType.fromValue(orderType);
		return new SortingParameter(orderNameValue, orderTypeValue);
	}
}
