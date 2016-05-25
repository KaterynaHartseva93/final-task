package ua.nure.hartseva.SummaryTask4.service;

import java.util.List;

import ua.nure.hartseva.SummaryTask4.bean.ProductSearchCriteria;
import ua.nure.hartseva.SummaryTask4.bean.ProductUpdateCriteria;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;

public interface IProductService {

	List<Product> findNewest(int count);

	List<Product> findPopular(int count);
	
	List<Product> findByCriteria(ProductSearchCriteria criteria);
	
	int getProductsCount(ProductSearchCriteria criteria);
	
	Product getProductById(int id);

	void update(Product product, ProductUpdateCriteria criteria);

	void create(Product product);
	
	void delete(int id);
	
	void updateSellCount(int productId);
	
	boolean doesProductExist(int productId, String size);
}
