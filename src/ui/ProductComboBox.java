package ui;

import java.util.Vector;

import javax.swing.JComboBox;

import entity.ProductOption;

/**
 * �Զ�������������չʾ��Ʒ�����Զ�value��text����
 * 
 * @author xujinnan
 * 
 */
public class ProductComboBox extends JComboBox {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2461723055752181348L;

	public ProductComboBox(){
		
	}
	
	public ProductComboBox(Vector<ProductOption> vec){
		super(vec);
	}
	
	public String getText() {
		ProductOption item = (ProductOption) this.getSelectedItem();
		return item.getText();
	}

	public Integer getValue() {
		ProductOption item = (ProductOption) this.getSelectedItem();
		return item.getValue();
	}
}
