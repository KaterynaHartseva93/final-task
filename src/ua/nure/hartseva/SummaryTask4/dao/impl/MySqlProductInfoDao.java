package ua.nure.hartseva.SummaryTask4.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ua.nure.hartseva.SummaryTask4.dao.BaseDao;
import ua.nure.hartseva.SummaryTask4.dao.IProductInfoDao;
import ua.nure.hartseva.SummaryTask4.entity.product.ProductInfo;
import ua.nure.hartseva.SummaryTask4.exception.MySqlDaoException;
import ua.nure.hartseva.SummaryTask4.service.IConnectionProvider;

public class MySqlProductInfoDao extends BaseDao implements IProductInfoDao {

	private static final String CREATE_PRODUCT_INFO = "INSERT INTO product_info (id_product, sell_count, creation_timestamp, image_identifier) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_SELL_COUNT = "UPDATE product_info SET sell_count=sell_count+1 WHERE id_product=?";

	public MySqlProductInfoDao(IConnectionProvider connectionProvider) {
		super(connectionProvider);
	}

	@Override
	public void create(ProductInfo productInfo) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		int k = 1;
		PreparedStatement preparedStatement = connection.prepareStatement(CREATE_PRODUCT_INFO);
		preparedStatement.setInt(k++, productInfo.getProductId());
		preparedStatement.setInt(k++, 0);
		preparedStatement.setLong(k++, productInfo.getCreationDate().getTime());
		preparedStatement.setString(k++, productInfo.getImageIdentifier());

		if (preparedStatement.executeUpdate() < 1) {
			throw new MySqlDaoException("Failed to create product info object.");
		}
	}

	@Override
	public void updateSellCount(int productId) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SELL_COUNT);
		preparedStatement.setInt(1, productId);
		preparedStatement.executeUpdate();
	}

	
}
