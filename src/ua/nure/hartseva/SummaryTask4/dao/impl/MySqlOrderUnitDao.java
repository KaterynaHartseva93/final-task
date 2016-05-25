package ua.nure.hartseva.SummaryTask4.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.dao.BaseDao;
import ua.nure.hartseva.SummaryTask4.dao.IOrderUnitDao;
import ua.nure.hartseva.SummaryTask4.entity.OrderUnit;
import ua.nure.hartseva.SummaryTask4.entity.Size;
import ua.nure.hartseva.SummaryTask4.service.IConnectionProvider;

public class MySqlOrderUnitDao extends BaseDao implements IOrderUnitDao {

	private static final String GET_ORDER_UNITS = "SELECT * FROM order_units WHERE id_order=?";
	private static final String CREATE_ORDER_UNIT = "INSERT INTO order_units (id_order, id_product, product_name, product_brand, product_category, product_price, product_count, product_size) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	public MySqlOrderUnitDao(IConnectionProvider connectionProvider) {
		super(connectionProvider);
	}

	@Override
	public List<OrderUnit> getOrderUnits(int orderId) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_UNITS);
		preparedStatement.setInt(1, orderId);

		ResultSet resultSet = preparedStatement.executeQuery();
		List<OrderUnit> orderUnits = new ArrayList<>();
		while (resultSet.next()) {
			OrderUnit orderUnit = getOrderUnitFromResultSet(resultSet);
			orderUnits.add(orderUnit);
		}
		return orderUnits;
	}

	@Override
	public void create(int orderId, List<OrderUnit> orderUnitList) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER_UNIT,
				Statement.RETURN_GENERATED_KEYS);
		for (OrderUnit orderUnit : orderUnitList) {
			int k = 1;
			preparedStatement.setInt(k++, orderId);
			preparedStatement.setInt(k++, orderUnit.getProductId());
			preparedStatement.setString(k++, orderUnit.getProductName());
			preparedStatement.setString(k++, orderUnit.getProductBrand());
			preparedStatement.setString(k++, orderUnit.getProductCategory());
			preparedStatement.setDouble(k++, orderUnit.getPrice());
			preparedStatement.setInt(k++, orderUnit.getCount());
			preparedStatement.setString(k++, orderUnit.getProductSize().name());
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
	}

	private OrderUnit getOrderUnitFromResultSet(ResultSet resultSet) throws SQLException {
		OrderUnit orderUnit = new OrderUnit();

		int id = resultSet.getInt(Attributes.ID);
		int orderId = resultSet.getInt(Attributes.ID_ORDER);
		int productId = resultSet.getInt(Attributes.ID_PRODUCT);
		String productName = resultSet.getString(Attributes.PRODUCT_NAME);
		String productBrand = resultSet.getString(Attributes.PRODUCT_BRAND);
		String productCategory = resultSet.getString(Attributes.PRODUCT_CATEGORY);
		double productPrice = resultSet.getDouble(Attributes.PRODUCT_PRICE);
		int productCount = resultSet.getInt(Attributes.PRODUCT_COUNT);
		Size size = Size.valueOf(resultSet.getString(Attributes.PRODUCT_SIZE));

		orderUnit.setId(id);
		orderUnit.setProductId(productId);
		orderUnit.setOrderId(orderId);
		orderUnit.setProductName(productName);
		orderUnit.setProductBrand(productBrand);
		orderUnit.setProductCategory(productCategory);
		orderUnit.setProductSize(size);
		orderUnit.setPrice(productPrice);
		orderUnit.setCount(productCount);
		return orderUnit;
	}
}
