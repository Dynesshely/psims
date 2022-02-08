package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 数据库工具类
 * @author xujinnan
 *
 */
public class DataBaseUtil {

	//测试方法
	public static void main(String[] args) {
		try {
			Connection conn = getConnection();
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from product;");// 查询数据
			while (rs.next()) {// 将查询到的数据打印出来
				System.out.print("name = " + rs.getString("name") + " ");// 列属性一
				System.out.println("productNo = " + rs.getString("product_no"));// 列属性二
			}
			rs.close();
			conn.close();// 结束数据库的连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取Connection的工具方法
	 * @return
	 */
	public static Connection getConnection(){
		// 连接SQLite的JDBC
		try {
			Class.forName("org.sqlite.JDBC");
			// 建立一个数据库名zieckey.db的连接，如果不存在就在当前目录下创建之
			Connection conn = DriverManager.getConnection("jdbc:sqlite:psims.db");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
