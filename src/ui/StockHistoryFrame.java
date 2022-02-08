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
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.StockHistoryDao;
import entity.StockHistory;

public class StockHistoryFrame extends JFrame{
	private static final long serialVersionUID = -8808883923263763897L;
	
	private ClientContext clientContext;
	private JScrollPane stockHistoryPanel;
	private JTable stockHistoryTable;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**��ͷ*/
	private String[] rowname;
	/**�����ݣ���ά���ݣ�*/
	private String[][] data;
	
	public StockHistoryFrame(){
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		init();
	}
	
	public void init(){
		this.setTitle("������¼��ѯ");
		this.setSize(420, 320);
		this.setContentPane(createContentPane());
		int windowWidth = this.getWidth(); //��ô��ڿ�
		int windowHeight = this.getHeight(); //��ô��ڸ�
		Toolkit kit = Toolkit.getDefaultToolkit(); //���幤�߰�
		Dimension screenSize = kit.getScreenSize(); //��ȡ��Ļ�ĳߴ�
		int screenWidth = screenSize.width; //��ȡ��Ļ�Ŀ�
		int screenHeight = screenSize.height; //��ȡ��Ļ�ĸ�
		this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//���ô��ھ�����ʾ
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
		JButton okBtn = new JButton("�ر�");
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.showOrHideStockHistory(false);
			}
		});
		panel.add(okBtn);
		return panel;
	}

	private Component createStoragePanel() {
		stockHistoryPanel = new JScrollPane();
		initTableData();
		stockHistoryPanel.setViewportView(stockHistoryTable);
		return stockHistoryPanel;
	}
	
	/**
	 * ��ʼ���������
	 */
	public void initTableData(){
		String[] rowNames = getRowNames();
		String[][] data = getTableData();
		stockHistoryTable = new JTable(data, rowNames);
		//��Ԫ�������ʾ
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
		stockHistoryTable.setDefaultRenderer(Object.class, tcr);
		stockHistoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //��ѡ
		stockHistoryTable.isCellEditable(1, 1);
	}
	
	/**
	 * ˢ��JTable������
	 */
	public void refreshTableData(){
		DefaultTableModel model = new DefaultTableModel(getTableData(), getRowNames());
		stockHistoryTable.setModel(model);
		stockHistoryTable.repaint();
		stockHistoryTable.updateUI();
	}

	/**
	 * ��������
	 * @return
	 */
	private String[] getRowNames() {
		if(rowname == null){
			rowname = new String[]{"��������", "Ʒ��", "����", "��������"};
		}
		return rowname;
	}

	/**
	 * ���ɱ��������ݵĶ�ά����
	 * @return
	 */
	private String[][] getTableData() {
		StockHistoryDao dao = new StockHistoryDao();
		Vector<StockHistory> list = dao.findAllHistory();
		String[][] ret = new String[list.size()][8];
		for (int idx = 0; idx < list.size(); idx++) {
			ret[idx][0] = sdf.format(list.get(idx).getStockDate());
			int cat = list.get(idx).getProduct().getCatogery();
			String catStr = "";
			switch (cat) {
			case 1:
				catStr = "����";
				break;
			case 2:
				catStr = "ʳƷ";
				break;
			case 3:
				catStr = "����";
				break;
			case 4:
				catStr = "����";
				break;
			case 5:
				catStr = "��ʳ";
				break;
			case 6:
				catStr = "������Ʒ";
				break;
			}
			ret[idx][1] = catStr;
			ret[idx][2] = list.get(idx).getProduct().getName();
			ret[idx][3] = list.get(idx).getQuantity()+"";
		}
		data = ret;
		return data;
	}

	public static void main(String[] args) {
		StockHistoryFrame mf = new StockHistoryFrame();
		mf.init();
		mf.setVisible(true);
	}
	
	public ClientContext getClientContext() {
		return clientContext;
	}

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

	public JScrollPane getStockHistoryPanel() {
		return stockHistoryPanel;
	}

	public void setStockHistoryPanel(JScrollPane stockHistoryPanel) {
		this.stockHistoryPanel = stockHistoryPanel;
	}

	public JTable getStockHistoryTable() {
		return stockHistoryTable;
	}

	public void setStockHistoryTable(JTable stockHistoryTable) {
		this.stockHistoryTable = stockHistoryTable;
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
