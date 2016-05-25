package ua.nure.hartseva.SummaryTask4.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.bean.ProductSearchCriteria;
import ua.nure.hartseva.SummaryTask4.bean.ProductUpdateCriteria;
import ua.nure.hartseva.SummaryTask4.bean.QueryConverter;
import ua.nure.hartseva.SummaryTask4.dao.BaseDao;
import ua.nure.hartseva.SummaryTask4.dao.IProductDao;
import ua.nure.hartseva.SummaryTask4.entity.Brand;
import ua.nure.hartseva.SummaryTask4.entity.Category;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;
import ua.nure.hartseva.SummaryTask4.entity.product.ProductInfo;
import ua.nure.hartseva.SummaryTask4.exception.MySqlDaoException;
import ua.nure.hartseva.SummaryTask4.service.IConnectionProvider;

public class MySqlProductDao extends BaseDao implements IProductDao {

	private static final Logger LOG = Logger.getLogger(MySqlProductDao.class);

	private static final String GET_NEWEST_CLOTHES = "SELECT products.*, product_info.*"
			+ " FROM products INNER JOIN product_info ON products.id=product_info.id_product"
			+ " ORDER BY product_info.creation_timestamp DESC LIMIT ?";
	private static final String GET_POPULAR_CLOTHES = "SELECT products.*, product_info.*"
			+ " FROM products INNER JOIN product_info ON products.id=product_info.id_product"
			+ " ORDER BY product_info.sell_count DESC LIMIT ?";
	private static final String GET_PRODUCT_BY_ID = "SELECT products.*, product_info.* FROM products INNER JOIN product_info ON products.id=product_info.id_product WHERE products.id=?";
	private static final String UPDATE_PRODUCT = "UPDATE products SET price=? WHERE id=?";
	private static final String CREATE_PRODUCT = "INSERT INTO products (name, price, id_brand, id_category) VALUES (?, ?, ?, ?)";
	private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id=?";
	private static final String GET_PRODUCT_BU_ID_AND_SIZE = "SELECT products.id"
			+ " FROM products INNER JOIN product_sizes ON products.id=product_sizes.id_product WHERE id=? AND product_sizes.size=?";

	public MySqlProductDao(IConnectionProvider connectionProvider) {
		super(connectionProvider);
	}

	@Override
	public List<Product> getNewest(int count) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_NEWEST_CLOTHES);
		preparedStatement.setInt(1, count);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Product> clothesList = new ArrayList<>();
		if (resultSet != null) {
			while (resultSet.next()) {
				Product clothes = toProduct(resultSet);
				clothesList.add(clothes);
			}
		}
		return clothesList;
	}

	@Override
	public List<Product> getPopular(int count) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_POPULAR_CLOTHES);
		preparedStatement.setInt(1, count);

		ResultSet resultSet = preparedStatement.executeQuery();
		List<Product> clothesList = new ArrayList<>();
		if (resultSet != null) {
			while (resultSet.next()) {
				Product clothes = toProduct(resultSet);
				clothesList.add(clothes);
			}
		}
		return clothesList;
	}

	@Override
	public List<Product> getByCriteria(ProductSearchCriteria criteria) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		List<Product> clothesList = new ArrayList<>();
		Statement statement = connection.createStatement();
		String query = QueryConverter.getSelectQuery(criteria);
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			Product clothes = toProduct(resultSet);
			clothesList.add(clothes);
		}
		return clothesList;
	}

	@Override
	public Product getProductById(int id) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			return toProduct(resultSet);
		}
		return null;
	}

	@Override
	public void update(Product product, ProductUpdateCriteria criteria) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		int k = 1;
		PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT);

		if (criteria.getPrice() != null) {
			preparedStatement.setDouble(k++, criteria.getPrice());
		} else {
			preparedStatement.setDouble(k++, product.getPrice());
		}

		preparedStatement.setInt(k++, product.getId());
		boolean created = preparedStatement.executeUpdate() > 0;
		if (!created) {
			throw new MySqlDaoException("Failed to update product.");
		}
	}

	@Override
	public int create(Product product) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		int k = 1;
		PreparedStatement preparedStatement = connection.prepareStatement(CREATE_PRODUCT,
				Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(k++, product.getName());
		preparedStatement.setDouble(k++, product.getPrice());
		preparedStatement.setInt(k++, product.getBrand().getId());
		preparedStatement.setInt(k++, product.getCategory().getId());

		if (preparedStatement.executeUpdate() > 0) {
			try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
				resultSet.next();
				return resultSet.getInt(1);
			}
		}
		throw new MySqlDaoException("Failed to create product.");
	}

	@Override
	public void delete(int productId) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT);
		preparedStatement.setInt(1, productId);
		preparedStatement.executeUpdate();
	}

	@Override
	public int getCountByCriteria(ProductSearchCriteria criteria) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		Statement statement = connection.createStatement();
		String query = QueryConverter.getCountQuery(criteria);

		LOG.info("Query ---> " + query);

		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			return resultSet.getInt(1);
		}
		return 0;
	}

	private Product toProduct(ResultSet resultSet) throws SQLException {
		int brandId = resultSet.getInt(Attributes.ID_BRAND);
		int categoryId = resultSet.getInt(Attributes.ID_CATEGORY);

		String name = resultSet.getString(Attributes.NAME);
		double price = resultSet.getDouble(Attributes.PRICE);
		int id = resultSet.getInt(Attributes.ID);

		int sellCount = resultSet.getInt(Attributes.SELL_COUNT);
		long creationTimestamp = resultSet.getLong(Attributes.CREATION_DATE);
		String imageIdentifier = resultSet.getString(Attributes.IMAGE_IDENTIFIER);

		ProductInfo productInfo = new ProductInfo();
		productInfo.setSellCount(sellCount);
		productInfo.setCreationDate(new Date(creationTimestamp));
		productInfo.setImageIdentifier(imageIdentifier);

		Category category = new Category(categoryId, null);
		Brand brand = new Brand(brandId, null);

		Product product = new Product();
		product.setBrand(brand);
		product.setId(id);
		product.setCategory(category);
		product.setName(name);
		product.setPrice(price);
		product.setInfo(productInfo);
		return product;
	}

	@Override
	public boolean doesProductExist(int productId, String size) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BU_ID_AND_SIZE);
		preparedStatement.setInt(1, productId);
		preparedStatement.setString(2, size);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			return true;
		}
		return false;
	}
}
