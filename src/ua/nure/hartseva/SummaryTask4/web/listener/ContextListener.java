package ua.nure.hartseva.SummaryTask4.web.listener;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Parameters;
import ua.nure.hartseva.SummaryTask4.SAXController;
import ua.nure.hartseva.SummaryTask4.bean.Presentation;
import ua.nure.hartseva.SummaryTask4.dao.IBrandDao;
import ua.nure.hartseva.SummaryTask4.dao.ICategoryDao;
import ua.nure.hartseva.SummaryTask4.dao.IOrderDao;
import ua.nure.hartseva.SummaryTask4.dao.IOrderUnitDao;
import ua.nure.hartseva.SummaryTask4.dao.IProductDao;
import ua.nure.hartseva.SummaryTask4.dao.IProductInfoDao;
import ua.nure.hartseva.SummaryTask4.dao.IProductSizeDao;
import ua.nure.hartseva.SummaryTask4.dao.IUserStatusDao;
import ua.nure.hartseva.SummaryTask4.dao.IUsersDao;
import ua.nure.hartseva.SummaryTask4.dao.impl.InMemoryUserStatusDao;
import ua.nure.hartseva.SummaryTask4.dao.impl.MySqlBrandDao;
import ua.nure.hartseva.SummaryTask4.dao.impl.MySqlCategoryDao;
import ua.nure.hartseva.SummaryTask4.dao.impl.MySqlOrderDao;
import ua.nure.hartseva.SummaryTask4.dao.impl.MySqlOrderUnitDao;
import ua.nure.hartseva.SummaryTask4.dao.impl.MySqlProductDao;
import ua.nure.hartseva.SummaryTask4.dao.impl.MySqlProductInfoDao;
import ua.nure.hartseva.SummaryTask4.dao.impl.MySqlProductSizeDao;
import ua.nure.hartseva.SummaryTask4.dao.impl.MySqlUsersDao;
import ua.nure.hartseva.SummaryTask4.exception.ContextInitializationFailedException;
import ua.nure.hartseva.SummaryTask4.service.IBrandService;
import ua.nure.hartseva.SummaryTask4.service.ICategoryService;
import ua.nure.hartseva.SummaryTask4.service.IConnectionProvider;
import ua.nure.hartseva.SummaryTask4.service.IFileService;
import ua.nure.hartseva.SummaryTask4.service.IOrderService;
import ua.nure.hartseva.SummaryTask4.service.IProductService;
import ua.nure.hartseva.SummaryTask4.service.ITransactionProvider;
import ua.nure.hartseva.SummaryTask4.service.IUserService;
import ua.nure.hartseva.SummaryTask4.service.IUserStatusService;
import ua.nure.hartseva.SummaryTask4.service.impl.BrandService;
import ua.nure.hartseva.SummaryTask4.service.impl.CategoryService;
import ua.nure.hartseva.SummaryTask4.service.impl.ConnectionProvider;
import ua.nure.hartseva.SummaryTask4.service.impl.ImagesService;
import ua.nure.hartseva.SummaryTask4.service.impl.OrderService;
import ua.nure.hartseva.SummaryTask4.service.impl.ProductService;
import ua.nure.hartseva.SummaryTask4.service.impl.TransactionProvider;
import ua.nure.hartseva.SummaryTask4.service.impl.UserService;
import ua.nure.hartseva.SummaryTask4.service.impl.UserStatusService;

public class ContextListener implements ServletContextListener {

	private static final String ACCESS_XML_PATH = "/WEB-INF/access/access.xml";
	private static final String CONTEXT_ACCESS_KEY = "access";

	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		log("Servlet context destruction starts");
		// no op
		log("Servlet context destruction finished");
	}

	public void contextInitialized(ServletContextEvent event) {
		log("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		initLog4J(servletContext);
		initAccesForUsers(servletContext);
		initializeServices(servletContext);
		initCommandContainer();

		log("Servlet context initialization finished");
	}

	/**
	 * Initializes log4j framework.
	 * 
	 * @param servletContext
	 */
	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
			LOG.debug("Log4j has been initialized");
		} catch (Exception ex) {
			log("Cannot configure Log4j");
			LOG.error(ex.getMessage());
		}
		log("Log4J initialization finished");
	}

	private void initAccesForUsers(ServletContext servletContext) {
		SAXController accessController = new SAXController();
		InputStream stream = servletContext.getResourceAsStream(ACCESS_XML_PATH);

		try {
			accessController.parse(stream, true);
			Presentation presentation = accessController.getPresentation();

			// Add access information to servlet context as attribute
			servletContext.setAttribute(CONTEXT_ACCESS_KEY, presentation);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new ContextInitializationFailedException("Failed to get pages presentation.", e);
		}
	}

	private void initializeServices(ServletContext servletContext) {

		// INIT PARAMETERS
		String imagesPath = servletContext.getInitParameter(Parameters.PRODUCT_IMAGES_DIR);
		
		// CONNECTION PROVIDER
		IConnectionProvider connectionProvider = new ConnectionProvider();
		
		// TRANSACTION PROVIDER
		ITransactionProvider transactionProvider = new TransactionProvider(connectionProvider);
		
		// DAO
		IUsersDao usersDao = new MySqlUsersDao(connectionProvider);
		IUserStatusDao userStatusDao = new InMemoryUserStatusDao(usersDao);
		IProductDao productDao = new MySqlProductDao(connectionProvider);
		IProductSizeDao productSizeDao = new MySqlProductSizeDao(connectionProvider);
		IProductInfoDao productInfoDao = new MySqlProductInfoDao(connectionProvider);
		IOrderDao orderDao = new MySqlOrderDao(connectionProvider);
		IOrderUnitDao orderUnitDao = new MySqlOrderUnitDao(connectionProvider);
		IBrandDao brandDao = new MySqlBrandDao(connectionProvider);
		ICategoryDao categoryDao = new MySqlCategoryDao(connectionProvider);
		
		// SERVICES
		IUserStatusService userStatusService = new UserStatusService(userStatusDao);
		IBrandService brandService = new BrandService(transactionProvider, brandDao);
		ICategoryService categoryService = new CategoryService(transactionProvider, categoryDao);
		IProductService productService = new ProductService(transactionProvider, productDao, productSizeDao, productInfoDao, brandDao, categoryDao);
		IOrderService orderService = new OrderService(transactionProvider, orderDao, orderUnitDao);
		IUserService usersService = new UserService(transactionProvider, usersDao);
		IFileService imagesService = new ImagesService(imagesPath);

		servletContext.setAttribute(Attributes.USER_SERVICE, usersService);
		servletContext.setAttribute(Attributes.PRODUCT_SERVICE, productService);
		servletContext.setAttribute(Attributes.USER_STATUS_SERVICE, userStatusService);
		servletContext.setAttribute(Attributes.BRAND_SERVICE, brandService);
		servletContext.setAttribute(Attributes.CATEGORY_SERVICE, categoryService);
		servletContext.setAttribute(Attributes.ORDER_SERVICE, orderService);
		servletContext.setAttribute(Attributes.IMAGES_SERVICE, imagesService);
	}

	/**
	 * Initializes CommandContainer.
	 * 
	 * @param servletContext
	 */
	private void initCommandContainer() {

		// initialize commands container
		// just load class to JVM
		try {
			Class.forName("ua.nure.hartseva.SummaryTask4.web.command.impl.CommandContainer");
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Cannot initialize Command Container");
		}
	}

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}
}