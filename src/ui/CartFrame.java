package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.ProductDao;
import entity.Cart;
import entity.Product;

/**
 * 购物车窗口
 * @author xujinnan
 *
 */
public class CartFrame extends JFrame{
	private static final long serialVersionUID = -8808883923263763897L;
	
	private ClientContext clientContext;
	private JScrollPane storagePanel;
	private JTable storageTable;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private JTextField productQty;
	private JTextField productNumField;
	private JButton addProductBtn;
	/**表头*/
	private String[] rowname;
	/**表内容（二维数据）*/
	private String[][] data;
	
	public CartFrame(){
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		init();
	}
	
	public void init(){
		this.setTitle("购物车");
		this.setSize(480, 320);
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
		panel.add(createTopPanel(), BorderLayout.NORTH);
		panel.add(createStoragePanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * 顶部输入框、按钮
	 * @return
	 */
	private Component createTopPanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("商品编号"));
		productNumField = new JTextField(10);
		panel.add(productNumField);
		panel.add(new JLabel("数量"));
		productQty = new JTextField(5);
		panel.add(productQty);
		addProductBtn = new JButton("确定");
		panel.add(addProductBtn);
		addProductBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.addCart();
			}
		});
		return panel;
	}

	private Component createButtonPanel() {
		JPanel panel = new JPanel();
		/*JButton modifyBtn = new JButton("修改");
		modifyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(storageTable.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(CartFrame.this, "请选择要操作的商品！","商品管理", JOptionPane.WARNING_MESSAGE);
				}else{
					ProductDao dao = new ProductDao();
					int pid = Integer.parseInt(data[storageTable.getSelectedRow()][0]);//根据选中行的下标，从data二维数组中取到对应的行，其中第一列为ID
					Product currProduct = dao.findProduct(pid);//根据ID从数据库中读取商品
					clientContext.setCurrProduct(currProduct);//设置控制器中的当前商品实例
					clientContext.showOrHideModifyProductFrame(true);//显示修改商品窗口
				}
			}
		});
		panel.add(modifyBtn);*/
		
		JButton delBtn = new JButton("删除");
		delBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(storageTable.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(CartFrame.this, "请选择要操作的商品！","商品管理", JOptionPane.WARNING_MESSAGE);
				}else{
					int val = JOptionPane.showConfirmDialog(CartFrame.this, "是否删除该商品？");
					if (val == JOptionPane.YES_OPTION) {
						int deleteRow = storageTable.getSelectedRow();
						clientContext.getSellProducts().remove(deleteRow);
						String[][] tmp = new String[data.length-1][6];
						for(int i=0; i<data.length; i++){
							if(i == deleteRow){
								continue;
							}else if(i > deleteRow){
								for(int j=0; j<6; j++){
									tmp[i-1][j] = data[i][j];
								}
							}else{
								for(int j=0; j<6; j++){
									tmp[i][j] = data[i][j];
								}
							}
						}
						data = tmp;
						DefaultTableModel model = new DefaultTableModel(data, getRowNames());
						storageTable.setModel(model);
						storageTable.repaint();
						storageTable.updateUI();
					}
				}
			}
		});
		panel.add(delBtn);
		
		JButton settleBtn = new JButton("结账");
		settleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.settle();
			}
		});
		panel.add(settleBtn);
		
		JButton okBtn = new JButton("关闭");
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CartFrame.this.setVisible(false);
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
		String[][] data = getData(new Vector<Cart>());
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
	public void refreshTableData(List<Cart> list){
		Vector<Cart> v = new Vector<Cart>();
		for (Cart cart : list) {
			v.add(cart);
		}
		DefaultTableModel model = new DefaultTableModel(getData(v), getRowNames());
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
			rowname = new String[]{"商品编号", "品种", "名称", "数量", "单价", "小计"};
		}
		return rowname;
	}

	/**
	 * 生成表数据内容的二维数组
	 * @return
	 */
	private String[][] getData(Vector<Cart> list) {
		String[][] ret = new String[list.size()][6];
		for (int idx = 0; idx < list.size(); idx++) {
			ret[idx][0] = list.get(idx).getProduct().getProductNo();
			int cat = list.get(idx).getProduct().getCatogery();
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
			ret[idx][1] = catStr;
			ret[idx][2] = list.get(idx).getProduct().getName();
			ret[idx][3] = list.get(idx).getQuantity()+"";
			ret[idx][4] = list.get(idx).getProduct().getPrice()+"";
			ret[idx][5] = list.get(idx).getAmt()+"";
		}
		data = ret;
		return data;
	}

	public static void main(String[] args) {
		CartFrame mf = new CartFrame();
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

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public JTextField getProductQty() {
		return productQty;
	}

	public void setProductQty(JTextField productQty) {
		this.productQty = productQty;
	}

	public JTextField getProductNumField() {
		return productNumField;
	}

	public void setProductNumField(JTextField productNumField) {
		this.productNumField = productNumField;
	}

	public JButton getAddProductBtn() {
		return addProductBtn;
	}

	public void setAddProductBtn(JButton addProductBtn) {
		this.addProductBtn = addProductBtn;
	}

	public String[] getRowname() {
		return rowname;
	}

	public void setRowname(String[] rowname) {
		this.rowname = rowname;
	}

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}

}
