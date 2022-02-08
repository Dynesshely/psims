package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import service.StorageAlarmService;

import entity.Product;

/**
 * 缺货商品列表窗口
 * @author xujinnan
 *
 */
public class StorageAlarmFrame extends JFrame{
	private static final long serialVersionUID = -8808883923263763897L;
	
	private ClientContext clientContext;
	private JScrollPane storagePanel;
	private JTable storageTable;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**表头*/
	private String[] rowname;
	/**表内容（二维数据）*/
	private String[][] data;
	
	public StorageAlarmFrame(){
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		init();
	}
	
	public void init(){
		this.setTitle("缺货提醒");
		this.setSize(400, 320);
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
		JLabel title = new JLabel("以下商品处于缺货状态，请尽快补充库存！");
		title.setFont(new Font("微软雅黑", 0, 16));
		title.setForeground(Color.red);
		panel.add(title, BorderLayout.NORTH);
		panel.add(createStoragePanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.SOUTH);
		return panel;
	}

	private Component createButtonPanel() {
		JPanel panel = new JPanel();
		
		JButton okBtn = new JButton("关闭");
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				StorageAlarmFrame.this.setVisible(false);
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
		String[][] data = getData();
		storageTable = new JTable(data, rowNames);
		//单元格居中显示
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
		storageTable.setDefaultRenderer(Object.class, tcr);
	}
	
	/**
	 * 刷新JTable的数据
	 */
	public void refreshTableData(){
		DefaultTableModel model = new DefaultTableModel(getData(), getRowNames());
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
			rowname = new String[]{"编号", "品种", "名称", "当前库存", "上次进货"};
		}
		return rowname;
	}

	/**
	 * 生成表数据内容的二维数组
	 * @return
	 */
	private String[][] getData() {
		StorageAlarmService service = new StorageAlarmService();
		Vector<Product> list;
		list = service.findAlarmProduct();
		String[][] ret = new String[list.size()][8];
		for (int idx = 0; idx < list.size(); idx++) {
			ret[idx][0] = list.get(idx).getProductNo();
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
			ret[idx][1] = catStr;
			ret[idx][2] = list.get(idx).getName();
			ret[idx][3] = list.get(idx).getStorage()+"";
			ret[idx][4] = sdf.format(list.get(idx).getStockDate());
		}
		data = ret;
		return data;
	}

	public static void main(String[] args) {
		StorageAlarmFrame mf = new StorageAlarmFrame();
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

}
