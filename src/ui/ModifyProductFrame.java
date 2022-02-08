package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 更新商品信息窗口
 * @author xujinnan
 *
 */
public class ModifyProductFrame extends JFrame{
	
	private static final long serialVersionUID = 303139940819412480L;
	private ClientContext clientContext;
	private JTextField productNameText = new JTextField();
	private JTextField productNoText = new JTextField();
	private JTextField storageText = new JTextField();
	private JTextField purchasePriceText = new JTextField();
	private JTextField priceText = new JTextField();
	private JTextField alarmStorageText = new JTextField(); 
	private JComboBox catCom = new JComboBox();
	
	public ModifyProductFrame(){
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		init();
	}
	
	public void init(){
		this.setTitle("编辑商品信息");
		this.setSize(450, 320);
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
		JPanel p0 = new JPanel(new BorderLayout());
		p0.add(new JLabel("\n"), BorderLayout.NORTH);
		
		JPanel panel = new JPanel(new GridLayout(8, 3, 3, 5));
		catCom = new JComboBox();
		catCom.addItem("饮料");
		catCom.addItem("食品");
		catCom.addItem("酒类");
		catCom.addItem("香烟");
		catCom.addItem("零食");
		catCom.addItem("生活用品");
		panel.add(new JLabel(leftPadding("商品类型")));
		panel.add(catCom);
		panel.add(new JLabel(leftPadding("商品名称")));
		panel.add(productNameText);
		panel.add(new JLabel(leftPadding("商品编号")));
		panel.add(productNoText);
		panel.add(new JLabel(leftPadding("进货价")));
		panel.add(purchasePriceText);
		panel.add(new JLabel(leftPadding("零售价")));
		panel.add(priceText);
		panel.add(new JLabel(leftPadding("库存")));
		panel.add(storageText);
		panel.add(new JLabel(leftPadding("提醒阈值")));
		panel.add(alarmStorageText);
		//setMargin(new Insets(0, 0, 0, 160));
		
		p0.add(panel, BorderLayout.CENTER);
		p0.add(new JLabel("                             "), BorderLayout.EAST);
		JPanel btnPanel = new JPanel();
		JButton okBtn = new JButton("更新");
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.updateProduct();
				JOptionPane.showMessageDialog(ModifyProductFrame.this, "商品修改成功！","商品入库", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnPanel.add(okBtn);
		JButton canelBtn = new JButton("取消");
		canelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.showOrHideModifyProductFrame(false);
			}
		});
		btnPanel.add(canelBtn);
		p0.add(btnPanel, BorderLayout.SOUTH);
		
		return p0;
	}

	public ClientContext getClientContext() {
		return clientContext;
	}

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

	public JTextField getProductNameText() {
		return productNameText;
	}

	public void setProductNameText(JTextField productNameText) {
		this.productNameText = productNameText;
	}

	public JTextField getStorageText() {
		return storageText;
	}

	public void setStorageText(JTextField storageText) {
		this.storageText = storageText;
	}

	public JTextField getPurchasePriceText() {
		return purchasePriceText;
	}

	public void setPurchasePriceText(JTextField purchasePriceText) {
		this.purchasePriceText = purchasePriceText;
	}

	public JTextField getPriceText() {
		return priceText;
	}

	public void setPriceText(JTextField priceText) {
		this.priceText = priceText;
	}
	
	public JTextField getProductNoText() {
		return productNoText;
	}

	public void setProductNoText(JTextField productNoText) {
		this.productNoText = productNoText;
	}

	public JTextField getAlarmStorageText() {
		return alarmStorageText;
	}

	public void setAlarmStorageText(JTextField alarmStorageText) {
		this.alarmStorageText = alarmStorageText;
	}

	public JComboBox getCatCom() {
		return catCom;
	}

	public void setCatCom(JComboBox catCom) {
		this.catCom = catCom;
	}

	private String leftPadding(String str){
		return "                                  "+str;
	}
	
	public static void main(String[] args) {
		ModifyProductFrame npf = new ModifyProductFrame();
		npf.init();
		npf.setVisible(true);
		/* JFrame mai=new JFrame();  
         mai.setLayout(new BorderLayout());  
         JToolBar jtoolbar=new JToolBar();  
         JLabel jl=new JLabel("state");  
         jtoolbar.add(jl);  
         JPanel jpanel1=new JPanel();  
         JButton jb1=new JButton("North");  
           
         jpanel1.setPreferredSize(new Dimension(130, 50));//关键代码,设置JPanel的大小  
         jpanel1.add(jb1);  
          jpanel1.setBorder(BorderFactory.createEtchedBorder());  
         JButton jb2=new JButton("Center");  
           
           
         mai.add(jpanel1,BorderLayout.EAST);  
         mai.add(jb2,BorderLayout.CENTER);  
         mai.add(jtoolbar,BorderLayout.SOUTH);  
         mai.setSize(300, 400);  
         mai.setVisible(true);  
         mai.setDefaultCloseOperation(EXIT_ON_CLOSE);  */
	}
}
