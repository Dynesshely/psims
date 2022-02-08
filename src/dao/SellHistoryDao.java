package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import entity.SellHistory;

/**
 * 销售历史记录数据库操作类
 * @author xujinnan
 *
 */
public class SellHistoryDao {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	ProductDao pdao = new ProductDao();
	
	/**
	 * 查找所有销售记录
	 * @return
	 */
	public Vector<SellHistory> findAllHistory(){
		Vector<SellHistory> ret = new Vector<SellHistory>();
		
		Connection conn = DataBaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from sell_history order by sell_date desc");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				SellHistory sh = new SellHistory();
				sh.setShId(rs.getInt("sh_id"));
				sh.setProductId(rs.getInt("product_id"));
				sh.setProduct(pdao.findProduct(sh.getProductId()));
				sh.setSellDate(sdf.parse(rs.getString("sell_date")));
				sh.setQuantity(rs.getInt("quantity"));
				ret.add(sh);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	/**
	 * 插入销售记录
	 * @param sh
	 */
	public void saveSellHistory(SellHistory sh){
		Connection conn = DataBaseUtil.getConnection();
		String sql = "insert into sell_history(product_id, sell_date ,quantity) values(?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sh.getProductId());
			pstmt.setString(2, sdf.format(new Date()));
			pstmt.setInt(3, sh.getQuantity());
			pstmt.executeUpdate();
//			System.out.println(sql);
		} catch (Exception e) {
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
