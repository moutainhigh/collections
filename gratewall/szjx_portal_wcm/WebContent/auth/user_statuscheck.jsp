<%
/** Title:			user_statuscheck.jsp
 *  Description:
 *		WCM5.2 检验用户的状态
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			niuzhao
 *  Created:		2005-11-15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see user_statuscheck.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%!
private boolean equals(int _nId, String[] _arrIds) {
	if(_arrIds == null || _arrIds.length == 0) {
		return false;
	}
	int i = 0;
	for( ; i<_arrIds.length; ) {
		if(String.valueOf(_nId).equals(_arrIds[i])) {
			break;
		}
		i++;
	}
	return (i < _arrIds.length);
}
%>
<%
//4.初始化（获取数据）
	String strUserIds = CMyString.showNull(currRequestHelper.getString("UserIds"));
	Users currUsers = null;
	currUsers = Users.findByIds(loginUser, strUserIds);
	if(currUsers == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取要户集合失败！");
	}

	String strStatuses = CMyString.showNull(currRequestHelper.getString("AllowStatuses"));
	String[] arrStatuses = strStatuses.split(",");

	boolean isAllowDeleted = currRequestHelper.getBoolean("AllowDeleted", false);
//5.权限校验

//6.业务代码
	String strResult = "";
	User currUser = null;
	for(int i=0; i<currUsers.size(); i++) {
		currUser = (User)currUsers.getAt(i);
		if(currUser == null) continue;
		if(!isAllowDeleted && currUser.isDeleted()) {
			if(!"".equals(strResult)) {
				strResult += ",";
			}
			strResult += currUser.getName() + "[已删除] ";
		}
		if(!equals(currUser.getStatus(), arrStatuses)) {
			if(!"".equals(strResult)) {
				strResult += ",";
			}
			strResult += currUser.getName() + "[" + currUser.getStatusString() + "] ";
		}
	}
//7.结束
	out.clear();
	out.print(strResult);
%>