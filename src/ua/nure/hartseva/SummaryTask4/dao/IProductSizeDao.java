package ua.nure.hartseva.SummaryTask4.dao;

import java.sql.SQLException;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.entity.Size;
import ua.nure.hartseva.SummaryTask4.entity.product.ProductSize;

public interface IProductSizeDao {

	List<ProductSize> findProductSizes(List<Integer> productIds) throws SQLException;

	void addSizes(int productId, List<Size> sizes) throws SQLException;

	void deleteSizes(int productId, List<Size> sizes) throws SQLException;
}
