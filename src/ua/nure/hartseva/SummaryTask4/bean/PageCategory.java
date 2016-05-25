package ua.nure.hartseva.SummaryTask4.bean;

public class PageCategory {

	private String pageCategoryKey;
	private String pageCategoryPath;

	public String getPageCategoryKey() {
		return pageCategoryKey;
	}

	public void setPageCategoryKey(String value) {
		this.pageCategoryKey = value;
	}

	public String getPageCategoryPath() {
		return pageCategoryPath;
	}

	public void setPageCategoryPath(String value) {
		this.pageCategoryPath = value;
	}

	@Override
	public String toString() {
		return "PageCategory [pageCategoryKey=" + pageCategoryKey + ", pageCategoryPath=" + pageCategoryPath + "]";
	}
}
