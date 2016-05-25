package ua.nure.hartseva.SummaryTask4.service;

import java.util.List;

import ua.nure.hartseva.SummaryTask4.entity.Order;
import ua.nure.hartseva.SummaryTask4.entity.OrderUnit;

public interface IOrderService {

	List<Order> getAll();

	List<Order> getAllByUserEmail(String email);

	void create(Order order);
	
	void changeOrderStatus(int orderId, String newStatus);
	
	List<OrderUnit> getOrderUnits(int orderId);
}
