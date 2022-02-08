package ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.ProductDao;
import entity.Product;
import entity.ProductOption;

public class PurchaseFrame extends JFrame{
	
	private static final long serialVersionUID = 3743009130266090272L;
	private JTextField purNumbers;
	private ClientContext clientContext;
	private JComboBox catCombox;
	private ProductComboBox productCombox;
	private JButton okBtn;
	
	public PurchaseFrame(){
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		init();
	}

	/**
	 * 初始化方法
	 */
	private void init() {
		this.setTitle("进货管理");
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

	/**
	 * 创建主面板
	 * @return
	 */
	private Container createContentPane() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(7,4));
		
		JLabel top=new JLabel("进货信息登记");
		JPanel jp1=new JPanel();
		jp1.add(top);
		mainPanel.add(jp1);
		
		JLabel category=new JLabel("商品类型");
		
		catCombox = new JComboBox();
		catCombox.addItem("饮料");
		catCombox.addItem("食品");
		catCombox.addItem("酒类");
		catCombox.addItem("香烟");
		catCombox.addItem("零食");
		catCombox.addItem("生活用品");
		catCombox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.correlationCombox();
			}
		});
	    JPanel jp2=new JPanel();
	    jp2.add(category);
	    jp2.add(catCombox);
	    mainPanel.add(jp2);
		
		productCombox = new ProductComboBox(getOption(1));//productCombox.setModel(aModel)
		productCombox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.setCurrSelectedProduct();
			}
		});
//        product.setModel(aModel);
        JLabel name=new JLabel("商品名称");
        JPanel jp3=new JPanel();
        jp3.add(name);
        jp3.add(productCombox);
        mainPanel.add(jp3);
        
        JPanel jp7=new JPanel();
        mainPanel.setVisible(true);
        mainPanel.add(jp7);
 		 
        JLabel number=new JLabel("请输入进货数量");
        purNumbers=new JTextField(12);
        JPanel jp4=new JPanel();
        jp4.add(number);
        jp4.add(purNumbers);
        mainPanel.add(jp4);
 		
		JPanel jp5=new JPanel();
		mainPanel.add(jp5);
		
        okBtn = new JButton("确定");
        okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.purchase();
			}
		});
 		JButton cancelBtn = new JButton("取消");
 		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.showOrHidePurchaseFrame(false);
			}
		});
 		JButton addBtn = new JButton("新增商品");
 		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.showOrHideNewProductFrame(true);
			}
		});
 		JButton stockHistoryBtn = new JButton("进货记录");
 		stockHistoryBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.showOrHideStockHistory(true);
			}
		});
 		JPanel jp6=new JPanel();
 		jp6.add(okBtn);
 		jp6.add(cancelBtn);
 		jp6.add(addBtn);
 		jp6.add(stockHistoryBtn);
 		mainPanel.add(jp6);
		return mainPanel;
	}
	
	/**
	 * 取下拉框Key-Value集合
	 * @param category
	 * @return
	 */
	public Vector<ProductOption> getOption(int category){
		ProductDao dao = new ProductDao();
		Vector<ProductOption> ret = new Vector<ProductOption>();
		Vector<Product> ps = dao.findProductByCategory(category);
		if(ps == null || ps.size() == 0){
			ProductOption po = new ProductOption();
			po.setValue(-1);
			po.setText("该分类下无商品");
			ret.add(po);
		}
		for (Product p : ps) {
			ProductOption po = new ProductOption();
			po.setValue(p.getProductId());
			po.setText(p.getName());
			ret.add(po);
		}
		return ret;
	}

	public static void main(String[] args) {
		PurchaseFrame pf = new PurchaseFrame();
		pf.init();
		pf.setVisible(true);
	}

	public JTextField getPurNumbers() {
		return purNumbers;
	}

	public void setPurNumbers(JTextField purNumbers) {
		this.purNumbers = purNumbers;
	}

	public ClientContext getClientContext() {
		return clientContext;
	}

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

	public JComboBox getCatCombox() {
		return catCombox;
	}

	public void setCatCombox(JComboBox catCombox) {
		this.catCombox = catCombox;
	}

	public ProductComboBox getProductCombox() {
		return productCombox;
	}

	public void setProductCombox(ProductComboBox productCombox) {
		this.productCombox = productCombox;
	}

	public JButton getOkBtn() {
		return okBtn;
	}

	public void setOkBtn(JButton okBtn) {
		this.okBtn = okBtn;
	}
	
}
