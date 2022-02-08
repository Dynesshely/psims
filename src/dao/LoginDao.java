package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ����������ݿ������
 * @author xujinnan
 *
 */
public class LoginDao {

	/**
	 * ��ȡ����
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
	 * ��������
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
