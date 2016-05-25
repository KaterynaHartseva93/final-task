package ua.nure.hartseva.SummaryTask4.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.bean.ITransactedOperation;
import ua.nure.hartseva.SummaryTask4.dao.IOrderDao;
import ua.nure.hartseva.SummaryTask4.dao.IOrderUnitDao;
import ua.nure.hartseva.SummaryTask4.entity.Order;
import ua.nure.hartseva.SummaryTask4.entity.OrderUnit;
import ua.nure.hartseva.SummaryTask4.service.BaseService;
import ua.nure.hartseva.SummaryTask4.service.IOrderService;
import ua.nure.hartseva.SummaryTask4.service.ITransactionProvider;

public class OrderService extends BaseService implements IOrderService {

	private final IOrderDao orderDao;
	private final IOrderUnitDao orderUnitDao;

	public OrderService(ITransactionProvider transactionProvider, IOrderDao orderDao, IOrderUnitDao orderUnitDao) {
		super(transactionProvider);
		this.orderDao = orderDao;
		this.orderUnitDao = orderUnitDao;
	}

	@Override
	public List<Order> getAll() {
		return transactionProvider.execute(new ITransactedOperation<List<Order>>() {
			@Override
			public List<Order> execute() throws SQLException {
				List<Order> orders = orderDao.getAll();
				return fillWithUnits(orders);
			}
		});
	}

	@Override
	public List<Order> getAllByUserEmail(final String email) {
		return transactionProvider.execute(new ITransactedOperation<List<Order>>() {
			@Override
			public List<Order> execute() throws SQLException {
				List<Order> orders = orderDao.getAllByUserEmail(email);
				return fillWithUnits(orders);
			}
		});
	}

	@Override
	public void create(final Order order) {
		transactionProvider.execute(new ITransactedOperation<Void>() {
			@Override
			public Void execute() throws SQLException {
				int orderId = orderDao.create(order);
				orderUnitDao.create(orderId, order.getOrderUnitList());
				return null;
			}
		});
	}

	private List<Order> fillWithUnits(List<Order> orders) throws SQLException {
		if (orders == null) {
			return orders;
		}
		for (Order order : orders) {
			int orderId = order.getId();
			List<OrderUnit> orderUnits = orderUnitDao.getOrderUnits(orderId);
			order.setOrderUnitList(orderUnits);
		}
		return new ArrayList<>(orders);
	}

	@Override
	public void changeOrderStatus(final int orderId, final String newStatus) {
		transactionProvider.execute(new ITransactedOperation<Void>() {
			@Override
			public Void execute() throws SQLException {
				orderDao.changeOrderStatus(orderId, newStatus);
				return null;
			}
		});
	}

	@Override
	public List<OrderUnit> getOrderUnits(final int orderId) {
		return transactionProvider.execute(new ITransactedOperation<List<OrderUnit>>() {
			@Override
			public List<OrderUnit> execute() throws SQLException {
				return orderUnitDao.getOrderUnits(orderId);
			}
		});
	}
}
