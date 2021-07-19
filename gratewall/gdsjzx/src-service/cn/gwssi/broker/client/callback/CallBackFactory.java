package cn.gwssi.broker.client.callback;

public class CallBackFactory {
	static CallBack callback=null;
	public static CallBack getServiceInstance(String nameClass){
		try {
			callback = (CallBack) Class.forName(nameClass).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return callback;
	}
}