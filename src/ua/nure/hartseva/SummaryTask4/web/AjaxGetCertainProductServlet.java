package ua.nure.hartseva.SummaryTask4.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.entity.Size;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;
import ua.nure.hartseva.SummaryTask4.entity.product.ProductInfo;
import ua.nure.hartseva.SummaryTask4.service.IProductService;

public class AjaxGetCertainProductServlet extends HttpServlet {

	private static final long serialVersionUID = -2091697571661810064L;

	private static final String JSON_CONTENT_TYPE = "application/json";
	private static final String ENCODING = "UTF-8";
	private static final Logger LOG = Logger.getLogger(AjaxGetCertainProductServlet.class);

	private IProductService productService;

	@Override
	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		this.productService = (IProductService) servletContext.getAttribute(Attributes.PRODUCT_SERVICE);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String productId = request.getParameter(Attributes.ID_PRODUCT);

		Product product = productService.getProductById(Integer.parseInt(productId));
		String json = getJsonObject(product);
		LOG.info(json);

		response.setContentType(JSON_CONTENT_TYPE);
		response.setCharacterEncoding(ENCODING);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}

	private String getJsonObject(Product product) {
		String imageIdentifier = null;
		ProductInfo productInfo = product.getInfo();
		if (productInfo != null) {
			imageIdentifier = productInfo.getImageIdentifier();
		}
		return "{\"category\": \"" + product.getCategory().getName() + "\", \"brand\": \""
				+ product.getBrand().getName() + "\", \"name\": \"" + product.getName() + "\", \"price\": \""
				+ product.getPrice() + "\", \"imageIdentifier\": \"" + imageIdentifier + "\", \"sizes\": [" + getSizes(product) + "]}";
	}

	private String getSizes(Product product) {
		if (product.getAvailableSizes() == null || product.getAvailableSizes().isEmpty()) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		String separator = "";
		for (Size size : product.getAvailableSizes()) {
			builder.append(separator).append("\"").append(size.name()).append("\"");
			separator = ", ";
		}
		return builder.toString();
	}
}
