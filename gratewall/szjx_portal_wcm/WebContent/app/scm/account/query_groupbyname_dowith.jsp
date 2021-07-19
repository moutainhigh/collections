<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>

<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.infra.util.CMyException"%>
<%@ page import="com.trs.scm.persistent.SCMGroup"%>
<%@ page import="com.trs.scm.persistent.SCMGroups"%>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ include file="../../include/public_server.jsp"%>

<%	
	// 获取要查询的分组名称
	String sGroupName = currRequestHelper.getString("GroupName");
	String sTableName = SCMGroup.DB_TABLE_NAME;
	
	// 从数据库中查询分组集合
	WCMFilter oFilter = new WCMFilter(sTableName, "GROUPNAME = ?", "");
	oFilter.addSearchValues(sGroupName);
	SCMGroups oGroups = null;

	try{
		oGroups = SCMGroups.openWCMObjs(loginUser, oFilter);
	}catch(Exception e){
		
		out.println(2);
		return;
	}
	 
	if (oGroups != null && oGroups.size() > 0) {
		out.print(1);
		return;
	}
	out.print(0);
%>


