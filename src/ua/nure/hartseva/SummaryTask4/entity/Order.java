package ua.nure.hartseva.SummaryTask4.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {

	private static final long serialVersionUID = 4103082478021728770L;

	private int id;
	private List<OrderUnit> orderUnitList;
	private String ownerEmail;
	private OrderStatus status = OrderStatus.REGISTERED;
	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<OrderUnit> getOrderUnitList() {
		return orderUnitList;
	}

	public void setOrderUnitList(List<OrderUnit> orderUnitList) {
		this.orderUnitList = orderUnitList;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		if (ownerEmail != null) {
			// emails stored only in lower case
			this.ownerEmail = ownerEmail.toLowerCase();
		}
	}

	public Date getDate() {
		if (date != null) {
			return new Date(date.getTime());
		}
		return null;
	}

	public void setDate(Date date) {
		if (date != null) {
			this.date = new Date(date.getTime());
		}
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public double getTotalPrice() {
		double totalPrice = 0;
		if (orderUnitList != null) {
			for (OrderUnit orderUnit : orderUnitList) {
				totalPrice += orderUnit.getPrice() * orderUnit.getCount();
			}
		}
		return Math.rint(100.0 * totalPrice) / 100.0;
	}
}
