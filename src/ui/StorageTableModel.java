package ui;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import entity.Product;

/**
 * 库存表格JTable的Model类，为实现获取选定行ID，弃用此类
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
				return "饮料";
			case 2:
				return "食品";
			case 3:
				return "酒类";
			case 4:
				return "香烟";
			case 5:
				return "零食";
			case 6:
				return "生活用品";
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
			return "品种";
		case 1:
			return "编号";
		case 2:
			return "名称";
		case 3:
			return "进货价";
		case 4:
			return "零售价";
		case 5:
			return "库存";
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
