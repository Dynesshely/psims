package entity;

import java.util.Date;

public class SellHistory {

	private int shId;
	private int productId;
	private Product product;
	private Date sellDate;
	private int quantity;
	
	public int getShId() {
		return shId;
	}
	public void setShId(int shId) {
		this.shId = shId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Date getSellDate() {
		return sellDate;
	}
	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
