package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = -8808883923263763897L;
	
	private ClientContext clientContext;
	
	public MainFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
	}
	
	public void init(){
		this.setTitle("С���̵���������ϵͳ");
		this.setSize(550, 400);
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
		panel.add(createFunctionPanel(), BorderLayout.CENTER);
		return panel;
	}

	private Component createFunctionPanel() {
		Image image=new ImageIcon(MainFrame.class.getResource("icon/bg.png")).getImage(); 
		JPanel panel = new BackgroundPanel(image);
		Font font16=new Font("΢���ź�", 1, 16);
		
		JButton sale=new JButton("��  ��",new ImageIcon(MainFrame.class.getResource("icon/sell.png")));
		sale.setHorizontalTextPosition(SwingConstants.CENTER);
		sale.setVerticalTextPosition(SwingConstants.BOTTOM);
		sale.setBorderPainted(false);
		sale.setFocusPainted(false);
		sale.setContentAreaFilled(false);
		sale.setFocusable(true);
		sale.setMargin(new Insets(180, 10, 0, 30));
		sale.setFont(font16);
		sale.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.showSellFrame();
			}
		});
		panel.add(sale);
		
		JButton purchases=new JButton("��  ��",new ImageIcon(MainFrame.class.getResource("icon/inventory.png")));
		purchases.setHorizontalTextPosition(SwingConstants.CENTER);
		purchases.setVerticalTextPosition(SwingConstants.BOTTOM);
		purchases.setBorderPainted(false);
		purchases.setFocusPainted(false);
		purchases.setContentAreaFilled(false);
		purchases.setFocusable(true);
		purchases.setMargin(new Insets(180, 10, 0, 30));
		purchases.setFont(font16);
		purchases.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.showOrHidePurchaseFrame(true);
			}
		});
		panel.add(purchases);
		
		JButton stock=new JButton("��  ��",new ImageIcon(MainFrame.class.getResource("icon/storage.png")));
		stock.setHorizontalTextPosition(SwingConstants.CENTER);
		stock.setVerticalTextPosition(SwingConstants.BOTTOM);
		stock.setBorderPainted(false);
		stock.setFocusPainted(false);
		stock.setContentAreaFilled(false);
		stock.setFocusable(true);
		stock.setMargin(new Insets(180, 10, 0, 30));
		stock.setFont(font16);
		stock.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.showOrHideStorageFrame(true);
			}
		});
		panel.add(stock);
		
		JButton sellHistoryBtn=new JButton("���ۼ�¼",new ImageIcon(MainFrame.class.getResource("icon/summary.png")));
		sellHistoryBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		sellHistoryBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		sellHistoryBtn.setBorderPainted(false);
		sellHistoryBtn.setFocusPainted(false);
		sellHistoryBtn.setContentAreaFilled(false);
		sellHistoryBtn.setFocusable(true);
		sellHistoryBtn.setMargin(new Insets(180, 10, 0, 10));
		sellHistoryBtn.setFont(font16);
		sellHistoryBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.showSellHistoryFrame();
			}
		});
		panel.add(sellHistoryBtn);
		
		JButton setting=new JButton("�޸�����",new ImageIcon(MainFrame.class.getResource("icon/setting.png")));
		setting.setHorizontalTextPosition(SwingConstants.CENTER);
		setting.setVerticalTextPosition(SwingConstants.BOTTOM);
		setting.setBorderPainted(false);
		setting.setFocusPainted(false);
		setting.setContentAreaFilled(false);
		setting.setFocusable(true);
		setting.setMargin(new Insets(180, 10, 0, 10));
		setting.setFont(font16);
		setting.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.showModifyPwdFrame(true);
			}
		});
		panel.add(setting);
		
		return panel;
	}

	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		mf.init();
		mf.setVisible(true);
	}

	public ClientContext getClientContext() {
		return clientContext;
	}

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

}