package ui;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import entity.Product;

/**
 * �����JTable��Model�࣬Ϊʵ�ֻ�ȡѡ����ID�����ô���
 * @author xujinnan
 *
 */
public class StorageTableModel extends DefaultTableModel{
	private static final long serialVersionUID = -318347835885156596L;

	private Vector<Product> products;
	
	public StorageTableModel(Vector<Product> list){
		this.products = list;
	}
	
	@Override
	public int getRowCount() {
		return this.products.size();
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex < 0 || columnIndex < 0){
			return "";
		}
		switch (columnIndex) {
		case 0:
			int c = this.products.get(rowIndex).getCatogery();
			switch (c) {
			case 1:
				return "����";
			case 2:
				return "ʳƷ";
			case 3:
				return "����";
			case 4:
				return "����";
			case 5:
				return "��ʳ";
			case 6:
				return "������Ʒ";
			}
		case 1:
			return this.products.get(rowIndex).getProductNo();
		case 2:
			return this.products.get(rowIndex).getName();
		case 3:
			return this.products.get(rowIndex).getPurPrice();
		case 4:
			return this.products.get(rowIndex).getPrice();
		case 5:
			return this.products.get(rowIndex).getStorage();
		default:
			return "";
		}
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Ʒ��";
		case 1:
			return "���";
		case 2:
			return "����";
		case 3:
			return "������";
		case 4:
			return "���ۼ�";
		case 5:
			return "���";
		default:
			return "-";
		}
	}

	public Vector<Product> getProducts() {
		return products;
	}

	public void setProducts(Vector<Product> products) {
		this.products = products;
	}

}
