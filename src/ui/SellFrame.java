package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import dao.ProductDao;
import entity.Product;

public class SellFrame extends JFrame {

	private static final long serialVersionUID = 8486147879661945934L;
	private JTextField productQty;
	private JTextField productNumField;
	private JLabel productInfo;
	private ProductDao productDao = new ProductDao();

	public SellFrame() {
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		init();
	}

	private void init() {
		setTitle("����");
		setSize(310, 220);
		setContentPane(createContentPane());
		int windowWidth = this.getWidth(); //��ô��ڿ�
		int windowHeight = this.getHeight(); //��ô��ڸ�
		Toolkit kit = Toolkit.getDefaultToolkit(); //���幤�߰�
		Dimension screenSize = kit.getScreenSize(); //��ȡ��Ļ�ĳߴ�
		int screenWidth = screenSize.width; //��ȡ��Ļ�Ŀ�
		int screenHeight = screenSize.height; //��ȡ��Ļ�ĸ�
		this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//���ô��ھ�����ʾ
	}

	/** �������ڿ��м��������� */
	private JPanel createContentPane() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(BorderLayout.NORTH, new JLabel("��Ʒ����", JLabel.CENTER));
		p.add(BorderLayout.CENTER, createCenterPane());
		p.add(BorderLayout.SOUTH, createBtnPane());
		p.setBorder(new EmptyBorder(6, 45, 6, 45));
		return p;
	}

	private JPanel createCenterPane() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(BorderLayout.NORTH, createIdPwdPane());
		p.setBorder(new EmptyBorder(6, 6, 6, 6));
		return p;
	}

	private JPanel createIdPwdPane() {
		JPanel p = new JPanel(new GridLayout(2, 2, 0, 6));
		p.add(createProductNoPanel());
		p.add(createProductQtyPsanel());
		return p;
	}

	/**
	 * ������Ʒ������� 
	 * @return
	 */
	private Component createProductQtyPsanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("��������:"));
		productQty = new JTextField(10);
		panel.add(productQty);
		return panel;
	}

	/**
	 * ������Ʒ�������򼰱�ǩ���
	 * @return
	 */
	private JPanel createProductNoPanel() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(new JLabel("  "), BorderLayout.NORTH);
		p.add(BorderLayout.WEST, new JLabel("��Ʒ���:"));
		productNumField = new JTextField(20);
		Document doc = productNumField.getDocument();
		doc.addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				Product p = productDao.findProductByNo(productNumField.getText());
				if(p != null){
					productInfo.setText(leftPadding(p.getName()));
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				Product p = productDao.findProductByNo(productNumField.getText());
				if(p != null){
					productInfo.setText(leftPadding(p.getName()));
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
		productInfo = new JLabel(leftPadding("��������Ʒ���"));
		p.add(BorderLayout.CENTER, productNumField);
		p.add(BorderLayout.SOUTH, productInfo);
		return p;
	}
	
	private JPanel createBtnPane() {
		JPanel p = new JPanel(new FlowLayout());
		JButton sell = new JButton("С��");
		JButton settle = new JButton("����");
		JButton cancel = new JButton("�ر�");
		p.add(sell);
		p.add(settle);
		p.add(cancel);
		
		sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientContext.sell();
				productInfo.setText(leftPadding("��������Ʒ���"));
			}
		});
		
		settle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.settle();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SellFrame.this.setVisible(false);
			}
		});

		return p;
	}

	private ClientContext clientContext;// Ĭ��ֵ��null

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

	public JLabel getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(JLabel productInfo) {
		this.productInfo = productInfo;
	}

	public JTextField getProductNumField() {
		return productNumField;
	}

	public void setProductNumField(JTextField productNumField) {
		this.productNumField = productNumField;
	}

	public ClientContext getClientContext() {
		return clientContext;
	}
	
	private String leftPadding(String str){
		return "                   "+str;
	}
	
	public JTextField getProductQty() {
		return productQty;
	}

	public void setProductQty(JTextField productQty) {
		this.productQty = productQty;
	}

	public static void main(String[] args) {
		SellFrame frame = new SellFrame();
		frame.setVisible(true);
	}
}
