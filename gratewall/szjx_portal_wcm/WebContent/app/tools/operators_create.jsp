<%@ page contentType="text/plain;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.Iterator,java.util.Comparator,java.util.Collections" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.individuation.*" %>
<%@ page import="com.trs.wcm.config.PageOperators" %>
<%@ page import="com.trs.wcm.config.PageOperator" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.common.conjection.service.IComponentEntryConfigService"%>
<%@ page import="com.trs.components.common.conjection.ComponentEntryConfigConstants"%>
<%@ page import="com.trs.components.common.conjection.persistent.EntryConfig"%>
<%
String oprType = request.getParameter("oprType");
String where = "";
if(oprType != null){
	if(oprType.indexOf(",")!=-1){
		oprType = "\'" + oprType.replaceAll(",", "\',\'") + "\'";
		where = "oprType in(" + oprType + ")";
	}
	else{
		where = "oprType=?"	;
	}
}
WCMFilter filter = new WCMFilter("", where, "OprType, OprOrder");
if(oprType != null){//得到某一特定类型的操作命令
	if(oprType.indexOf(",")==-1){
		filter.addSearchValues(oprType);
	}
}
String sCurrOprType = null;
int nCurrOprTypeCount = 0;
out.clear();
PageOperators operators = PageOperators.openWCMObjs(null, filter);
for (int i = 0, nSize = operators.size(); i < nSize; i++) {
	PageOperator operator = (PageOperator) operators.getAt(i);
	if (operator == null)
		continue;
%>
	reg({
		key : '<%=operator.getOprKey().toLowerCase()%>',
		type : '<%=operator.getType()%>',
		desc : '<%=operator.getName()%>',
		title : '<%=operator.getDesc()%>',
		rightIndex : <%=operator.getRightIndex()%>,
		order : <%=i+1%>,
		fn : pageObjMgr['<%=operator.getOprKey().toLowerCase()%>']
	});<%
}
%>