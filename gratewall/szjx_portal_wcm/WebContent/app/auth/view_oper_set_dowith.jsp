<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/errorforAuth.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	//初始化(获取数据)
	int nObjId = currRequestHelper.getInt("ObjId", 0);
	int nObjType = currRequestHelper.getInt("ObjType", 0);
	if(nObjId <= 0){
		throw new WCMException(CMyString.format(LocaleServer.getString("view_oper_set_dowith.auth.label.noviewperssion", "新建模式不允许查看/设置可访问可操作成员!Type:[{0}] Id:[{1}]"), new int[]{nObjType, nObjId}));
	}
	//保存
	JSPRequestProcessor processor = new JSPRequestProcessor(request,response);
	String sServiceId="wcm61_objectmember",sMethodName="saveMembersOfObject";
	processor.excute(sServiceId,sMethodName);

	//结束
	out.clear();
%>