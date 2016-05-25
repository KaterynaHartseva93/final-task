package ua.nure.hartseva.SummaryTask4.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.dao.BaseDao;
import ua.nure.hartseva.SummaryTask4.dao.IProductSizeDao;
import ua.nure.hartseva.SummaryTask4.entity.Size;
import ua.nure.hartseva.SummaryTask4.entity.product.ProductSize;
import ua.nure.hartseva.SummaryTask4.service.IConnectionProvider;

public class MySqlProductSizeDao extends BaseDao implements IProductSizeDao {

	private static final String FIND_PRODUCTS_SIZES_QUERY_TEMPLATE = "SELECT * FROM product_sizes WHERE id_product IN ";
	private static final String ADD_SIZE_QUERY = "INSERT INTO product_sizes (id_product, size) VALUES (?, ?)";
	private static final String DELETE_SIZE_QUERY = "DELETE FROM product_sizes WHERE id_product=? AND size=?";
	private static final String SEPARATOR = ", ";

	public MySqlProductSizeDao(IConnectionProvider connectionProvider) {
		super(connectionProvider);
	}

	@Override
	public List<ProductSize> findProductSizes(List<Integer> productIds) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		String queryString = getQueryString(productIds.size());
		PreparedStatement statement = connection.prepareStatement(queryString);

		int k = 1;
		for (Integer productId : productIds) {
			statement.setInt(k++, productId);
		}

		List<ProductSize> productSizes = new ArrayList<>();
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			ProductSize productSize = getFromResultSet(resultSet);
			productSizes.add(productSize);
		}
		return productSizes;
	}

	@Override
	public void addSizes(int productId, List<Size> sizes) throws SQLException {
		executeQueryBatch(productId, sizes, ADD_SIZE_QUERY);
	}

	@Override
	public void deleteSizes(int productId, List<Size> sizes) throws SQLException {
		executeQueryBatch(productId, sizes, DELETE_SIZE_QUERY);
	}

	private void executeQueryBatch(int productId, List<Size> sizes, String query) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		for (Size size : sizes) {
			preparedStatement.setInt(1, productId);
			preparedStatement.setString(2, size.name());
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
	}

	private String getQueryString(int variablesCount) {
		StringBuilder builder = new StringBuilder();
		String separator = "";
		builder.append(FIND_PRODUCTS_SIZES_QUERY_TEMPLATE).append("(");
		for (int i = 0; i < variablesCount; i++) {
			builder.append(separator);
			builder.append("?");
			separator = SEPARATOR;
		}
		builder.append(")");
		return builder.toString();
	}

	private ProductSize getFromResultSet(ResultSet resultSet) throws SQLException {
		int productId = resultSet.getInt(Attributes.ID_PRODUCT);
		Size size = Size.valueOf(resultSet.getString(Attributes.SIZE));

		ProductSize productSize = new ProductSize();
		productSize.setProductId(productId);
		productSize.setSize(size);
		return productSize;
	}
}
