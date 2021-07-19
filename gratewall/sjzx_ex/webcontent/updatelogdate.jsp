<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="com.gwssi.common.database.DBOperation" %>
<%@ page import="com.gwssi.common.database.DBOperationFactory" %>
<%@ page import="java.util.*" %>
<%@ page import="com.gwssi.common.util.StringUtil" %>
<freeze:html>
	<freeze:body>
	<%
		out.print("<h3>��ʼ����..........</h3>");
		out.flush();
		long begin = new Date().getTime();
		DBOperation operation = DBOperationFactory.createTimeOutOperation();
		String sql="select t.sys_svr_log_id,t.execute_start_time,t.execute_end_time from sys_svr_log t WHERE T.EXECUTE_START_TIME NOT LIKE '____-%'";
		List list = operation.select(sql);
		
		if(list != null && list.size() > 0){
			List sqlList = new ArrayList();
			for(int i = 0; i < list.size(); i++){
				Map map = (Map)list.get(i);
				long startTime = Long.parseLong(""+map.get("EXECUTE_START_TIME"));
				long endTime = Long.parseLong(""+map.get("EXECUTE_END_TIME"));
				String id = ""+map.get("SYS_SVR_LOG_ID");
				sql = "update sys_svr_log set execute_start_time='"+StringUtil.formatDateToString(new Date(startTime),"yyyy-MM-dd HH:mm:ss")+"', execute_end_time='"+StringUtil.formatDateToString(new Date(endTime),"yyyy-MM-dd HH:mm:ss")+"' where SYS_SVR_LOG_ID='"+id+"'";
				sqlList.add(sql);
			}
			operation.execute(sqlList,true);
		}
		long cost = new Date().getTime() - begin;
		out.print("���³ɹ��������� <font color='red'>"+list.size()+"</font> ����¼����ʱ <font color='red'>"+(cost/1000)+"</font> �룡");
	%>
	
    
	</freeze:body>
</freeze:html>