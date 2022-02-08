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
 * ������ʷ��¼����
 * @author xujinnan
 *
 */
public class SellHistoryFrame extends JFrame{
	private static final long serialVersionUID = -8808883923263763897L;
	
	private ClientContext clientContext;
	private JScrollPane sellHistoryPanel;
	private JTable sellHistoryTable;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**��ͷ*/
	private String[] rowname;
	/**�����ݣ���ά���ݣ�*/
	private String[][] data;
	
	public SellHistoryFrame(){
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		init();
	}
	
	public void init(){
		this.setTitle("���ۼ�¼��ѯ");
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
	 * ��ʼ���������
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
	 * ˢ��JTable������
	 */
	public void refreshTableData(){
		DefaultTableModel model = new DefaultTableModel(getTableData(), getRowNames());
		sellHistoryTable.setModel(model);
		sellHistoryTable.repaint();
		sellHistoryTable.updateUI();
	}

	/**
	 * ��������
	 * @return
	 */
	private String[] getRowNames() {
		if(rowname == null){
			rowname = new String[]{"��������", "Ʒ��", "����", "����", "�ܼ�"};
		}
		return rowname;
	}

	/**
	 * ���ɱ��������ݵĶ�ά����
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
