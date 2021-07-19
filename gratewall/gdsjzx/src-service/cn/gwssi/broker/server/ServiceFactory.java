package cn.gwssi.broker.server;

public class ServiceFactory {
	static ServiceProvider serviceProvider=null;
	public static ServiceProvider getServiceInstance(String nameClass){
		try {
			serviceProvider = (ServiceProvider) Class.forName(nameClass).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return serviceProvider;
	}
}
