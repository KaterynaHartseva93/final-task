package ua.nure.hartseva.SummaryTask4.bean;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class QueryConverterTest {

	private static final String GET_CLOTHES_BY_CRITERIA = "SELECT products.*, product_info.* FROM products "
			+ "INNER JOIN product_info ON products.id=product_info.id_product";
	private static final int LIMIT = 12;

	private ProductSearchCriteria criteria;
	private StringBuilder stringBuilder;
	private QueryConverter queryConverter = new QueryConverter();

	@Before
	public void setUp() {
		criteria = new ProductSearchCriteria();
		stringBuilder = new StringBuilder(GET_CLOTHES_BY_CRITERIA);
	}

	@Test
	public void shouldBeQueryLike_whenCriteriaHasCategoryId() {
		criteria.setCategoryId(7);
		String actual = QueryConverter.getSelectQuery(criteria);
		stringBuilder.append(" WHERE price>0 AND id_category=7");
		stringBuilder.append(" LIMIT ").append(LIMIT).append(" OFFSET 0");
		assertEquals(stringBuilder.toString(), actual);
	}

	@Test
	public void shouldBeQueryLike_whenCriteriaHasMinPrice() {
		criteria.setMinPrice(100);
		String actual = QueryConverter.getSelectQuery(criteria);
		stringBuilder.append(" WHERE price>100");
		stringBuilder.append(" LIMIT ").append(LIMIT).append(" OFFSET 0");
		assertEquals(stringBuilder.toString(), actual);
	}

	@Test
	public void shouldBeQueryLike_whenCriteriaHasMaxPrice() {
		criteria.setMaxPrice(100);
		String actual = QueryConverter.getSelectQuery(criteria);
		stringBuilder.append(" WHERE price>0 AND price<100");
		stringBuilder.append(" LIMIT ").append(LIMIT).append(" OFFSET 0");
		assertEquals(stringBuilder.toString(), actual);
	}

	@Test
	public void shouldBeQueryLike_whenCriteriaHasProductNamePart() {
		criteria.setProductNamePart("dre");
		String actual = QueryConverter.getSelectQuery(criteria);
		stringBuilder.append(" WHERE price>0 AND name LIKE '%dre%'");
		stringBuilder.append(" LIMIT ").append(LIMIT).append(" OFFSET 0");
		assertEquals(stringBuilder.toString(), actual);

	}

	@Test
	public void shouldBeQueryLike_whenCriteriaHasSortingParameters() {
		criteria.setSortingParameters(new SortingParameter(OrderName.NAME, OrderType.DESC));
		String actual = QueryConverter.getSelectQuery(criteria);
		stringBuilder.append(" WHERE price>0 ORDER BY name DESC");
		stringBuilder.append(" LIMIT ").append(LIMIT).append(" OFFSET 0");
		assertEquals(stringBuilder.toString(), actual);
	}

	@Test
	public void shouldBeQueryLike_whenCriteriaHasBrandsList() {
		criteria.setBrandIds(Arrays.asList(1, 2, 3));
		String actual = QueryConverter.getSelectQuery(criteria);
		stringBuilder.append(" WHERE price>0 AND id_brand IN (1, 2, 3)");
		stringBuilder.append(" LIMIT ").append(LIMIT).append(" OFFSET 0");
		assertEquals(stringBuilder.toString(), actual);
	}

	@Test
	public void shouldBeQueryLike_whenCriteriaHasNothing() {
		String actual = QueryConverter.getSelectQuery(criteria);
		stringBuilder.append(" WHERE price>0").append(" LIMIT ").append(LIMIT).append(" OFFSET 0");
		assertEquals(stringBuilder.toString(), actual);
	}
}