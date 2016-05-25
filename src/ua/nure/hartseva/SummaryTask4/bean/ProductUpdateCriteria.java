package ua.nure.hartseva.SummaryTask4.bean;

import java.util.List;

import ua.nure.hartseva.SummaryTask4.entity.Size;

public class ProductUpdateCriteria {

	private String brandName;
	private int brandId;
	private String categoryName;
	private int categoryId;
	private String name;
	private Double price;
	private List<Size> sizesList;

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<Size> getSizesList() {
		return sizesList;
	}

	public void setSizesList(List<Size> sizesList) {
		this.sizesList = sizesList;
	}
}
