package ua.nure.hartseva.SummaryTask4.bean;

import ua.nure.hartseva.SummaryTask4.entity.Role;

public class PageView {

	private Role role;
	private String homePage;
	private PageCategories pageCategories;

	public Role getRole() {
		return role;
	}

	public void setRole(Role value) {
		this.role = value;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String value) {
		this.homePage = value;
	}
	 
	public PageCategories getPageCategories() {
		return pageCategories;
	}
	
	public void setPageCategories(PageCategories pageCategories) {
		this.pageCategories = pageCategories;
	}

	@Override
	public String toString() {
		return "PageView [role=" + role + ", homePage=" + homePage + ", pageCategories=" + pageCategories + "]";
	}
}
