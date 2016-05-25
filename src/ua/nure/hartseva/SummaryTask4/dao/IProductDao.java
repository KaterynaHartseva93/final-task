package ua.nure.hartseva.SummaryTask4.dao;

import java.sql.SQLException;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.bean.ProductSearchCriteria;
import ua.nure.hartseva.SummaryTask4.bean.ProductUpdateCriteria;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;

public interface IProductDao {
	
	List<Product> getNewest(int count) throws SQLException;
	
	List<Product> getPopular(int count) throws SQLException;
	
	Product getProductById(int id) throws SQLException;
	
	List<Product> getByCriteria(ProductSearchCriteria criteria) throws SQLException;
	
	int getCountByCriteria(ProductSearchCriteria criteria) throws SQLException;

	void update(Product product, ProductUpdateCriteria criteria) throws SQLException;

	int create(Product product) throws SQLException;

	void delete(int productId) throws SQLException;
	
	boolean doesProductExist(int productId, String size) throws SQLException;
}
