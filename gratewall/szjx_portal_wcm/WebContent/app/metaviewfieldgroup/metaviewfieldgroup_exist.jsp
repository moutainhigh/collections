<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.metadata.definition.MetaViewFieldGroups" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFieldGroup" %>
<%@ page import="com.trs.infra.util.CMyString" %>


<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	int nCurrGroupId = currRequestHelper.getInt("MetaViewFieldGroupId",0);
	int nMetaViewId = currRequestHelper.getInt("MetaViewId",0);
	String sFieldGroupName = currRequestHelper.getString("GroupName");
	//参数的校验
	if(CMyString.isEmpty(sFieldGroupName) || nMetaViewId <= 0){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,LocaleServer.getString("metaviewfieldgroup_exist.jsp.label.error_param", "无效的参数！"));
	}
	out.clear();
	MetaViewFieldGroups currMetaViewFieldGroups = MetaViewFieldGroup.findByName(nMetaViewId, sFieldGroupName);
	if(currMetaViewFieldGroups.size() > 0){
		MetaViewFieldGroup fieldGroup = (MetaViewFieldGroup)currMetaViewFieldGroups.getAt(0);
		if(nCurrGroupId == 0) {
			out.print(fieldGroup != null);
		}
		else {
			out.print(fieldGroup != null && fieldGroup.getId() != nCurrGroupId);
		}
	}else{
		out.print(false);
	}
%>