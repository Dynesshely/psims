package entity;

/**
 * ���ﳵʵ����
 * @author xujinnan
 *
 */
public class Cart {
	/**���*/
	private int seq;
	/**��Ʒ*/
	private Product product;
	/**����*/
	private int quantity;
	/**С��*/
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
