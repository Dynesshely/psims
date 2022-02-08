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

	/** ��ʼ����ʾ����(����ȫ������) */
	private void init() {
		setTitle("С���̵���������ϵͳ");
		setSize(300, 220);
		setContentPane(createContentPane());
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		/*addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				clientContext.exit(LoginFrame.this);
			}
		});*/
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
		p.add(BorderLayout.NORTH, new JLabel("С���̵���������ϵͳ", JLabel.CENTER));
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
		p.add(BorderLayout.WEST, new JLabel("��    ��:"));
		pwdField = new JPasswordField(20);
		pwdField.enableInputMethods(true);
		p.add(BorderLayout.CENTER, pwdField);
		return p;
	}
	
	private JPanel createBtnPane() {
		JPanel p = new JPanel(new FlowLayout());
		JButton login = new JButton("����");
		JButton cancel = new JButton("�˳�");
		p.add(login);
		p.add(cancel);
		
		// ����Ĭ�ϰ�ť
		getRootPane().setDefaultButton(login);

		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���ÿ������ĵ�¼����
				clientContext.login();
				// ��ִ���������֮ǰ, ����ע��clientContextʵ��
				// ��������NullPointerException
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

	/** ClientContext �ͻ���������, �����ǿͻ��˿����� */
	private ClientContext clientContext;// Ĭ��ֵ��null

	/**
	 * ΪclientContext��ֵ, ʹclientContext���õ�ֵ, ����Ϊnull, ��������������ֵע�뷽�� ��д: IOC
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
