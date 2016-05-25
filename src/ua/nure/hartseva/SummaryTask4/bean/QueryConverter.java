package ua.nure.hartseva.SummaryTask4.bean;

import java.util.List;

public class QueryConverter {
	
	private static final String GET_CLOTHES_BY_CRITERIA = "SELECT products.*, product_info.* FROM products INNER JOIN product_info ON products.id=product_info.id_product";
	private static final String GET_CLOTHES_COUNT = "SELECT COUNT(*) FROM products";

	public static String getSelectQuery(ProductSearchCriteria criteria) {
		return getFullQuery(criteria, GET_CLOTHES_BY_CRITERIA, true);
	}
	
	public static String getCountQuery(ProductSearchCriteria criteria) {
		return getFullQuery(criteria, GET_CLOTHES_COUNT, false);
	}
	
	private static String getFullQuery(ProductSearchCriteria criteria, String initialQuery, boolean takeIntoAccountOrdering) {
		StringBuilder query = new StringBuilder(initialQuery);
		List<Integer> brandsId = criteria.getBrandsId();
		int categoryId = criteria.getCategoryId();
		int minPrice = criteria.getMinPrice();
		int maxPrice = criteria.getMaxPrice();
		int skipProductsCount = (criteria.getPageNumber() - 1) * ProductSearchCriteria.PRODUCTS_LIMIT_ON_PAGE;
		String productNamePart = criteria.getProductNamePart();

		SortingParameter sortingParameters = criteria.getSortingParameters();

		query.append(" WHERE price>").append(minPrice);
		if (categoryId != 0) {
			query.append(" AND id_category=").append(categoryId);
		}

		if (maxPrice != 0) {
			query.append(" AND price<").append(maxPrice);
		}
		if (!brandsId.isEmpty()) {
			query.append(" AND id_brand IN (");
			for (int brandId : brandsId) {
				query.append(brandId).append(", ");
			}
			query.delete(query.length() - 2, query.length());
			query.append(")");
		}
		if (productNamePart != null) {
			query.append(" AND name LIKE '%").append(productNamePart).append("%'");
		}
		
		if (takeIntoAccountOrdering) {
			if (sortingParameters != null) {
				query.append(" ORDER BY ").append(sortingParameters.getOrderName().getValue());
				if (sortingParameters.getOrderType() != null) {
					query.append(" ").append(sortingParameters.getOrderType());
				}
			}
			query.append(" LIMIT ").append(ProductSearchCriteria.PRODUCTS_LIMIT_ON_PAGE).append(" OFFSET ").append(skipProductsCount);
		}
		return query.toString();
	}
}
