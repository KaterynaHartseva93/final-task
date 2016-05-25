package ua.nure.hartseva.SummaryTask4.bean;

import java.util.ArrayList;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.entity.Role;

public class Presentation {

	private List<PageView> pageView;

	public List<PageView> getPageView() {
		if (pageView == null) {
			pageView = new ArrayList<PageView>();
		}
		return this.pageView;
	}

	public PageView getByRole(Role role) {
		if (pageView != null && !pageView.isEmpty()) {
			for (PageView pageViewItem : pageView) {
				if (pageViewItem.getRole() == role) {
					return pageViewItem;
				}
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "Presentation [pageView=" + pageView + "]";
	}
}
