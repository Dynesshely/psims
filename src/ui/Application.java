package ui;

/**
 * 程序主入口
 * @author xujinnan
 *
 */
public class Application {
	public static void main(String[] args) {
		/*
		 * 实例化各个Frame及控制器ClientContext
		 * 这里借鉴IOC思想，整个程序运行过程中，只有一个控制器实例，每个Frame也只有一个实例
		 * 在这里创建所有实例，并将它们相互注入
		 * 这样，不同的Frame就可以引用到控制器中的方法，控制器也可以操作所有的Frame及其中的控件
		 * 由于是单例，不会造成对象混乱
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
		 * 将所有的Frame实例注入到ClientContext中
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
		 * 将ClientContext实例注入各个Frame实例中
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
		
		//显示登录窗口，登录及成功后的操作交由控制器处理
		loginFrame.setVisible(true);
	}
}
