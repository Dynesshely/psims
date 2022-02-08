package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import entity.StockHistory;

/**
 * ������ʷ��¼���ݿ������
 * @author xujinnan
 *
 */
public class StockHistoryDao {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	ProductDao pdao = new ProductDao();
	
	/**
	 * �������н�����¼
	 * @return
	 */
	public Vector<StockHistory> findAllHistory(){
		Vector<StockHistory> ret = new Vector<StockHistory>();
		
		Connection conn = DataBaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from stock_history order by stock_date desc");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				StockHistory sh = new StockHistory();
				sh.setSid(rs.getInt("sh_id"));
				sh.setProductId(rs.getInt("product_id"));
				sh.setProduct(pdao.findProduct(sh.getProductId()));
				sh.setStockDate(sdf.parse(rs.getString("stock_date")));
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
	 * ���������¼
	 * @param sh
	 */
	public void saveStockHistory(StockHistory sh){
		Connection conn = DataBaseUtil.getConnection();
		String sql = "insert into stock_history(product_id, stock_date ,quantity) values(?,?,?)";
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
