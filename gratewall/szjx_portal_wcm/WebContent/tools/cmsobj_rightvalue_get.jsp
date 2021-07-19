<%
/** Title:			template_parser.jsp
 *  Description:
 *		WCM5.2 模板语法检测
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *		2004.04.30	caohui
 *			导航树性能的优化（右键菜单速度）的同步修改
 *  Parameters:
 *		see template_parser.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="java.util.StringTokenizer" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.infra.util.DebugTimer" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%!
	private int[] stringToIntArray(String _str){
		if(_str == null) return new int[0];
		StringTokenizer st = new StringTokenizer(_str, ",");
		int nCount = st.countTokens();
		int ar[] = new int[nCount];
		int i = 0;
		while(st.hasMoreTokens()){
			ar[i] = Integer.parseInt(st.nextToken());
			i++;
		}
		return ar;
	}
%>
<%
	DebugTimer timer = new DebugTimer();
	timer.start();

	int nObjType = currRequestHelper.getInt("ObjType", 0);
	int nObjId = currRequestHelper.getInt("ObjId", 0);
	String sRightIndexes = currRequestHelper.getString("RightIndexes");

	CMSObj currObj = (CMSObj)BaseObj.findById(nObjType, nObjId);
	if(currObj == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到Type为["+nObjType+"] ID为["+nObjId+"]的对象！");
	}

	String sRightValue = "";
	if(loginUser.equals(currObj.getCrUser()) || loginUser.isAdministrator()){
		//如果是CrUser或者如果是管理员，则64位权限全部置为1
		sRightValue = "1";
	} else {		
		sRightValue = AuthServer.getLogicalRightValue(currObj, loginUser, stringToIntArray(sRightIndexes));
		//RightValue rightValue =  getRightValueByMember(currObj, loginUser);
		//System.out.println("rightValue;"+rightValue.getValue());
		//sRightValue = rightValue.toString();
	}
	
	
	timer.stop();
	
	out.clear();%><%=sRightValue%>