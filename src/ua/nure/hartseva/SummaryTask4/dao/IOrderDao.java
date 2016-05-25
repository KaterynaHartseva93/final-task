package ua.nure.hartseva.SummaryTask4.dao;

import java.sql.SQLException;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.entity.Order;

public interface IOrderDao {

	List<Order> getAll() throws SQLException;
	
	List<Order> getAllByUserEmail(String email) throws SQLException;

	int create(Order order) throws SQLException;
	
	void changeOrderStatus(int orderId, String newStatus) throws SQLException;
}
