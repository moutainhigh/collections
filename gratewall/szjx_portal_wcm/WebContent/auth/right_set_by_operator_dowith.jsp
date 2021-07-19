<%
/** Title:			right_set_by_operator.jsp
 *  Description:
 *		WCM5.2 设置权限页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wenyh
 *  Created:		2006-04-30 13:34
 *  Vesion:			1.0
 *  Last EditTime:	2006-04-30 / 2006-04-30
 *	Update Logs:
 *		
 *
 *  Parameters:
 *		see right_set_by_operator.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Rights" %>
<%@ page import="com.trs.cms.auth.persistent.Right" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>


<%
	int nOperId = currRequestHelper.getInt("OperId",0);
	int nOperType = currRequestHelper.getInt("OperType",0);
	String rightsXml = currRequestHelper.getString("RightsXML");

	Right srcRight = new Right();
	List rightsList = WCMObjHelper.toWCMObjList(rightsXml,srcRight,loginUser);

	//clear
	Right right = null;
	StringBuffer sbRightIds = new StringBuffer(128);	
	for(int i=0; i<rightsList.size();i++){
		right = (Right)rightsList.get(i);
		if(right == null) continue;
		//nRightId = right.getId();
		right.setOperatorId(nOperId);
		right.setOperatorType(nOperType);
		right.save(loginUser);
		sbRightIds.append(',').append(right.getId());
	}

	StringBuffer sbWhere = new StringBuffer(64);
	sbWhere.append("OprId=").append(nOperId);
	sbWhere.append(" AND ");
	sbWhere.append("OprType=").append(nOperType);

	if(sbRightIds.length() > 1){
		sbWhere.append(" AND RightId NOT IN(").append(sbRightIds.substring(1)).append(")");
	}

	Rights rights = new Rights(loginUser);
	WCMFilter filter = new WCMFilter("",sbWhere.toString(),"");
	rights.open(filter);
	rights.removeAll(true);
%>
<html>
<body>
<script>
window.close();
</script>
</body>
</html>