package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * ���ݿ⹤����
 * @author xujinnan
 *
 */
public class DataBaseUtil {

	//���Է���
	public static void main(String[] args) {
		try {
			Connection conn = getConnection();
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from product;");// ��ѯ����
			while (rs.next()) {// ����ѯ�������ݴ�ӡ����
				System.out.print("name = " + rs.getString("name") + " ");// ������һ
				System.out.println("productNo = " + rs.getString("product_no"));// �����Զ�
			}
			rs.close();
			conn.close();// �������ݿ������
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡConnection�Ĺ��߷���
	 * @return
	 */
	public static Connection getConnection(){
		// ����SQLite��JDBC
		try {
			Class.forName("org.sqlite.JDBC");
			// ����һ�����ݿ���zieckey.db�����ӣ���������ھ��ڵ�ǰĿ¼�´���֮
			Connection conn = DriverManager.getConnection("jdbc:sqlite:psims.db");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
