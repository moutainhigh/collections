<%@ page buffer="1024kb" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Role"%>
<%@ page import="com.trs.cms.auth.persistent.RightValue"%>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.infra.support.log.LogServer" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	int nOperId = currRequestHelper.getInt("OperId",0);
	int nOperType = currRequestHelper.getInt("OperType",0); 
	String sRightIndex = currRequestHelper.getString("RightIndex");
		
	try{
		//获取当前角色
		Role role = null;
		role = Role.findById(nOperId);
		RightValue rightValue = new RightValue();

		//修改角色的RightValue
		int index = 0;
		if(sRightIndex == null) return;
		String[] sRightIndexs = sRightIndex.split(",");
		if(sRightIndexs.length <= 0) return;
		for(int i=0;i<sRightIndexs.length;i++){
			index = Integer.parseInt(sRightIndexs[i]);
			rightValue = rightValue.setRight(index,true);
		}
		role.setRightValue(rightValue.getValue());

		//保存
		StringBuffer buff = new StringBuffer(128);
		buff.append(LocaleServer.getString("systemright_edit_dowith.jsp.label.rightvalue", "设置角色的权限, RightValue: "));
		buff.append(role.getRightValue()).append(" --> ").append(rightValue.getValue());
	    long lLogId = LogServer.startRecord(loginUser.getName(), "ROLE_EDIT",buff.toString(), role);
		role.save(loginUser);
		LogServer.endRecordSucceedOperation(lLogId);
	}catch(Throwable e){
		e.printStackTrace();
		throw new WCMException(LocaleServer.getString("systemright_edit_dowith.jsp.label.fail2setright","设置权限值失败！"), e);
	}
%>
<script language="javascript">
<!--
	window.top.close();
//-->
</script>