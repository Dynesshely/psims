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
	/**��ͷ*/
	private String[] rowname;
	/**�����ݣ���ά���ݣ�*/
	private String[][] data;
	private int currCat = 1;
	private String currOrder = "desc";
	
	public StorageFrame(){
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		init();
	}
	
	public void init(){
		this.setTitle("����ѯ");
		this.setSize(600, 320);
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
		catCombox = new JComboBox();
		panel.add(catCombox);
		catCombox.addItem("����");
		catCombox.addItem("ʳƷ");
		catCombox.addItem("����");
		catCombox.addItem("����");
		catCombox.addItem("��ʳ");
		catCombox.addItem("������Ʒ");
		catCombox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.filterCategory();
			}
		});
		JButton ascBtn = new JButton("����");
		ascBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.orderStorageData("asc");
			}
		});
		panel.add(ascBtn);
		JButton descBtn = new JButton("����");
		descBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.orderStorageData("desc");
			}
		});
		panel.add(descBtn);
		JButton modifyBtn = new JButton("�޸�");
		modifyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(storageTable.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(StorageFrame.this, "��ѡ��Ҫ��������Ʒ��","��Ʒ����", JOptionPane.WARNING_MESSAGE);
				}else{
					ProductDao dao = new ProductDao();
					int pid = Integer.parseInt(data[storageTable.getSelectedRow()][0]);//����ѡ���е��±꣬��data��ά������ȡ����Ӧ���У����е�һ��ΪID
					Product currProduct = dao.findProduct(pid);//����ID�����ݿ��ж�ȡ��Ʒ
					clientContext.setCurrProduct(currProduct);//���ÿ������еĵ�ǰ��Ʒʵ��
					clientContext.showOrHideModifyProductFrame(true);//��ʾ�޸���Ʒ����
				}
			}
		});
		panel.add(modifyBtn);
		
		JButton delBtn = new JButton("ɾ��");
		delBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(storageTable.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(StorageFrame.this, "��ѡ��Ҫ��������Ʒ��","��Ʒ����", JOptionPane.WARNING_MESSAGE);
				}else{
					int val = JOptionPane.showConfirmDialog(StorageFrame.this, "�Ƿ�ɾ������Ʒ��");
					if (val == JOptionPane.YES_OPTION) {
						int pid = Integer.parseInt(data[storageTable.getSelectedRow()][0]);//����ѡ���е��±꣬��data��ά������ȡ����Ӧ���У����е�һ��ΪID
						JOptionPane.showMessageDialog(StorageFrame.this, "��Ʒ��ɾ����","��Ʒ����", JOptionPane.INFORMATION_MESSAGE);
						clientContext.deleteProduct(pid);
					}
				}
			}
		});
		panel.add(delBtn);
		
		JButton okBtn = new JButton("�ر�");
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
	 * ��ʼ���������
	 */
	public void initTableData(){
		String[] rowNames = getRowNames();
		String[][] data = getData(null);
		storageTable = new JTable(data, rowNames);
		//��Ԫ�������ʾ
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
		storageTable.setDefaultRenderer(Object.class, tcr);
		storageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //��ѡ
	}
	
	/**
	 * ˢ��JTable������
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
	 * ��������
	 * @return
	 */
	private String[] getRowNames() {
		if(rowname == null){
			rowname = new String[]{"ID", "���", "Ʒ��", "����", "������", "���ۼ�", "���", "�ϴν���"};
		}
		return rowname;
	}

	/**
	 * ���ɱ��������ݵĶ�ά����
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
