package entity;

/**
 * 购物车实体类
 * @author xujinnan
 *
 */
public class Cart {
	/**序号*/
	private int seq;
	/**商品*/
	private Product product;
	/**数量*/
	private int quantity;
	/**小计*/
	private double amt;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getAmt() {
		return amt;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
}
