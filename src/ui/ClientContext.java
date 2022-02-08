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
	 * �رմ���
	 * @param frame
	 */
	public void exit(JFrame frame) {
		int val = JOptionPane.showConfirmDialog(frame, "ȷ���ر�?");
		if (val == JOptionPane.YES_OPTION) {
			frame.setVisible(false);
			System.exit(0);
		}
	}
	
	/**
	 * ��¼
	 */
	public void login() {
		try {
			String pwd = loginFrame.getUserPwd();
			String realPwd = LoginDao.getPwd();
			if(realPwd.equals(pwd.trim())){
				// ��¼�ɹ�
				mainFrame.init();
				mainFrame.setVisible(true);
				this.loginFrame.setVisible(false);
				//��ѯ��棬���ж��Ƿ���Ҫ����������� 
				if(storageService.checkStorage()){
					storageAlarmFrame.setVisible(true);
				}
			}else{
				JOptionPane.showMessageDialog(loginFrame, "�û������������", "��¼ʧ��", JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(loginFrame, e.getMessage());
		}
	}
	
	/**
	 * ��ʾ�޸����봰��
	 */
	public void showModifyPwdFrame(boolean show){
		modifyPwdFrame.setVisible(show);
	}
	
	/**
	 * �޸�����
	 */
	public void updatePwd(){
		String pwd = new String(modifyPwdFrame.getOrgPwd().getPassword());
		String realPwd = LoginDao.getPwd();
		if(pwd.equals(realPwd)){
			String np = new String(modifyPwdFrame.getPwdField().getPassword());
			String rp = new String(modifyPwdFrame.getRepeatPwd().getPassword());
			if(np.equals(rp)){
				LoginDao.updatePwd(np);
				JOptionPane.showMessageDialog(modifyPwdFrame, "��������ɹ����´ε�¼��ʹ�������롣", "�޸�����", JOptionPane.INFORMATION_MESSAGE);
				showModifyPwdFrame(false);
			}else{
				JOptionPane.showMessageDialog(modifyPwdFrame, "������������벻һ�£�", "�޸�����", JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(modifyPwdFrame, "ԭ�����������", "�޸�����", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * ��ʾ�����ؽ�������
	 */
	public void showOrHidePurchaseFrame(boolean showOrHide){
		purchaseFrame.setVisible(showOrHide);
		
	}
	
	/**
	 * ��ʾ���۴���
	 */
	public void showSellFrame(){
		amount=0;
//		sellFrame.setVisible(true);
		cartFrame.refreshTableData(sellProducts);
		cartFrame.setVisible(true);
	}
	
	/**
	 * ��ʾ����������Ʒ��ⴰ��
	 */
	public void showOrHideNewProductFrame(boolean showOrHide){
		if(showOrHide){
			//��ձ���Ϣ
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
	 * ����Ʒ���
	 */
	public void saveNewProduct(){
		Product p = new Product();
		//������Ʒ��ⴰ�ڵĸ������л������Ʒ�ĸ������ԣ�����һ��Product����
		p.setName(newProductFrame.getProductNameText().getText());
		p.setAlarmStorage(Integer.valueOf(newProductFrame.getAlarmStorageText().getText()));
		p.setCatogery(newProductFrame.getCatCom().getSelectedIndex()+1);
		p.setPrice(Double.parseDouble(newProductFrame.getPriceText().getText()));
		p.setProductNo(newProductFrame.getProductNoText().getText());
		p.setPurPrice(Double.parseDouble(newProductFrame.getPurchasePriceText().getText()));
		p.setStorage(Integer.parseInt(newProductFrame.getStorageText().getText()));
		p.setStockDate(new Date());
		productDao.saveProduct(p);//���µ���Ʒ��Ϣ�������ݿ�
		newProductFrame.setVisible(false);//��������Ʒ������
	}
	
	/**
	 * ��ʾ/���ؽ�����¼����
	 * @param showOrHide
	 */
	public void showOrHideStockHistory(boolean showOrHide){
		if(showOrHide){
			stockHistoryFrame.refreshTableData();
		}
		stockHistoryFrame.setVisible(showOrHide);
	}
	
	/**
	 * ��ʾ�����ؿ�洰��
	 * @param showOrHide
	 */
	public void showOrHideStorageFrame(boolean showOrHide){
		if(showOrHide){
			storageFrame.refreshTableData(null);
		}
		storageFrame.setVisible(showOrHide);
	}
	
	/**
	 * ������鿴���
	 */
	public void filterCategory(){
		int cat = storageFrame.getCatCombox().getSelectedIndex()+1;
		storageFrame.filterCategory(cat);
	}
	
	/**
	 * ���������
	 * @param order asc����desc����
	 */
	public void orderStorageData(String order){
		storageFrame.refreshTableData(order);
	}
	
	/**
	 * ��ʾ/�����޸Ĳ�Ʒ����
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
	 * ������Ʒ��Ϣ
	 */
	public void updateProduct(){
		Product p = currProduct;
		//�Ӹ�����Ʒ��Ϣ���ڵĸ������л������Ʒ�ĸ�������
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
	 * ɾ����Ʒ
	 * @param pid
	 */
	public void deleteProduct(int pid){
		productDao.deleteProduct(pid);
		storageFrame.refreshTableData(null);
	}
	
	/**
	 * ��Ʒ���༰��Ʒ���Ƽ����¼�
	 */
	public void correlationCombox(){
		purchaseFrame.getPurNumbers().setEditable(true);//ÿ���л��������������������ı���
		purchaseFrame.getOkBtn().setEnabled(true);
		int cat = purchaseFrame.getCatCombox().getSelectedIndex()+1;
		ComboBoxModel aModel = new DefaultComboBoxModel(purchaseFrame.getOption(cat));
		purchaseFrame.getProductCombox().setModel(aModel);
		if(purchaseFrame.getProductCombox().getValue() == -1){
			purchaseFrame.getPurNumbers().setEditable(false);//����÷���������Ʒ�������������������
			purchaseFrame.getOkBtn().setEnabled(false);//ȷ����ť�û�
		}else{
			currProduct = productDao.findProduct(purchaseFrame.getProductCombox().getValue());
		}
	}
	
	/**
	 * ����������Ʒ��������Ӧ�¼����趨��ǰѡ������Ʒ
	 */
	public void setCurrSelectedProduct(){
		currProduct = productDao.findProduct(purchaseFrame.getProductCombox().getValue());
	}
	
	/**
	 * ������ť��Ӧ�¼�
	 * �����������ı�������ݻ�ȡ�����������ݿ�
	 */
	public void purchase(){
		String txt = purchaseFrame.getPurNumbers().getText();
		if(txt == null || txt.trim().length() == 0){
			JOptionPane.showMessageDialog(purchaseFrame, "��������Ʒ������","��Ʒ���", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Integer num = Integer.valueOf(txt);
		productDao.updateStorage(currProduct.getProductId(), num);
		purchaseFrame.getPurNumbers().setText("");
		StockHistory sh = new StockHistory();
		sh.setProductId(currProduct.getProductId());
		sh.setQuantity(num);
		stockHistoryDao.saveStockHistory(sh);
		JOptionPane.showMessageDialog(purchaseFrame, "���ɹ���","��Ʒ���", JOptionPane.INFORMATION_MESSAGE);
		currProduct = null;
	}
	
	/**
	 * ��Ʒ���ۣ�С��
	 */
	public void sell(){
		String qtyTxt = sellFrame.getProductQty().getText();
		if(qtyTxt == null || qtyTxt.trim().length() == 0){
			JOptionPane.showMessageDialog(sellFrame, "��������Ʒ������","��Ʒ���", JOptionPane.INFORMATION_MESSAGE);
			return ;
		}
		int quantity = Integer.parseInt(qtyTxt);
		Product p = productDao.findProductByNo(sellFrame.getProductNumField().getText());
		if(p != null){
			//����ǰ����Ѳ���
			if(p.getStorage() < quantity){
				JOptionPane.showMessageDialog(sellFrame, p.getName()+"��治�㣡", "��澯��", JOptionPane.WARNING_MESSAGE);
			}else{
				double amt = p.getPrice() * quantity;//�����ܼ�
				p.setStorage(p.getStorage()-quantity);//���¿��
				productDao.updateProduct(p);
				SellHistory sh = new SellHistory();
				sh.setProductId(p.getProductId());
				sh.setQuantity(quantity);
				shDao.saveSellHistory(sh);//�������ۼ�¼
				//���۳ɹ���ʾ
				JOptionPane.showMessageDialog(sellFrame, p.getName()+"\n"+"������"+quantity+"    С�ƣ�"+amt,"��Ʒ����", JOptionPane.INFORMATION_MESSAGE);
				amount += amt;
				sellFrame.getProductNumField().setText("");//������۴��ڱ�
				sellFrame.getProductQty().setText("");
				//���۳ɹ��󣬼�ʱ����棬��������ֵ����������
				if(storageService.checkProductStorage(p.getProductId())){
					JOptionPane.showMessageDialog(sellFrame, p.getName()+"���ͣ��뾡�첹���棡", "��澯��", JOptionPane.WARNING_MESSAGE);
				}
			}
		}else{
			JOptionPane.showMessageDialog(sellFrame, "���޴���Ʒ�����������ţ�", "�������", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * ��ӵ����ﳵ
	 */
	public void addCart(){
		String qtyTxt = cartFrame.getProductQty().getText();
		if(qtyTxt == null || qtyTxt.trim().length() == 0){
			JOptionPane.showMessageDialog(cartFrame, "��������Ʒ������","��ӹ��ﳵ", JOptionPane.WARNING_MESSAGE);
			return ;
		}
		if(!isNumeric(qtyTxt)){
			JOptionPane.showMessageDialog(cartFrame, "�������������Ʒ������","��ӹ��ﳵ", JOptionPane.WARNING_MESSAGE);
			return ;
		}
		int quantity = Integer.parseInt(qtyTxt);
		Product p = productDao.findProductByNo(cartFrame.getProductNumField().getText());
		if(p != null){
			//����ǰ����Ѳ���
			if(p.getStorage() < quantity){
				JOptionPane.showMessageDialog(cartFrame, p.getName()+"��治�㣡", "��澯��", JOptionPane.WARNING_MESSAGE);
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
			JOptionPane.showMessageDialog(cartFrame, "���޴���Ʒ�����������ţ�", "�������", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * ��Ʒ���ۣ�����
	 */
	public void settle(){
		amount = 0;
		for(Cart c:sellProducts){
			amount += c.getAmt();
			Product p = c.getProduct();
			p.setStorage(p.getStorage()-c.getQuantity());//���¿��
			productDao.updateProduct(p);
			SellHistory sh = new SellHistory();
			sh.setProductId(p.getProductId());
			sh.setQuantity(c.getQuantity());
			shDao.saveSellHistory(sh);//�������ۼ�¼
			//���۳ɹ��󣬼�ʱ����棬��������ֵ����������
			if(storageService.checkProductStorage(p.getProductId())){
				JOptionPane.showMessageDialog(cartFrame, p.getName()+"���ͣ��뾡�첹���棡", "��澯��", JOptionPane.WARNING_MESSAGE);
			}
		}
		sellProducts.clear();
		JOptionPane.showMessageDialog(cartFrame, "��Ʒ�ܶ"+amount, "��Ʒ����", JOptionPane.INFORMATION_MESSAGE);
		cartFrame.setVisible(false);
	}
	
	/**
	 * ˢ�����ۼ�¼�����ʾ���ۼ�¼����
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
