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
		setTitle("销售");
		setSize(310, 220);
		setContentPane(createContentPane());
		int windowWidth = this.getWidth(); //获得窗口宽
		int windowHeight = this.getHeight(); //获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
		Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
		int screenWidth = screenSize.width; //获取屏幕的宽
		int screenHeight = screenSize.height; //获取屏幕的高
		this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
	}

	/** 创建窗口框中间的内容面板 */
	private JPanel createContentPane() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(BorderLayout.NORTH, new JLabel("商品销售", JLabel.CENTER));
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
	 * 创建商品数量面板 
	 * @return
	 */
	private Component createProductQtyPsanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("销售数量:"));
		productQty = new JTextField(10);
		panel.add(productQty);
		return panel;
	}

	/**
	 * 创建商品编号输入框及标签面板
	 * @return
	 */
	private JPanel createProductNoPanel() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(new JLabel("  "), BorderLayout.NORTH);
		p.add(BorderLayout.WEST, new JLabel("商品编号:"));
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
		productInfo = new JLabel(leftPadding("请输入商品编号"));
		p.add(BorderLayout.CENTER, productNumField);
		p.add(BorderLayout.SOUTH, productInfo);
		return p;
	}
	
	private JPanel createBtnPane() {
		JPanel p = new JPanel(new FlowLayout());
		JButton sell = new JButton("小计");
		JButton settle = new JButton("结账");
		JButton cancel = new JButton("关闭");
		p.add(sell);
		p.add(settle);
		p.add(cancel);
		
		sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientContext.sell();
				productInfo.setText(leftPadding("请输入商品编号"));
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

	private ClientContext clientContext;// 默认值是null

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
