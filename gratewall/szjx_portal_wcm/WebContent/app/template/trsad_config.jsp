<%
/** Title:			trsad_config.jsp
 *  Author:			gronlet
 *  Created:		2007-09-13 11:38 AM
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		
 *
 */
%>

<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.conjection.service.IComponentEntryConfigService"%>
<%@ page import="com.trs.components.common.conjection.ComponentEntryConfigConstants"%>
<%@ page import="com.trs.components.common.conjection.persistent.EntryConfig"%>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	//ge gfc add @ 2007-03-15 选件入口的配置
	IComponentEntryConfigService configSrv = (IComponentEntryConfigService) DreamFactory
			.createObjectById("IComponentEntryConfigService");
	EntryConfig currConfig = configSrv.getTypedEntryConfig(ComponentEntryConfigConstants.TYPE_ADVERTISEMENT);
	out.clear();
%>
		var trsad_config = {}; 
		trsad_config.enable 	= false;
<%
		if(currConfig !=null && currConfig.isEnable()){
%>
		trsad_config.root_path 	= '<%=currConfig.getLinkPath()%>';
		trsad_config.enable 	= true;
<%
	}
%>