package ua.nure.hartseva.SummaryTask4.entity;

import java.io.Serializable;

/**
 * 
 * Contains information about current state of product. Does not contain
 * productId, because products can be removed.
 * 
 * @author Kateryna Hartseva
 *
 */
public class OrderUnit implements Serializable {

	private static final long serialVersionUID = 9199438575162762570L;

	private int id;
	private int orderId;
	private int productId;
	private String productName;
	private String productBrand;
	private String productCategory;
	private Size productSize;
	private int count;
	private double price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public Size getProductSize() {
		return productSize;
	}

	public void setProductSize(Size productSize) {
		this.productSize = productSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
}
