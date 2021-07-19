package cn.gwssi.common.log;

public class LogOperationFactory {
	static LogOperation logOperation=null;
	public static LogOperation getLogOperationInstance(String nameClass){
		try {
			logOperation = (LogOperation) Class.forName(nameClass).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return logOperation;
	}
}