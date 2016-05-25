package ua.nure.hartseva.SummaryTask4.entity.product;

import java.io.Serializable;
import java.util.Set;

import ua.nure.hartseva.SummaryTask4.entity.Brand;
import ua.nure.hartseva.SummaryTask4.entity.Category;
import ua.nure.hartseva.SummaryTask4.entity.Size;

public class Product implements Serializable {

	private static final long serialVersionUID = 8284214767488055656L;

	private int id;
	private String name;
	private Brand brand;
	private double price;
	private Category category;
	private ProductInfo info;
	private Set<Size> availableSizes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public ProductInfo getInfo() {
		return info;
	}

	public void setInfo(ProductInfo info) {
		this.info = info;
	}

	public Set<Size> getAvailableSizes() {
		return availableSizes;
	}

	public void setAvailableSizes(Set<Size> availableSizes) {
		this.availableSizes = availableSizes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Product other = (Product) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	public String toString() {
		return name;
	}
}
