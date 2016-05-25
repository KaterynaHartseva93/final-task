package ua.nure.hartseva.SummaryTask4.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.bean.Cart;
import ua.nure.hartseva.SummaryTask4.bean.ProductItem;
import ua.nure.hartseva.SummaryTask4.entity.Size;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;
import ua.nure.hartseva.SummaryTask4.exception.WebNotFoundException;
import ua.nure.hartseva.SummaryTask4.service.IProductService;

public class AjaxChangeCartServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(AjaxChangeCartServlet.class);

	private static final long serialVersionUID = -4463299224818282997L;

	private static final String JSON_CONTENT_TYPE = "application/json";

	private IProductService productService;

	private static final String DELETE = "delete";
	private static final String INCREMENT = "increment";
	private static final String DECREMENT = "decrement";

	@Override
	public void init() throws ServletException {
		this.productService = (IProductService) getServletContext().getAttribute(Attributes.PRODUCT_SERVICE);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String productId = request.getParameter(Attributes.ID_PRODUCT);
		String sizeString = request.getParameter(Attributes.SIZE);
		String action = request.getParameter(Attributes.ACTION);

		Product product = productService.getProductById(Integer.parseInt(productId));
		Size size = Size.valueOf(sizeString);
		ProductItem productItem = new ProductItem(product, size);

		if (product == null) {
			throw new WebNotFoundException("product not found");
		}

		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute(Attributes.CART);

		if (action.equals(INCREMENT)) {
			cart.incrementCount(productItem);
		}
		if (action.equals(DECREMENT)) {
			cart.decrementCount(productItem);
		}
		if (action.equals(DELETE)) {
			cart.deletePosition(productItem);
		}
		session.setAttribute(Attributes.CART, cart);
		response.setContentType(JSON_CONTENT_TYPE);
		String json = getJsonObject(cart);
		LOG.info(json);

		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}

	private String getJsonObject(Cart cart) {
		return "{\"count\": \"" + cart.getCount() + "\", \"totalPrice\": \"" + cart.getTotalPrice() + "\"}";
	}
}
