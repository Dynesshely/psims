package ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import service.StorageAlarmService;
import dao.LoginDao;
import dao.ProductDao;
import dao.SellHistoryDao;
import dao.StockHistoryDao;
import entity.Cart;
import entity.Product;
import entity.SellHistory;
import entity.StockHistory;

public class ClientContext {
	private MainFrame mainFrame;
	private LoginFrame loginFrame;
	private ModifyPwdFrame modifyPwdFrame;
	private PurchaseFrame purchaseFrame;
	private SellFrame sellFrame;
	private NewProductFrame newProductFrame;
	private StorageFrame storageFrame;
	private ModifyProductFrame modifyProductFrame;
	private StockHistoryFrame stockHistoryFrame;
	private StorageAlarmFrame storageAlarmFrame;
	private SellHistoryFrame sellHistoryFrame;
	private CartFrame cartFrame;
	private ProductDao productDao = new ProductDao();
	private SellHistoryDao shDao = new SellHistoryDao();
	private StockHistoryDao stockHistoryDao = new StockHistoryDao();
	private StorageAlarmService storageService = new StorageAlarmService();
	private Product currProduct;
	private List<Cart> sellProducts = new ArrayList<Cart>();
	private double amount = 0;
	
	/**
	 * 关闭窗口
	 * @param frame
	 */
	public void exit(JFrame frame) {
		int val = JOptionPane.showConfirmDialog(frame, "确定关闭?");
		if (val == JOptionPane.YES_OPTION) {
			frame.setVisible(false);
			System.exit(0);
		}
	}
	
	/**
	 * 登录
	 */
	public void login() {
		try {
			String pwd = loginFrame.getUserPwd();
			String realPwd = LoginDao.getPwd();
			if(realPwd.equals(pwd.trim())){
				// 登录成功
				mainFrame.init();
				mainFrame.setVisible(true);
				this.loginFrame.setVisible(false);
				//查询库存，是判断是否需要弹出库存提醒 
				if(storageService.checkStorage()){
					storageAlarmFrame.setVisible(true);
				}
			}else{
				JOptionPane.showMessageDialog(loginFrame, "用户名或密码错误", "登录失败", JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(loginFrame, e.getMessage());
		}
	}
	
	/**
	 * 显示修改密码窗口
	 */
	public void showModifyPwdFrame(boolean show){
		modifyPwdFrame.setVisible(show);
	}
	
	/**
	 * 修改密码
	 */
	public void updatePwd(){
		String pwd = new String(modifyPwdFrame.getOrgPwd().getPassword());
		String realPwd = LoginDao.getPwd();
		if(pwd.equals(realPwd)){
			String np = new String(modifyPwdFrame.getPwdField().getPassword());
			String rp = new String(modifyPwdFrame.getRepeatPwd().getPassword());
			if(np.equals(rp)){
				LoginDao.updatePwd(np);
				JOptionPane.showMessageDialog(modifyPwdFrame, "更新密码成功！下次登录请使用新密码。", "修改密码", JOptionPane.INFORMATION_MESSAGE);
				showModifyPwdFrame(false);
			}else{
				JOptionPane.showMessageDialog(modifyPwdFrame, "两次输入的密码不一致！", "修改密码", JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(modifyPwdFrame, "原密码输入错误！", "修改密码", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * 显示或隐藏进货窗口
	 */
	public void showOrHidePurchaseFrame(boolean showOrHide){
		purchaseFrame.setVisible(showOrHide);
		
	}
	
	/**
	 * 显示销售窗口
	 */
	public void showSellFrame(){
		amount=0;
//		sellFrame.setVisible(true);
		cartFrame.refreshTableData(sellProducts);
		cartFrame.setVisible(true);
	}
	
	/**
	 * 显示或隐藏新商品入库窗口
	 */
	public void showOrHideNewProductFrame(boolean showOrHide){
		if(showOrHide){
			//清空表单信息
			newProductFrame.getCatCom().setSelectedIndex(0);
			newProductFrame.getAlarmStorageText().setText("");
			newProductFrame.getProductNameText().setText("");
			newProductFrame.getProductNoText().setText("");
			newProductFrame.getStorageText().setText("");
			newProductFrame.getPurchasePriceText().setText("");
			newProductFrame.getPriceText().setText("");
		}
		newProductFrame.setVisible(showOrHide);
	}
	
	/**
	 * 新商品入库
	 */
	public void saveNewProduct(){
		Product p = new Product();
		//从新商品入库窗口的各个表单中获得新商品的各个属性，创建一个Product对象
		p.setName(newProductFrame.getProductNameText().getText());
		p.setAlarmStorage(Integer.valueOf(newProductFrame.getAlarmStorageText().getText()));
		p.setCatogery(newProductFrame.getCatCom().getSelectedIndex()+1);
		p.setPrice(Double.parseDouble(newProductFrame.getPriceText().getText()));
		p.setProductNo(newProductFrame.getProductNoText().getText());
		p.setPurPrice(Double.parseDouble(newProductFrame.getPurchasePriceText().getText()));
		p.setStorage(Integer.parseInt(newProductFrame.getStorageText().getText()));
		p.setStockDate(new Date());
		productDao.saveProduct(p);//将新的商品信息存入数据库
		newProductFrame.setVisible(false);//隐藏新商品表单窗口
	}
	
	/**
	 * 显示/隐藏进货记录窗口
	 * @param showOrHide
	 */
	public void showOrHideStockHistory(boolean showOrHide){
		if(showOrHide){
			stockHistoryFrame.refreshTableData();
		}
		stockHistoryFrame.setVisible(showOrHide);
	}
	
	/**
	 * 显示或隐藏库存窗口
	 * @param showOrHide
	 */
	public void showOrHideStorageFrame(boolean showOrHide){
		if(showOrHide){
			storageFrame.refreshTableData(null);
		}
		storageFrame.setVisible(showOrHide);
	}
	
	/**
	 * 按分类查看库存
	 */
	public void filterCategory(){
		int cat = storageFrame.getCatCombox().getSelectedIndex()+1;
		storageFrame.filterCategory(cat);
	}
	
	/**
	 * 按库存排序
	 * @param order asc升序，desc降序
	 */
	public void orderStorageData(String order){
		storageFrame.refreshTableData(order);
	}
	
	/**
	 * 显示/隐藏修改产品窗口
	 * @param showOrHide
	 */
	public void showOrHideModifyProductFrame(boolean showOrHide){
		if(showOrHide){
			modifyProductFrame.getCatCom().setSelectedIndex(0);
			modifyProductFrame.getAlarmStorageText().setText(currProduct.getAlarmStorage()+"");
			modifyProductFrame.getProductNameText().setText(currProduct.getName());
			modifyProductFrame.getProductNoText().setText(currProduct.getProductNo());
			modifyProductFrame.getStorageText().setText(currProduct.getStorage()+"");
			modifyProductFrame.getPurchasePriceText().setText(currProduct.getPurPrice()+"");
			modifyProductFrame.getPriceText().setText(currProduct.getPrice()+"");
		}
		modifyProductFrame.setVisible(showOrHide);
	}
	
	/**
	 * 更新商品信息
	 */
	public void updateProduct(){
		Product p = currProduct;
		//从更新商品信息窗口的各个表单中获得新商品的各个属性
		p.setName(modifyProductFrame.getProductNameText().getText());
		p.setAlarmStorage(Integer.valueOf(modifyProductFrame.getAlarmStorageText().getText()));
		p.setCatogery(modifyProductFrame.getCatCom().getSelectedIndex()+1);
		p.setPrice(Double.parseDouble(modifyProductFrame.getPriceText().getText()));
		p.setProductNo(modifyProductFrame.getProductNoText().getText());
		p.setPurPrice(Double.parseDouble(modifyProductFrame.getPurchasePriceText().getText()));
		p.setStorage(Integer.parseInt(modifyProductFrame.getStorageText().getText()));
		productDao.updateProduct(p);
		storageFrame.refreshTableData(null);
		modifyProductFrame.setVisible(false);
		currProduct = null;
	}
	
	/**
	 * 删除商品
	 * @param pid
	 */
	public void deleteProduct(int pid){
		productDao.deleteProduct(pid);
		storageFrame.refreshTableData(null);
	}
	
	/**
	 * 商品种类及商品名称级联事件
	 */
	public void correlationCombox(){
		purchaseFrame.getPurNumbers().setEditable(true);//每次切换种类下拉框，重置数量文本框
		purchaseFrame.getOkBtn().setEnabled(true);
		int cat = purchaseFrame.getCatCombox().getSelectedIndex()+1;
		ComboBoxModel aModel = new DefaultComboBoxModel(purchaseFrame.getOption(cat));
		purchaseFrame.getProductCombox().setModel(aModel);
		if(purchaseFrame.getProductCombox().getValue() == -1){
			purchaseFrame.getPurNumbers().setEditable(false);//如果该分类下无商品，不允许输入进货数量
			purchaseFrame.getOkBtn().setEnabled(false);//确定按钮置灰
		}else{
			currProduct = productDao.findProduct(purchaseFrame.getProductCombox().getValue());
		}
	}
	
	/**
	 * 进货窗口商品下拉框响应事件，设定当前选定的商品
	 */
	public void setCurrSelectedProduct(){
		currProduct = productDao.findProduct(purchaseFrame.getProductCombox().getValue());
	}
	
	/**
	 * 进货按钮响应事件
	 * 将进货数量文本框的数据获取，并更新数据库
	 */
	public void purchase(){
		String txt = purchaseFrame.getPurNumbers().getText();
		if(txt == null || txt.trim().length() == 0){
			JOptionPane.showMessageDialog(purchaseFrame, "请输入商品数量！","商品入库", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Integer num = Integer.valueOf(txt);
		productDao.updateStorage(currProduct.getProductId(), num);
		purchaseFrame.getPurNumbers().setText("");
		StockHistory sh = new StockHistory();
		sh.setProductId(currProduct.getProductId());
		sh.setQuantity(num);
		stockHistoryDao.saveStockHistory(sh);
		JOptionPane.showMessageDialog(purchaseFrame, "入库成功！","商品入库", JOptionPane.INFORMATION_MESSAGE);
		currProduct = null;
	}
	
	/**
	 * 商品销售：小计
	 */
	public void sell(){
		String qtyTxt = sellFrame.getProductQty().getText();
		if(qtyTxt == null || qtyTxt.trim().length() == 0){
			JOptionPane.showMessageDialog(sellFrame, "请输入商品数量！","商品入库", JOptionPane.INFORMATION_MESSAGE);
			return ;
		}
		int quantity = Integer.parseInt(qtyTxt);
		Product p = productDao.findProductByNo(sellFrame.getProductNumField().getText());
		if(p != null){
			//若当前库存已不足
			if(p.getStorage() < quantity){
				JOptionPane.showMessageDialog(sellFrame, p.getName()+"库存不足！", "库存警告", JOptionPane.WARNING_MESSAGE);
			}else{
				double amt = p.getPrice() * quantity;//计算总价
				p.setStorage(p.getStorage()-quantity);//更新库存
				productDao.updateProduct(p);
				SellHistory sh = new SellHistory();
				sh.setProductId(p.getProductId());
				sh.setQuantity(quantity);
				shDao.saveSellHistory(sh);//插入销售记录
				//销售成功提示
				JOptionPane.showMessageDialog(sellFrame, p.getName()+"\n"+"数量："+quantity+"    小计："+amt,"商品销售", JOptionPane.INFORMATION_MESSAGE);
				amount += amt;
				sellFrame.getProductNumField().setText("");//清除销售窗口表单
				sellFrame.getProductQty().setText("");
				//销售成功后，及时检查库存，若低于阈值，给出警告
				if(storageService.checkProductStorage(p.getProductId())){
					JOptionPane.showMessageDialog(sellFrame, p.getName()+"库存低，请尽快补充库存！", "库存警告", JOptionPane.WARNING_MESSAGE);
				}
			}
		}else{
			JOptionPane.showMessageDialog(sellFrame, "查无此商品，请检查输入编号！", "输入错误", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * 添加到购物车
	 */
	public void addCart(){
		String qtyTxt = cartFrame.getProductQty().getText();
		if(qtyTxt == null || qtyTxt.trim().length() == 0){
			JOptionPane.showMessageDialog(cartFrame, "请输入商品数量！","添加购物车", JOptionPane.WARNING_MESSAGE);
			return ;
		}
		if(!isNumeric(qtyTxt)){
			JOptionPane.showMessageDialog(cartFrame, "请输入正解的商品数量！","添加购物车", JOptionPane.WARNING_MESSAGE);
			return ;
		}
		int quantity = Integer.parseInt(qtyTxt);
		Product p = productDao.findProductByNo(cartFrame.getProductNumField().getText());
		if(p != null){
			//若当前库存已不足
			if(p.getStorage() < quantity){
				JOptionPane.showMessageDialog(cartFrame, p.getName()+"库存不足！", "库存警告", JOptionPane.WARNING_MESSAGE);
			}else{
				if(sellProducts.size() > 0){
					boolean exist = false;
					for(Cart c:sellProducts){
						if(c.getProduct().getProductId() == p.getProductId()){
							c.setQuantity(c.getQuantity() + quantity);
							c.setAmt(c.getAmt() + p.getPrice() * quantity);
							exist = true;
							break;
						}
					}
					if(!exist){
						Cart c = new Cart();
						c.setProduct(p);
						c.setQuantity(quantity);
						c.setAmt(p.getPrice() * quantity);
						sellProducts.add(c);
					}
				}else{
					Cart c = new Cart();
					c.setProduct(p);
					c.setQuantity(quantity);
					c.setAmt(p.getPrice() * quantity);
					sellProducts.add(c);
				}
				cartFrame.refreshTableData(sellProducts);
				cartFrame.getProductNumField().setText("");
				cartFrame.getProductQty().setText("");
			}
		}else{
			JOptionPane.showMessageDialog(cartFrame, "查无此商品，请检查输入编号！", "输入错误", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * 商品销售：结账
	 */
	public void settle(){
		amount = 0;
		for(Cart c:sellProducts){
			amount += c.getAmt();
			Product p = c.getProduct();
			p.setStorage(p.getStorage()-c.getQuantity());//更新库存
			productDao.updateProduct(p);
			SellHistory sh = new SellHistory();
			sh.setProductId(p.getProductId());
			sh.setQuantity(c.getQuantity());
			shDao.saveSellHistory(sh);//插入销售记录
			//销售成功后，及时检查库存，若低于阈值，给出警告
			if(storageService.checkProductStorage(p.getProductId())){
				JOptionPane.showMessageDialog(cartFrame, p.getName()+"库存低，请尽快补充库存！", "库存警告", JOptionPane.WARNING_MESSAGE);
			}
		}
		sellProducts.clear();
		JOptionPane.showMessageDialog(cartFrame, "商品总额："+amount, "商品销售", JOptionPane.INFORMATION_MESSAGE);
		cartFrame.setVisible(false);
	}
	
	/**
	 * 刷新销售记录表格，显示销售记录窗口
	 */
	public void showSellHistoryFrame(){
		sellHistoryFrame.refreshTableData();
		sellHistoryFrame.setVisible(true);
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public LoginFrame getLoginFrame() {
		return loginFrame;
	}

	public void setLoginFrame(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}

	public PurchaseFrame getPurchaseFrame() {
		return purchaseFrame;
	}

	public void setPurchaseFrame(PurchaseFrame purchaseFrame) {
		this.purchaseFrame = purchaseFrame;
	}

	public SellFrame getSellFrame() {
		return sellFrame;
	}

	public void setSellFrame(SellFrame sellFrame) {
		this.sellFrame = sellFrame;
	}

	public NewProductFrame getNewProductFrame() {
		return newProductFrame;
	}

	public void setNewProductFrame(NewProductFrame newProductFrame) {
		this.newProductFrame = newProductFrame;
	}

	public StorageFrame getStorageFrame() {
		return storageFrame;
	}

	public void setStorageFrame(StorageFrame storageFrame) {
		this.storageFrame = storageFrame;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public Product getCurrProduct() {
		return currProduct;
	}

	public void setCurrProduct(Product currProduct) {
		this.currProduct = currProduct;
	}

	public ModifyProductFrame getModifyProductFrame() {
		return modifyProductFrame;
	}

	public void setModifyProductFrame(ModifyProductFrame modifyProductFrame) {
		this.modifyProductFrame = modifyProductFrame;
	}

	public StockHistoryFrame getStockHistoryFrame() {
		return stockHistoryFrame;
	}

	public void setStockHistoryFrame(StockHistoryFrame stockHistoryFrame) {
		this.stockHistoryFrame = stockHistoryFrame;
	}

	public StorageAlarmFrame getStorageAlarmFrame() {
		return storageAlarmFrame;
	}

	public void setStorageAlarmFrame(StorageAlarmFrame storageAlarmFrame) {
		this.storageAlarmFrame = storageAlarmFrame;
	}

	public SellHistoryFrame getSellHistoryFrame() {
		return sellHistoryFrame;
	}

	public void setSellHistoryFrame(SellHistoryFrame sellHistoryFrame) {
		this.sellHistoryFrame = sellHistoryFrame;
	}

	public ModifyPwdFrame getModifyPwdFrame() {
		return modifyPwdFrame;
	}

	public void setModifyPwdFrame(ModifyPwdFrame modifyPwdFrame) {
		this.modifyPwdFrame = modifyPwdFrame;
	}

	public CartFrame getCartFrame() {
		return cartFrame;
	}

	public void setCartFrame(CartFrame cartFrame) {
		this.cartFrame = cartFrame;
	}
	
	public List<Cart> getSellProducts() {
		return sellProducts;
	}

	public void setSellProducts(List<Cart> sellProducts) {
		this.sellProducts = sellProducts;
	}

	private boolean isNumeric(String str){
		for (int i = str.length();--i>=0;){   
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}
}
