package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import dao.DataBaseUtil;
import dao.ProductDao;
import entity.Product;

public class StorageAlarmService {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 检查是否有商品的库存低于警告值
	 * @return
	 */
	public boolean checkStorage(){
		Connection conn = DataBaseUtil.getConnection();
		String sql = "select count(*) from product p where p.storage<p.alarm_storage";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			boolean ret = false;
			while(rs.next()){
				int count = rs.getInt(1);
				if(count > 0){
					ret = true;
				}
			}
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 查找所有缺货商品
	 * @return
	 */
	public Vector<Product> findAlarmProduct(){
		String sql = "select * from product p0 where p0.product_id in(select product_id from product p where p.storage<p.alarm_storage)";
		Vector<Product> ret = new Vector<Product>();
		Connection conn = DataBaseUtil.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Product p = new Product();
				p.setCatogery(rs.getInt("catogery"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getDouble("price"));
				p.setProductId(rs.getInt("product_id"));
				p.setProductNo(rs.getString("product_no"));
				p.setPurPrice(rs.getDouble("pur_price"));
				p.setStockDate(sdf.parse(rs.getString("stock_date")));
				p.setStorage(rs.getInt("storage"));
				p.setAlarmStorage(rs.getInt("alarm_storage"));
				ret.add(p);
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
	 * 销售商品后，及时检测其库存是否已低于阈值，若是则给出提醒 
	 * @param pid
	 * @return
	 */
	public boolean checkProductStorage(int pid){
		ProductDao dao = new ProductDao();
		Product p = dao.findProduct(pid);
		if(p.getStorage() <= p.getAlarmStorage()){
			return true;
		}else{
			return false;
		}
	}
}
