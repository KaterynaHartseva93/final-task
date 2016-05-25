package ua.nure.hartseva.SummaryTask4.bean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ua.nure.hartseva.SummaryTask4.entity.Size;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;

public class CartTest {

	private Cart cart;
	private Product product = new Product();
	private Size size = Size.L;
	
	@Before
	public void setUp() {
		cart = new Cart();
	}
	
	@Test
	public void shouldAddProductItemToCart() {
		cart.add(product, size);
		assertTrue(cart.getItems().containsKey(new ProductItem(product, size)));
	}
	
	@Test
	public void shouldIncrementCountWhenCartHasProductItem() {
		cart.add(product, size);
		cart.add(product, size);
		assertEquals(Integer.valueOf(2), cart.getItems().get(new ProductItem(product, size)));
	}
	
	@Test
	public void shouldClearCart() {
		cart.add(product, size);
		cart.clear();
		assertTrue(cart.isEmpty());
	}
	
	@Test
	public void shouldIncrementProductItemCount() {
		cart.add(product, size);
		cart.incrementCount(new ProductItem(product, size));
		assertEquals(Integer.valueOf(2), cart.getItems().get(new ProductItem(product, size)));
	}
	
	@Test
	public void shouldDecrementProductItemCount() {
		cart.add(product, size);
		cart.add(product, size);
		cart.decrementCount(new ProductItem(product, size));
		assertEquals(Integer.valueOf(1), cart.getItems().get(new ProductItem(product, size)));
	}
	
	@Test
	public void shouldDeleteProductItem_whenResultCountIsZero() {
		cart.add(product, size);
		cart.decrementCount(new ProductItem(product, size));
		assertTrue(cart.isEmpty());
	}
	
	@Test
	public void shouldDeleteProductItem() {
		cart.add(product, size);
		cart.add(product, size);
		cart.deletePosition(new ProductItem(product, size));
		assertTrue(cart.isEmpty());
	}
	
	@Test
	public void shouldCountProductsQuantityInTheCart() {
		cart.add(product, size);
		cart.add(product, size);
		cart.add(product, Size.XXXL);
		assertEquals(3, cart.getCount());
	}
	
	@Test
	public void shouldCountTotalPriceForProductsInTheCart() {
		product.setPrice(12.5);
		cart.add(product, size);
		Product product2 = new Product();
		product2.setPrice(13.);
		cart.add(product2, Size.M);
		assertEquals(Double.valueOf(25.5), Double.valueOf(cart.getTotalPrice()));
		
	}

}