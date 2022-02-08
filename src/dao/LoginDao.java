package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 密码相关数据库操作类
 * @author xujinnan
 *
 */
public class LoginDao {

	/**
	 * 读取密码
	 * @return
	 */
	public static String getPwd(){
		Connection conn = DataBaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from password");
			ResultSet rs = pstmt.executeQuery();
			String pwd = null;
			while(rs.next()){
				pwd = rs.getString("pwd");
			}
			return pwd;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 更新密码
	 */
	public static void updatePwd(String newPwd){
		Connection conn = DataBaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("update password set pwd=?");
			pstmt.setString(1, newPwd);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
