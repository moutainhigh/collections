<%
/** Title:			first_access_init.jsp
 *  Description:
 *		WCM 提示页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WYW
 *  Created:		2010-1-6 16:57
 *  Vesion:			1.0
 *
 */ 
%>

<%
	boolean bAccess = "true".equalsIgnoreCase(ConfigServer
			.getServer().getSysConfigValue("hasAccess", "false"));
	if(!bAccess){
		ConfigServer.getServer().updateConfigValue("hasAccess", "true");
	}
	out.clear();
%>