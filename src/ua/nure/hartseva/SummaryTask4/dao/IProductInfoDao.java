package ua.nure.hartseva.SummaryTask4.dao;

import java.sql.SQLException;

import ua.nure.hartseva.SummaryTask4.entity.product.ProductInfo;

public interface IProductInfoDao {

	void create(ProductInfo productInfo) throws SQLException;
	
	void updateSellCount(int productId) throws SQLException;
}
