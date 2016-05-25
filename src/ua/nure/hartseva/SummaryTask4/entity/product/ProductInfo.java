package ua.nure.hartseva.SummaryTask4.entity.product;

import java.io.Serializable;
import java.util.Date;

public class ProductInfo implements Serializable {

	private static final long serialVersionUID = 5167359079986169152L;

	private int productId;
	private int sellCount;
	private String imageIdentifier;
	private Date creationDate;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getSellCount() {
		return sellCount;
	}

	public void setSellCount(int sellCount) {
		this.sellCount = sellCount;
	}

	public String getImageIdentifier() {
		return imageIdentifier;
	}

	public void setImageIdentifier(String imageIdentifier) {
		this.imageIdentifier = imageIdentifier;
	}

	public Date getCreationDate() {
		if (creationDate != null) {
			return new Date(creationDate.getTime());
		}
		return null;
	}

	public void setCreationDate(Date creationDate) {
		if (creationDate != null) {
			this.creationDate = new Date(creationDate.getTime());
		}
	}

	@Override
	public String toString() {
		return "ProductInfo [productId=" + productId + ", sellCount=" + sellCount + ", imageIdentifier="
				+ imageIdentifier + ", creationDate=" + creationDate + "]";
	}
}
