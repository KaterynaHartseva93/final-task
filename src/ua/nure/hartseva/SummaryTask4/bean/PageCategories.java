package ua.nure.hartseva.SummaryTask4.bean;

import java.util.ArrayList;
import java.util.List;

public class PageCategories {

	private List<PageCategory> pageCategories;

	public List<PageCategory> getPageCategories() {
		if (pageCategories == null) {
			pageCategories = new ArrayList<PageCategory>();
		}
		return this.pageCategories;
	}

	@Override
	public String toString() {
		return "PageCategories [pageCategories=" + pageCategories + "]";
	}
}
