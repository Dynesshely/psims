package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.ProductDao;
import entity.Product;

public class StorageFrame extends JFrame{
	private static final long serialVersionUID = -8808883923263763897L;
	
	private ClientContext clientContext;
	private JScrollPane storagePanel;
	private JTable storageTable;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private JComboBox catCombox;
	/**表头*/
	private String[] rowname;
	/**表内容（二维数据）*/
	private String[][] data;
	private int currCat = 1;
	private String currOrder = "desc";
	
	public StorageFrame(){
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		init();
	}
	
	public void init(){
		this.setTitle("库存查询");
		this.setSize(600, 320);
		this.setContentPane(createContentPane());
		int windowWidth = this.getWidth(); //获得窗口宽
		int windowHeight = this.getHeight(); //获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
		Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
		int screenWidth = screenSize.width; //获取屏幕的宽
		int screenHeight = screenSize.height; //获取屏幕的高
		this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
		this.setResizable(false);
	}

	private Container createContentPane() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createStoragePanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.SOUTH);
		return panel;
	}

	private Component createButtonPanel() {
		JPanel panel = new JPanel();
		catCombox = new JComboBox();
		panel.add(catCombox);
		catCombox.addItem("饮料");
		catCombox.addItem("食品");
		catCombox.addItem("酒类");
		catCombox.addItem("香烟");
		catCombox.addItem("零食");
		catCombox.addItem("生活用品");
		catCombox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.filterCategory();
			}
		});
		JButton ascBtn = new JButton("升序");
		ascBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.orderStorageData("asc");
			}
		});
		panel.add(ascBtn);
		JButton descBtn = new JButton("降序");
		descBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.orderStorageData("desc");
			}
		});
		panel.add(descBtn);
		JButton modifyBtn = new JButton("修改");
		modifyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(storageTable.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(StorageFrame.this, "请选择要操作的商品！","商品管理", JOptionPane.WARNING_MESSAGE);
				}else{
					ProductDao dao = new ProductDao();
					int pid = Integer.parseInt(data[storageTable.getSelectedRow()][0]);//根据选中行的下标，从data二维数组中取到对应的行，其中第一列为ID
					Product currProduct = dao.findProduct(pid);//根据ID从数据库中读取商品
					clientContext.setCurrProduct(currProduct);//设置控制器中的当前商品实例
					clientContext.showOrHideModifyProductFrame(true);//显示修改商品窗口
				}
			}
		});
		panel.add(modifyBtn);
		
		JButton delBtn = new JButton("删除");
		delBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(storageTable.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(StorageFrame.this, "请选择要操作的商品！","商品管理", JOptionPane.WARNING_MESSAGE);
				}else{
					int val = JOptionPane.showConfirmDialog(StorageFrame.this, "是否删除该商品？");
					if (val == JOptionPane.YES_OPTION) {
						int pid = Integer.parseInt(data[storageTable.getSelectedRow()][0]);//根据选中行的下标，从data二维数组中取到对应的行，其中第一列为ID
						JOptionPane.showMessageDialog(StorageFrame.this, "商品已删除！","商品管理", JOptionPane.INFORMATION_MESSAGE);
						clientContext.deleteProduct(pid);
					}
				}
			}
		});
		panel.add(delBtn);
		
		JButton okBtn = new JButton("关闭");
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.showOrHideStorageFrame(false);
			}
		});
		panel.add(okBtn);
		return panel;
	}

	private Component createStoragePanel() {
		storagePanel = new JScrollPane();
		initTableData();
		storagePanel.setViewportView(storageTable);
		return storagePanel;
	}
	
	/**
	 * 初始化表格数据
	 */
	public void initTableData(){
		String[] rowNames = getRowNames();
		String[][] data = getData(null);
		storageTable = new JTable(data, rowNames);
		//单元格居中显示
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
		storageTable.setDefaultRenderer(Object.class, tcr);
		storageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
	}
	
	/**
	 * 刷新JTable的数据
	 */
	public void refreshTableData(String order){
		currOrder = order;
		DefaultTableModel model = new DefaultTableModel(getData(order), getRowNames());
		storageTable.setModel(model);
		storageTable.repaint();
		storageTable.updateUI();
	}
	
	public void filterCategory(int cat){
		this.currCat = cat;
		DefaultTableModel model = new DefaultTableModel(getData(currOrder), getRowNames());
		storageTable.setModel(model);
		storageTable.repaint();
		storageTable.updateUI();
	}

	/**
	 * 生成列名
	 * @return
	 */
	private String[] getRowNames() {
		if(rowname == null){
			rowname = new String[]{"ID", "编号", "品种", "名称", "进货价", "零售价", "库存", "上次进货"};
		}
		return rowname;
	}

	/**
	 * 生成表数据内容的二维数组
	 * @return
	 */
	private String[][] getData(String order) {
		ProductDao dao = new ProductDao();
		Vector<Product> list;
		if(order == null){
			list = dao.findProductByCategory(currCat);
		}else{
			list = dao.findProductByCategory(currCat, order);
		}
		String[][] ret = new String[list.size()][8];
		for (int idx = 0; idx < list.size(); idx++) {
			ret[idx][0] = list.get(idx).getProductId()+"";
			ret[idx][1] = list.get(idx).getProductNo();
			int cat = list.get(idx).getCatogery();
			String catStr = "";
			switch (cat) {
			case 1:
				catStr = "饮料";
				break;
			case 2:
				catStr = "食品";
				break;
			case 3:
				catStr = "酒类";
				break;
			case 4:
				catStr = "香烟";
				break;
			case 5:
				catStr = "零食";
				break;
			case 6:
				catStr = "生活用品";
				break;
			}
			ret[idx][2] = catStr;
			ret[idx][3] = list.get(idx).getName();
			ret[idx][4] = list.get(idx).getPurPrice()+"";
			ret[idx][5] = list.get(idx).getPrice()+"";
			ret[idx][6] = list.get(idx).getStorage()+"";
			ret[idx][7] = sdf.format(list.get(idx).getStockDate());
		}
		data = ret;
		return data;
	}

	public static void main(String[] args) {
		StorageFrame mf = new StorageFrame();
		mf.init();
		mf.setVisible(true);
	}
	
	public ClientContext getClientContext() {
		return clientContext;
	}

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

	public JScrollPane getStoragePanel() {
		return storagePanel;
	}

	public void setStoragePanel(JScrollPane storagePanel) {
		this.storagePanel = storagePanel;
	}

	public JTable getStorageTable() {
		return storageTable;
	}

	public void setStorageTable(JTable storageTable) {
		this.storageTable = storageTable;
	}

	public JTable getScoreTable() {
		return storageTable;
	}

	public void setScoreTable(JTable scoreTable) {
		this.storageTable = scoreTable;
	}

	public JComboBox getCatCombox() {
		return catCombox;
	}

	public void setCatCombox(JComboBox catCombox) {
		this.catCombox = catCombox;
	}

	public int getCurrCat() {
		return currCat;
	}

	public void setCurrCat(int currCat) {
		this.currCat = currCat;
	}

	public String getCurrOrder() {
		return currOrder;
	}

	public void setCurrOrder(String currOrder) {
		this.currOrder = currOrder;
	}

}
