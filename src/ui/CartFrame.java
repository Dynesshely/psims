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
 * ���ﳵ����
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
	/**��ͷ*/
	private String[] rowname;
	/**�����ݣ���ά���ݣ�*/
	private String[][] data;
	
	public CartFrame(){
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		init();
	}
	
	public void init(){
		this.setTitle("���ﳵ");
		this.setSize(480, 320);
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
		panel.add(createTopPanel(), BorderLayout.NORTH);
		panel.add(createStoragePanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * ��������򡢰�ť
	 * @return
	 */
	private Component createTopPanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("��Ʒ���"));
		productNumField = new JTextField(10);
		panel.add(productNumField);
		panel.add(new JLabel("����"));
		productQty = new JTextField(5);
		panel.add(productQty);
		addProductBtn = new JButton("ȷ��");
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
		/*JButton modifyBtn = new JButton("�޸�");
		modifyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(storageTable.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(CartFrame.this, "��ѡ��Ҫ��������Ʒ��","��Ʒ����", JOptionPane.WARNING_MESSAGE);
				}else{
					ProductDao dao = new ProductDao();
					int pid = Integer.parseInt(data[storageTable.getSelectedRow()][0]);//����ѡ���е��±꣬��data��ά������ȡ����Ӧ���У����е�һ��ΪID
					Product currProduct = dao.findProduct(pid);//����ID�����ݿ��ж�ȡ��Ʒ
					clientContext.setCurrProduct(currProduct);//���ÿ������еĵ�ǰ��Ʒʵ��
					clientContext.showOrHideModifyProductFrame(true);//��ʾ�޸���Ʒ����
				}
			}
		});
		panel.add(modifyBtn);*/
		
		JButton delBtn = new JButton("ɾ��");
		delBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(storageTable.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(CartFrame.this, "��ѡ��Ҫ��������Ʒ��","��Ʒ����", JOptionPane.WARNING_MESSAGE);
				}else{
					int val = JOptionPane.showConfirmDialog(CartFrame.this, "�Ƿ�ɾ������Ʒ��");
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
		
		JButton settleBtn = new JButton("����");
		settleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.settle();
			}
		});
		panel.add(settleBtn);
		
		JButton okBtn = new JButton("�ر�");
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
	 * ��ʼ���������
	 */
	public void initTableData(){
		String[] rowNames = getRowNames();
		String[][] data = getData(new Vector<Cart>());
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
	 * ��������
	 * @return
	 */
	private String[] getRowNames() {
		if(rowname == null){
			rowname = new String[]{"��Ʒ���", "Ʒ��", "����", "����", "����", "С��"};
		}
		return rowname;
	}

	/**
	 * ���ɱ��������ݵĶ�ά����
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
