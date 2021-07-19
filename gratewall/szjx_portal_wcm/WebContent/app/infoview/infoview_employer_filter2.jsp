<%
/** Title:			infoview_employer_filter.jsp
 *  Description:
 *		WCM5.2 文档 [ 移动/引用/复制 ] 的页面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wenyh
 *  Created:		2006-06-23 09:24
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *		
 *
 *  Parameters:
 *		see infoview_employer_filter.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="java.util.ArrayList" %>
<%-- ----- WCM IMPORTS END ---------- --%>
<%@include file="./infoview_public_include.jsp"%>
<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>

<%
	out.clear();
	int nFromId = currRequestHelper.getInt("FromChannelId",0);
	String sToIds = currRequestHelper.getString("ToChannelIds");

	if(CMyString.isEmpty(sToIds)){
		//为了取消选择
		out.println(0);
		return;
	}

	// 
	String[] arParts = CMyString.showNull(sToIds).split(",");
	if(arParts != null && arParts.length > 0) {
		String sPart = null;
		ArrayList arToChnlIds = new ArrayList(arParts.length);
		for (int i = 0; i < arParts.length; i++){
			sPart = arParts[i];
			if(CMyString.isEmpty(sPart)) {
				continue;
			}
			//else
			if(!sPart.equalsIgnoreCase(String.valueOf(nFromId))) {
				arToChnlIds.add(sPart);
			}
		}

		sToIds = CMyString.join(arToChnlIds, ",");
	}

	String strInChannelIds = nFromId + "";
	if(!CMyString.isEmpty(sToIds))
		strInChannelIds =  strInChannelIds + "," + sToIds;
	String sqlQuery = "select count(distinct InfoViewId) from WCMInfoViewEmploy where EmployerType=101 and EmployerID in(" + strInChannelIds + ")";
	int nCount = DBManager.getDBManager().sqlExecuteIntQuery(sqlQuery);
	if(nCount==1){
		//此时可能有普通栏目
		boolean bIsRefer = currRequestHelper.getBoolean("IsRefer", false);
		if(!bIsRefer){//不是引用时
			sqlQuery = "select count(InfoViewId) from WCMInfoViewEmploy where EmployerType=101 and EmployerID in(" + strInChannelIds + ")";
			int nCount2 = DBManager.getDBManager().sqlExecuteIntQuery(sqlQuery);
			if(nCount2 != strInChannelIds.split(",").length){
				//说明有普通栏目
				//故意的标识它成为复选
				nCount = 2;
			}
		}else{//需要区分自己是不是普通栏目
			sqlQuery = "select count(InfoViewId) from WCMInfoViewEmploy where EmployerType=101 and EmployerID = " + nFromId;
			int nCount2 = DBManager.getDBManager().sqlExecuteIntQuery(sqlQuery);
			if(nCount2 ==0 ){
				//说明是普通栏目
				//标识为引用到了表单栏目
				nCount = 2;
			}
		}
		if(arParts.length==1 && arParts[0].equalsIgnoreCase(String.valueOf(nFromId))) {
				nCount = 1;
		}
	}
	out.println(nCount);
%>