package ua.nure.hartseva.SummaryTask4.bean;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchCriteria {

	public static final int PRODUCTS_LIMIT_ON_PAGE = 12;
	
	private int pageNumber = 1;
	private int categoryId;
	private List<Integer> brandsId = new ArrayList<>();
	private int minPrice;
	private int maxPrice;
	private SortingParameter sortingParameters;
	private String productNamePart;

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public List<Integer> getBrandsId() {
		return brandsId;
	}

	public void setBrandIds(List<Integer> brandIds) {
		brandsId.addAll(brandIds);
	}

	public SortingParameter getSortingParameters() {
		return sortingParameters;
	}

	public void setSortingParameters(SortingParameter sortingParameters) {
		this.sortingParameters = sortingParameters;
	}

	public String getProductNamePart() {
		return productNamePart;
	}

	public void setProductNamePart(String productNamePart) {
		this.productNamePart = productNamePart;
	}
}
