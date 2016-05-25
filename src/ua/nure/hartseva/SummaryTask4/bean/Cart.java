package ua.nure.hartseva.SummaryTask4.bean;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import ua.nure.hartseva.SummaryTask4.entity.Size;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;

public class Cart implements Serializable {

	private static final long serialVersionUID = -1913562503381736296L;

	private ConcurrentHashMap<ProductItem, Integer> itemsMap;

	public Cart() {
		itemsMap = new ConcurrentHashMap<ProductItem, Integer>();
	}

	public Cart add(Product product, Size size) {
		ProductItem productItem = new ProductItem(product, size);
		Integer count = itemsMap.get(productItem);
		if (count == null || count == 0) {
			itemsMap.put(productItem, 1);
		} else {
			itemsMap.put(productItem, count + 1);
		}
		return this;
	}

	public void clear() {
		itemsMap.clear();

	}

	public ConcurrentHashMap<ProductItem, Integer> getItems() {
		return new ConcurrentHashMap<>(itemsMap);
	}

	public double getTotalPrice() {
		double totalPrice = 0;
		if (itemsMap != null && !itemsMap.isEmpty()) {
			for (ConcurrentHashMap.Entry<ProductItem, Integer> itemEntry : itemsMap.entrySet()) {
				ProductItem productItem = itemEntry.getKey();
				Integer count = itemEntry.getValue();
				totalPrice += productItem.getProduct().getPrice() * count;
			}
		}
		return Math.rint(100.0 * totalPrice) / 100.0;
	}

	public int getCount() {
		int itemsCount = 0;
		if (itemsMap != null && !itemsMap.isEmpty()) {
			for (Integer count : itemsMap.values()) {
				itemsCount += count;
			}
		}
		return itemsCount;
	}

	public boolean isEmpty() {
		return itemsMap.isEmpty();
	}

	public void incrementCount(ProductItem productItem) {
		itemsMap.put(productItem, itemsMap.get(productItem) + 1);
	}

	public void decrementCount(ProductItem productItem) {
		int oldCount = itemsMap.get(productItem);
		if (oldCount == 1) {
			deletePosition(productItem);
		} else {
			itemsMap.put(productItem, oldCount - 1);
		}
	}

	public void deletePosition(ProductItem productItem) {
		itemsMap.remove(productItem);
	}
}
