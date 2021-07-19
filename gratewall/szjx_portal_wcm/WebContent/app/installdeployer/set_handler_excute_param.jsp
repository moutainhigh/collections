<%--
/*
 *	History			Who			What
 *	2008-01-23		lhm		    安装后部署的消息获取页面
 *
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.deployer.domain.DeployerController"%>
<%@ page import="com.trs.deployer.common.DeployerConstants"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@include file="../include/public_server.jsp"%>

<!------- WCM IMPORTS END ------------>
<%	
	DeployerController controller = DeployerController.newInstance(loginUser);
	Enumeration e = request.getParameterNames();
	while(e.hasMoreElements()){
		String sName = (String)e.nextElement();
		String sValue = request.getParameter(sName);
		controller.setParameter(sName, CMyString.showNull(sValue));
	}
	//controller.setParameter(DeployerConstants.B_WAIT_PAGE_INPUT_PARAMS, "false");
	out.clear();
%>