package ua.nure.hartseva.SummaryTask4.bean;

public class SortingParameter {

	private final OrderName orderName;
	private final OrderType orderType;

	public SortingParameter(OrderName orderName, OrderType orderType) {
		this.orderName = orderName;
		this.orderType = orderType;
	}

	public OrderName getOrderName() {
		return orderName;
	}

	public OrderType getOrderType() {
		return orderType;
	}
}
