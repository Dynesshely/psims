package entity;

import java.util.Date;

public class Product {
	private int productId;
	private String productNo;
	private String name;
	private int catogery;
	private double price;
	private double purPrice;
	private Date stockDate;
	private int storage;
	private int alarmStorage;
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCatogery() {
		return catogery;
	}
	public void setCatogery(int catogery) {
		this.catogery = catogery;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getPurPrice() {
		return purPrice;
	}
	public void setPurPrice(double purPrice) {
		this.purPrice = purPrice;
	}
	public Date getStockDate() {
		return stockDate;
	}
	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}
	public int getStorage() {
		return storage;
	}
	public void setStorage(int storage) {
		this.storage = storage;
	}
	public int getAlarmStorage() {
		return alarmStorage;
	}
	public void setAlarmStorage(int alarmStorage) {
		this.alarmStorage = alarmStorage;
	}
}
