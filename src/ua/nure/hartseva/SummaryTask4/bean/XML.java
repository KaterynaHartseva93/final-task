package ua.nure.hartseva.SummaryTask4.bean;

public enum XML {
	PRESENTATION("presentation"), PAGE_VIEW("page-view"), ROLE("role"), HOME_PAGE("home-page"), PAGE_CATEGORIES(
			"page-categories"), PAGE_CATEGORY_KEY("page-category-key"), PAGE_CATEGORY_PATH("page-category-path"), PAGE_CATEGORY("page-category");

	private String value;

	XML(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static XML fromValue(String v) {
		for (XML c : XML.values()) {
			if (c.value.equalsIgnoreCase(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

	public boolean equalsTo(String name) {
		return value.equals(name);
	}

}
