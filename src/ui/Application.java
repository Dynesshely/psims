package ui;

/**
 * ���������
 * @author xujinnan
 *
 */
public class Application {
	public static void main(String[] args) {
		/*
		 * ʵ��������Frame��������ClientContext
		 * ������IOC˼�룬�����������й����У�ֻ��һ��������ʵ����ÿ��FrameҲֻ��һ��ʵ��
		 * �����ﴴ������ʵ�������������໥ע��
		 * ��������ͬ��Frame�Ϳ������õ��������еķ�����������Ҳ���Բ������е�Frame�����еĿؼ�
		 * �����ǵ�����������ɶ������
		 */
		ClientContext clientContext = new ClientContext();
		MainFrame mainFrame = new MainFrame();
		LoginFrame loginFrame = new LoginFrame();
		PurchaseFrame purchaseFrame = new PurchaseFrame();
		SellFrame sellFrame = new SellFrame();
		StorageFrame storageFrame = new StorageFrame();
		NewProductFrame newProductFrame = new NewProductFrame();
		StockHistoryFrame stockHistoryFrame = new StockHistoryFrame();
		ModifyProductFrame modifyProductFrame = new ModifyProductFrame();
		StorageAlarmFrame storageAlarmFrame = new StorageAlarmFrame();
		SellHistoryFrame sellHistoryFrame = new SellHistoryFrame();
		ModifyPwdFrame modifyPwdFrame = new ModifyPwdFrame();
		CartFrame cartFrame = new CartFrame();
		
		/*
		 * �����е�Frameʵ��ע�뵽ClientContext��
		 */
		clientContext.setMainFrame(mainFrame);
		clientContext.setLoginFrame(loginFrame);
		clientContext.setPurchaseFrame(purchaseFrame);
		clientContext.setSellFrame(sellFrame);
		clientContext.setStorageFrame(storageFrame);
		clientContext.setNewProductFrame(newProductFrame);
		clientContext.setModifyProductFrame(modifyProductFrame);
		clientContext.setStockHistoryFrame(stockHistoryFrame);
		clientContext.setStorageAlarmFrame(storageAlarmFrame);
		clientContext.setSellHistoryFrame(sellHistoryFrame);
		clientContext.setModifyPwdFrame(modifyPwdFrame);
		clientContext.setCartFrame(cartFrame);
		
		/*
		 * ��ClientContextʵ��ע�����Frameʵ����
		 */
		mainFrame.setClientContext(clientContext);
		loginFrame.setClientContext(clientContext);
		purchaseFrame.setClientContext(clientContext);
		sellFrame.setClientContext(clientContext);
		newProductFrame.setClientContext(clientContext);
		storageFrame.setClientContext(clientContext);
		modifyProductFrame.setClientContext(clientContext);
		stockHistoryFrame.setClientContext(clientContext);
		storageAlarmFrame.setClientContext(clientContext);
		sellHistoryFrame.setClientContext(clientContext);
		modifyPwdFrame.setClientContext(clientContext);
		cartFrame.setClientContext(clientContext);
		
		//��ʾ��¼���ڣ���¼���ɹ���Ĳ������ɿ���������
		loginFrame.setVisible(true);
	}
}
