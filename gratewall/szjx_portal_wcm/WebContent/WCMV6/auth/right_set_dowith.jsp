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
<%@ page import="com.trs.cms.auth.domain.NewRightSet" %>
<%@ page import="com.trs.cms.auth.persistent.Right" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="com.trs.infra.util.CMyString" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	boolean isCurrLoginUserUpdated = false;
	//初始化(获取数据)
	String sImpartModes = currRequestHelper.getString("ImpartModes");
	int[] pImpartModes = null;
	if(!CMyString.isEmpty(sImpartModes)){
		pImpartModes = CMyString.splitToInt(sImpartModes, ",");
	}
	int nImpartMode = currRequestHelper.getInt("ImpartMode", 0);
	int nResetChildrenRights = currRequestHelper.getInt("ResetChildrenRights", 0);
	int nObjType = currRequestHelper.getInt("ObjType", 0);
	int nObjId = currRequestHelper.getInt("ObjId", 0);
	if(nObjId <= 0)
		throw new WCMException("新建模式不允许查看/设置权限！Type:["+nObjType+"] Id:["+nObjId+"]");

	Right srcRight = Right.createNewInstance();
	List arRightList = null;
	try{
		arRightList = WCMObjHelper.toWCMObjList(currRequestHelper.getString("RightsXML"), srcRight, loginUser); 	

		if(nObjType != 1){
			IRightHost rightHost = RightHostFactory.makeRightHostFrom(nObjType, nObjId);
			if(rightHost == null)
				return;
			//权限校验
			try{
				rightHost.validateCanDoSetRight(loginUser);
			}catch(Exception e){
				throw e;
			}
			RightValue rightValueBefore = new RightValue();
			rightValueBefore.load(loginUser, nObjType, nObjId);
			
			/*
			RightSet rightSet = new RightSet();
			rightSet.setRights(loginUser, rightHost, arRightList, nResetChildrenRights>0);
			*/
			NewRightSet rightSet = new NewRightSet(loginUser, rightHost, arRightList, pImpartModes);
			rightSet.save();
			
			RightValue rightValueAfter = new RightValue();
			rightValueAfter.load(loginUser, nObjType, nObjId);
			if(rightValueBefore.getValue() != rightValueAfter.getValue()){
				isCurrLoginUserUpdated = true;
			}
		}else{
			//权限校验
			if(!loginUser.isAdministrator())
				throw new WCMException("当前用户["+loginUser.getName()+"]没有系统属性权限进行设置！");
			AuthServer.setSystemMgrRights(nObjId,arRightList);
		}
	}finally{
		if(arRightList != null){
			for(int i = 0, len = arRightList.size(); i < len; i++){
				CMSObj object = (CMSObj)arRightList.get(i);
				if(object.isLocked()){
					object.unlock();
				}
			}
		}
	}
//7.结束
	out.clear();
%>
<%=isCurrLoginUserUpdated%>