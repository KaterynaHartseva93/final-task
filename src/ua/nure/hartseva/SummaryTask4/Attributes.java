package ua.nure.hartseva.SummaryTask4;

/**
 * List of application attributes
 * 
 * @author Kateryna Hartseva
 *
 */
public interface Attributes {

	String USER = "user";
	String USERS = "users";
	String NEWEST = "newest";
	String POPULAR = "popular";
	String ERROR_MESSAGE = "errorMessage";
	String ACCESS = "access";
	String CART = "cart";
	String NAME = "name";
	String OWNER_EMAIL = "owner_email";
	String STATUS = "status";
	String CREATION_DATE = "creation_timestamp";
	String SELL_COUNT = "sell_count";
	String IMAGE_IDENTIFIER = "image_identifier";
	String SIZE = "size";
	String ACTION = "action";
	String USER_ID = "userId";
	String BRAND = "brand";
	String CATEGORY = "category";
	String CLOTHES = "clothes";
	String PAGE = "page";
	String ORDERS = "orders";
	String PRODUCT_SIZE_RADIO = "product-size-radio";
	
	//User attributes
	String ROLE = "role";
	String GENDER = "gender";
	String FIRST_NAME = "first_name";
	String LAST_NAME = "last_name";
	String EMAIL = "email";
	String BRANDS = "brands";
	String CATEGORIES = "categories";
	
	//Criteria attributes
	String PRICE = "price";
	String NAME_PART = "namePart";
	String ORDER_BY = "orderBy";
	String TYPE = "type";
	String MIN_PRICE = "minPrice";
	String MAX_PRICE = "maxPrice";

	// id
	String ID = "id";
	String ID_ORDER = "id_order";
	String ID_BRAND = "id_brand";
	String ID_CATEGORY = "id_category";

	// Product attributes
	String ID_PRODUCT = "id_product";
	String PRODUCT_NAME = "product_name";
	String PRODUCT_BRAND = "product_brand";
	String PRODUCT_CATEGORY = "product_category";
	String PRODUCT_PRICE = "product_price";
	String PRODUCT_COUNT = "product_count";
	String PRODUCT_SIZE = "product_size";

	// Services
	String USER_SERVICE = "userService";
	String PRODUCT_SERVICE = "productService";
	String IMAGES_SERVICE = "imagesService";
	String PRODUCT_SIZES_SERVICE = "productSizesService";
	String USER_STATUS_SERVICE = "userStatusService";
	String BRAND_SERVICE = "brandService";
	String CATEGORY_SERVICE = "categoryService";
	String ORDER_SERVICE = "orderService";

	// Messages
	String PAGE_ERROR_MESSAGE = "pageErrorMessage";
	String PAGE_SUCCESS_MESSAGE = "pageSuccessMessage";
}
