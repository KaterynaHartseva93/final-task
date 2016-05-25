package ua.nure.hartseva.SummaryTask4.dao;

import java.sql.SQLException;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.entity.OrderUnit;

public interface IOrderUnitDao {

	List<OrderUnit> getOrderUnits(int orderId) throws SQLException;

	void create(int orderId, List<OrderUnit> orderUnitList) throws SQLException;
}
