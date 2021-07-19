package cn.gwssi.mian;



import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Layout;
import org.apache.log4j.Priority;
import org.apache.log4j.jdbc.JDBCAppender;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

import cn.gwssi.mian.model.SmExceptionLogBO;
import cn.gwssi.mian.model.SmInterfaceLogBO;
import cn.gwssi.mian.model.SmOperationLogBO;

/**logger适配器
 * cn.gwssi.mian
 * JDBCExtApender.java
 * 上午10:56:13
 * @author wuminghua
 */
public class JDBCExtApender extends JDBCAppender {
		
		
	@Override
	public void append(LoggingEvent event) {
		super.append(event);
	}

	@Override
	public void close() {
		super.close();
	}

	//关闭连接
	@Override
	protected void closeConnection(Connection con) {
		try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            errorHandler.error("Error closing connection", e,
                    ErrorCode.GENERIC_FAILURE);
        }

	}

	@Override
	protected void execute(String sql) throws SQLException {
		
		if (sql==null||"".equals(sql)) return ;
		Connection conn=getConnection();
		Statement stmt= conn.createStatement();
		try {
			stmt.execute(sql);
		} catch (Exception e) {
			//写文件//打开连接
			
			System.out.println(e);
			
		}finally{
			ConnectManager.closeConn(conn);
		}
		
//		super.execute(sql);
	}

	@Override
	public void finalize() {
		super.finalize();
	}

	@Override
	public void flushBuffer() {
		super.flushBuffer();
	}

	@Override
	public int getBufferSize() {
		return super.getBufferSize();
	}

	@Override
	protected Connection getConnection() throws SQLException {
		return ConnectManager.getConn();
	}
	@Override
	protected String getLogStatement(LoggingEvent event) {
		Object obj=event.getMessage();
		
		Calendar calendat = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String sql="";
		//操作日志
		if(obj instanceof SmOperationLogBO){
			SmOperationLogBO bo=(SmOperationLogBO) obj;
			sql="insert into SM_OPERATION_LOG(PK_OPERATION_LOG,CREATER_ID,CLIENT_TYPE,CLIENT_IP,OPERATION_TIME,SYSTEM_CODE,FUNCTION_CODE,OPERATION_TYPE,OPERATION_STATE) values("
					+ "'"+bo.getPkOperationLog()+"',"
					+ "'"+bo.getCreaterId()+"',"
					+ "'"+bo.getClientType()+"',"
					+ "'"+bo.getClientIp()+"',"
					+ "TO_TIMESTAMP('"+sdf.format(bo.getOperationTime().getTime())+"','YYYY-MM-DD HH24:MI:SS'),"
					+ "'"+bo.getSystemCode()+"',"
					+ "'"+bo.getFunctionCode()+"',"
					+ "'"+bo.getOperationType()+"',"
					+ "'"+bo.getOperationState()+"'"
					+ ")";
			
			
		}
		
		//接口日志
		if(obj instanceof SmInterfaceLogBO){
			SmInterfaceLogBO bo=(SmInterfaceLogBO) obj;
			
			sql="insert into SM_INTERFACE_LOG(PK_INTERFACE_LOG,CREATER_ID,CLIENT_TYPE,CLIENT_IP,OPERATION_TIME,SYSTEM_CODE,FUNCTION_CODE,OPERATION_TYPE,OPERATION_STATE,EXCEPTION_CODE,EXCEPTION_DESC) values("
					+ "'"+bo.getPkInterfaceLog()+"',"
					+ "'"+bo.getCreaterId()+"',"
					+ "'"+bo.getClientType()+"',"
					+ "'"+bo.getClientIp()+"',"
					+ "TO_TIMESTAMP('"+sdf.format(bo.getOperationTime().getTime())+"','YYYY-MM-DD HH24:MI:SS'),"
					+ "'"+bo.getSystemCode()+"',"
					+ "'"+bo.getFunctionCode()+"',"
					+ "'"+bo.getOperationType()+"',"
					+ "'"+bo.getOperationState()+"',"
					+ "'"+bo.getExceptionCode()+"',"
					+ "'"+bo.getExceptionDesc()+"'"
					+ ")";
			
		}
		
		//异常日志
		if(obj instanceof SmExceptionLogBO){
			SmExceptionLogBO bo=(SmExceptionLogBO) obj;
			
			sql="insert into SM_EXCEPTION_LOG(PK_EXCEPTION_LOG,CREATER_ID,CLIENT_TYPE,CLIENT_IP,OPERATION_TIME,SYSTEM_CODE,FUNCTION_CODE,OPERATION_TYPE,OPERATION_STATE,EXCEPTION_CODE,EXCEPTION_DESC) values("
					+ "'"+bo.getPkExceptionLog()+"',"
					+ "'"+bo.getCreaterId()+"',"
					+ "'"+bo.getClientType()+"',"
					+ "'"+bo.getClientIp()+"',"
					+ "TO_TIMESTAMP('"+sdf.format(bo.getOperationTime().getTime())+"','YYYY-MM-DD HH24:MI:SS'),"
					+ "'"+bo.getSystemCode()+"',"
					+ "'"+bo.getFunctionCode()+"',"
					+ "'"+bo.getOperationType()+"',"
					+ "'"+bo.getOperationState()+"',"
					+ "'"+bo.getExceptionCode()+"',"
					+ "'"+bo.getExceptionDesc()+"'"
					+ ")";
		}
		
		
		return sql;
//		return super.getLogStatement(event);
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getSql() {
		return super.getSql();
	}

	@Override
	public String getURL() {
		return super.getURL();
	}

	@Override
	public String getUser() {
		return super.getUser();
	}

	@Override
	public boolean requiresLayout() {
		return super.requiresLayout();
	}

	@Override
	public void setBufferSize(int newBufferSize) {
		super.setBufferSize(newBufferSize);
	}

	@Override
	public void setDriver(String driverClass) {
		super.setDriver(driverClass);
	}

	@Override
	public void setPassword(String password) {
		super.setPassword(password);
	}

	@Override
	public void setSql(String s) {
		super.setSql(s);
	}

	@Override
	public void setURL(String url) {
		super.setURL(url);
	}

	@Override
	public void setUser(String user) {
		super.setUser(user);
	}

	@Override
	public void activateOptions() {
		super.activateOptions();
	}

	@Override
	public void addFilter(Filter newFilter) {
		super.addFilter(newFilter);
	}

	@Override
	public void clearFilters() {
		super.clearFilters();
	}

	@Override
	public synchronized void doAppend(LoggingEvent event) {
		super.doAppend(event);
	}

	@Override
	public ErrorHandler getErrorHandler() {
		return super.getErrorHandler();
	}

	@Override
	public Filter getFilter() {
		return super.getFilter();
	}

	@Override
	public Layout getLayout() {
		return super.getLayout();
	}

	@Override
	public Priority getThreshold() {
		return super.getThreshold();
	}

	@Override
	public boolean isAsSevereAsThreshold(Priority priority) {
		return super.isAsSevereAsThreshold(priority);
	}

	@Override
	public synchronized void setErrorHandler(ErrorHandler eh) {
		super.setErrorHandler(eh);
	}

	@Override
	public void setLayout(Layout layout) {
		super.setLayout(layout);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
	}

	@Override
	public void setThreshold(Priority threshold) {
		super.setThreshold(threshold);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	

}
