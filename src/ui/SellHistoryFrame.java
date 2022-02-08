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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.SellHistoryDao;
import entity.SellHistory;

/**
 * 销售历史记录窗口
 * @author xujinnan
 *
 */
public class SellHistoryFrame extends JFrame{
	private static final long serialVersionUID = -8808883923263763897L;
	
	private ClientContext clientContext;
	private JScrollPane sellHistoryPanel;
	private JTable sellHistoryTable;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**表头*/
	private String[] rowname;
	/**表内容（二维数据）*/
	private String[][] data;
	
	public SellHistoryFrame(){
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		init();
	}
	
	public void init(){
		this.setTitle("销售记录查询");
		this.setSize(420, 320);
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
		JButton okBtn = new JButton("关闭");
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SellHistoryFrame.this.setVisible(false);
			}
		});
		panel.add(okBtn);
		return panel;
	}

	private Component createStoragePanel() {
		sellHistoryPanel = new JScrollPane();
		initTableData();
		sellHistoryPanel.setViewportView(sellHistoryTable);
		return sellHistoryPanel;
	}
	
	/**
	 * 初始化表格数据
	 */
	public void initTableData(){
		String[] rowNames = getRowNames();
		String[][] data = getTableData();
		sellHistoryTable = new JTable(data, rowNames);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
		sellHistoryTable.setDefaultRenderer(Object.class, tcr);
	}
	
	/**
	 * 刷新JTable的数据
	 */
	public void refreshTableData(){
		DefaultTableModel model = new DefaultTableModel(getTableData(), getRowNames());
		sellHistoryTable.setModel(model);
		sellHistoryTable.repaint();
		sellHistoryTable.updateUI();
	}

	/**
	 * 生成列名
	 * @return
	 */
	private String[] getRowNames() {
		if(rowname == null){
			rowname = new String[]{"销售日期", "品种", "名称", "数量", "总价"};
		}
		return rowname;
	}

	/**
	 * 生成表数据内容的二维数组
	 * @return
	 */
	private String[][] getTableData() {
		SellHistoryDao dao = new SellHistoryDao();
		Vector<SellHistory> list = dao.findAllHistory();
		String[][] ret = new String[list.size()][8];
		for (int idx = 0; idx < list.size(); idx++) {
			ret[idx][0] = sdf.format(list.get(idx).getSellDate());
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
			ret[idx][4] = list.get(idx).getProduct().getPrice() * list.get(idx).getQuantity() + "";
		}
		data = ret;
		return data;
	}

	public static void main(String[] args) {
		SellHistoryFrame mf = new SellHistoryFrame();
		mf.init();
		mf.setVisible(true);
	}
	
	public ClientContext getClientContext() {
		return clientContext;
	}

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

	public JScrollPane getSellHistoryPanel() {
		return sellHistoryPanel;
	}

	public void setSellHistoryPanel(JScrollPane sellHistoryPanel) {
		this.sellHistoryPanel = sellHistoryPanel;
	}

	public JTable getSellHistoryTable() {
		return sellHistoryTable;
	}

	public void setSellHistoryTable(JTable sellHistoryTable) {
		this.sellHistoryTable = sellHistoryTable;
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
