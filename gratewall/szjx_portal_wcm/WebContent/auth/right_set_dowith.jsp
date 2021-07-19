<%
/** Title:			right_set.jsp
 *  Description:
 *		WCM5.2 设置权限页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wsw
 *  Created:		2004-12-29 13:54
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-29 / 2004-12-29	
 *	Update Logs:
 *		CH@2004-12-29 产生
 *
 *  Parameters:
 *		see right_set.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.domain.IRightHost" %>
<%@ page import="com.trs.cms.auth.domain.RightHostFactory" %>
<%@ page import="com.trs.cms.auth.domain.RightSet" %>
<%@ page import="com.trs.cms.auth.persistent.Right" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
//4.初始化(获取数据)
	int nObjType = currRequestHelper.getInt("ObjType", 0);
	int nObjId = currRequestHelper.getInt("ObjId", 0);
	if(nObjId <= 0)
		throw new WCMException("新建模式不允许设置权限！Type:["+nObjType+"] Id:["+nObjId+"]");

	int nResetChildrenRights = currRequestHelper.getInt("ResetChildrenRights", 0);
	

//6.业务代码
		IRightHost rightHost =null;
		Right srcRight = Right.createNewInstance();
		List arRightList = WCMObjHelper.toWCMObjList(currRequestHelper.getString("RightsXML"), srcRight, loginUser); 	
		
		
	if(nObjType != 1){
		rightHost = RightHostFactory.makeRightHostFrom(nObjType, nObjId);
		if(rightHost == null)
			throw new WCMException("没有找到指定的对象！Type:["+nObjType+"] Id:["+nObjId+"]");
		
		RightSet rightSet = new RightSet();
		rightSet.setRights(loginUser, rightHost, arRightList, nResetChildrenRights>0);
		//AuthServer.setRights(rightHost, arRightList);
	}else{
		AuthServer.setSystemMgrRights(nObjId,arRightList);
	}
//7.结束
	out.clear();
%>
<script>
	//CTRSAction_alert("成功设置<%=rightHost%>权限！");
	window.returnValue=true;
	window.close();
</script>