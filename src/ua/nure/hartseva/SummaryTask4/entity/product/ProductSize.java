package ua.nure.hartseva.SummaryTask4.entity.product;

import java.io.Serializable;

import ua.nure.hartseva.SummaryTask4.entity.Size;

public class ProductSize implements Serializable {

	private static final long serialVersionUID = 2274278335034404469L;
	
	private int productId;
	private Size size;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}
}
