package ua.nure.hartseva.SummaryTask4.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.dao.BaseDao;
import ua.nure.hartseva.SummaryTask4.dao.IOrderDao;
import ua.nure.hartseva.SummaryTask4.entity.Order;
import ua.nure.hartseva.SummaryTask4.entity.OrderStatus;
import ua.nure.hartseva.SummaryTask4.exception.MySqlDaoException;
import ua.nure.hartseva.SummaryTask4.service.IConnectionProvider;

public class MySqlOrderDao extends BaseDao implements IOrderDao {

	private static final String GET_ALL_ORDERS = "SELECT * FROM orders ORDER BY creation_timestamp DESC";
	private static final String GET_ALL_BY_USER_EMAIL = "SELECT * FROM orders WHERE owner_email=? ORDER BY creation_timestamp DESC";
	private static final String CREATE_ORDER = "INSERT INTO orders (owner_email, status, creation_timestamp) VALUES (?, ?, ?)";
	private static final String UPDATE_FIELD_IN_THE_TABLE = "UPDATE orders SET status=? WHERE id=?";
	
	public MySqlOrderDao(IConnectionProvider connectionProvider) {
		super(connectionProvider);
	}
	
	@Override
	public List<Order> getAll() throws SQLException {
		Connection connection = connectionProvider.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(GET_ALL_ORDERS);
		List<Order> orders = new ArrayList<>();
		while (resultSet.next()) {
			Order order = getOrderFromResultSet(resultSet);
			orders.add(order);
		}
		return orders;
	}

	@Override
	public List<Order> getAllByUserEmail(String email) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_USER_EMAIL);
		preparedStatement.setString(1,  email);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Order> orders = new ArrayList<>();
		while (resultSet.next()) {
			Order order = getOrderFromResultSet(resultSet);
			orders.add(order);
		}
		return orders;
	}

	@Override
	public int create(Order order) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		int k = 1;
		PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(k++, order.getOwnerEmail());
		preparedStatement.setString(k++, order.getStatus().name());
		preparedStatement.setLong(k++, order.getDate().getTime());
		if (preparedStatement.executeUpdate() > 0) {
			try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
				resultSet.next();
				return resultSet.getInt(1);
			}
		}
		throw new MySqlDaoException("Failed to create new order.");
	}
	
	private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt(Attributes.ID);
		String ownerEmail = resultSet.getString(Attributes.OWNER_EMAIL);
		OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString(Attributes.STATUS));
		Date creationDate = new Date(resultSet.getLong(Attributes.CREATION_DATE));
		
		Order order = new Order();
		order.setDate(creationDate);
		order.setId(id);
		order.setOwnerEmail(ownerEmail);
		order.setStatus(orderStatus);
		return order;
	}

	@Override
	public void changeOrderStatus(int orderId, String newStatus) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FIELD_IN_THE_TABLE);
		preparedStatement.setString(1,  newStatus);
		preparedStatement.setInt(2, orderId);
		preparedStatement.executeUpdate();
	}
}
