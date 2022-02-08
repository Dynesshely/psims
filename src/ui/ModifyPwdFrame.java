package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ModifyPwdFrame extends JFrame {


	private static final long serialVersionUID = 8486147879661945934L;

	public ModifyPwdFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
	}

	private void init() {
		setTitle("修改密码");
		setSize(300, 220);
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
		p.add(BorderLayout.NORTH, new JLabel("修改密码", JLabel.CENTER));
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
		JPanel p = new JPanel(new GridLayout(5, 1, 0, 6));
		p.add(createIdPane());
		p.add(createPwdPane());
		p.add(createPwdPane2());
		return p;
	}

	private JPanel createIdPane() {
		JPanel p = new JPanel(new BorderLayout(5, 0));
		p.add(BorderLayout.WEST, new JLabel("    原密码:"));
		orgPwd = new JPasswordField(20);
		orgPwd.enableInputMethods(true);
		p.add(BorderLayout.CENTER, orgPwd);
		return p;
	}

	private JPanel createPwdPane() {
		JPanel p = new JPanel(new BorderLayout(5, 0));
		p.add(BorderLayout.WEST, new JLabel("    新密码:"));
		pwdField = new JPasswordField(20);
		pwdField.enableInputMethods(true);
		p.add(BorderLayout.CENTER, pwdField);
		return p;
	}
	
	private JPanel createPwdPane2() {
		JPanel p = new JPanel(new BorderLayout(5, 0));
		p.add(BorderLayout.WEST, new JLabel("再次输入:"));
		repeatPwd = new JPasswordField(20);
		repeatPwd.enableInputMethods(true);
		p.add(BorderLayout.CENTER, repeatPwd);
		return p;
	}
	
	private JPanel createBtnPane() {
		JPanel p = new JPanel(new FlowLayout());
		JButton login = new JButton("修改");
		JButton cancel = new JButton("取消");
		p.add(login);
		p.add(cancel);
		
		getRootPane().setDefaultButton(login);

		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientContext.updatePwd();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModifyPwdFrame.this.setVisible(false);
			}
		});

		return p;
	}

	private ClientContext clientContext;// 默认值是null

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

	private JPasswordField pwdField;
	private JPasswordField orgPwd;
	private JPasswordField repeatPwd;

	public JPasswordField getPwdField() {
		return pwdField;
	}

	public void setPwdField(JPasswordField pwdField) {
		this.pwdField = pwdField;
	}

	public JPasswordField getOrgPwd() {
		return orgPwd;
	}

	public void setOrgPwd(JPasswordField orgPwd) {
		this.orgPwd = orgPwd;
	}

	public JPasswordField getRepeatPwd() {
		return repeatPwd;
	}

	public void setRepeatPwd(JPasswordField repeatPwd) {
		this.repeatPwd = repeatPwd;
	}

	public ClientContext getClientContext() {
		return clientContext;
	}

}
