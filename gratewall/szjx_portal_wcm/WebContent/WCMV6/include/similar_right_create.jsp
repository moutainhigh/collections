<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="error.jsp"%>
<%@ page import="com.trs.cms.auth.persistent.Rights" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.config.OperationConfig" %>
<%@ page import="com.trs.cms.auth.config.OperationRelatedConfig" %>
<%@ page import="com.trs.cms.auth.config.RightConfigServer" %>

<%
	out.clear();
	out.println(getRightString());
%>

<%!
	private String getRightString(){
        int[][] pResult = RightConfigServer.getInstance().getSimilarIndexs();
        if(pResult == null || pResult.length == 0) return null;
		StringBuffer sb = new StringBuffer();
		sb.append("[");
        for(int i = 0; i < pResult.length; i++){
			sb.append("[");
			for(int j = 0; j < pResult[i].length; j++){
				sb.append(pResult[i][j]).append(",");
			}
			if(pResult[i].length > 0){
				sb.setLength(sb.length()-1);
			}
			sb.append("],\n");
		}
		if(pResult.length > 0){
			sb.setLength(sb.length()-2);//,]
		}
		sb.append("]");
		return sb.toString();
	}
%>