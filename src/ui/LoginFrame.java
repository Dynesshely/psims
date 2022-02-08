package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame {


	private static final long serialVersionUID = 8486147879661945934L;

	public LoginFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
	}

	/** 初始化显示界面(绘制全部界面) */
	private void init() {
		setTitle("小型商店进销存管理系统");
		setSize(300, 220);
		setContentPane(createContentPane());
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		/*addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				clientContext.exit(LoginFrame.this);
			}
		});*/
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
		p.add(BorderLayout.NORTH, new JLabel("小型商店进销存管理系统", JLabel.CENTER));
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
		JPanel p = new JPanel(new GridLayout(3, 1, 0, 6));
		p.add(createIdPane());
		p.add(createPwdPane());
		return p;
	}

	private JPanel createIdPane() {
		JPanel p = new JPanel(new BorderLayout(5, 0));
		return p;
	}

	private JPanel createPwdPane() {
		JPanel p = new JPanel(new BorderLayout(5, 0));
		p.add(BorderLayout.WEST, new JLabel("密    码:"));
		pwdField = new JPasswordField(20);
		pwdField.enableInputMethods(true);
		p.add(BorderLayout.CENTER, pwdField);
		return p;
	}
	
	private JPanel createBtnPane() {
		JPanel p = new JPanel(new FlowLayout());
		JButton login = new JButton("登入");
		JButton cancel = new JButton("退出");
		p.add(login);
		p.add(cancel);
		
		// 设置默认按钮
		getRootPane().setDefaultButton(login);

		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 调用控制器的登录功能
				clientContext.login();
				// 在执行这个方法之前, 必须注入clientContext实例
				// 否则会出现NullPointerException
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ui.LoginFrame.this.clientContext.exit(ui.LoginFrame.this);
				// clientContext.exit(LoginFrame.this);
			}
		});

		return p;
	}

	/** ClientContext 客户端上下文, 这里是客户端控制器 */
	private ClientContext clientContext;// 默认值是null

	/**
	 * 为clientContext赋值, 使clientContext引用的值, 不再为null, 必须依赖的属性值注入方法 缩写: IOC
	 */
	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

	private JPasswordField pwdField;
	private JComboBox userType;

	public String getUserPwd() {
		char[] pwd = pwdField.getPassword();
		return new String(pwd);
	}
	
	public int getUserType(){
		return userType.getSelectedIndex();
	}
	
	public JPasswordField getPwdField() {
		return pwdField;
	}

	public static void main(String[] args) {
		LoginFrame frame = new LoginFrame();
		frame.setVisible(true);
	}
}
