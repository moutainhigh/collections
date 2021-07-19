

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.metadata.definition.MetaViewFieldGroup" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFieldGroups" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	int nMetaViewId = currRequestHelper.getInt("MetaViewId",0);
	String sWhere = "METAVIEWID = ?";
	WCMFilter filter = new WCMFilter("XWCMMETAVIEWFIELDGROUP", sWhere, "GROUPORDER asc");
	filter.addSearchValues(0, nMetaViewId);
	MetaViewFieldGroups currMetaViewFieldGroups = MetaViewFieldGroups.openWCMObjs(loginUser, filter);
	StringBuffer buffer = new StringBuffer();
	buffer.append("[");
	boolean bFirst = true;
	if(currMetaViewFieldGroups.size() > 0){
		for (int i = 0, nSize = currMetaViewFieldGroups.size(); i < nSize; i++) {
			MetaViewFieldGroup oMetaViewFieldGroup = (MetaViewFieldGroup) currMetaViewFieldGroups.getAt(i);
			if (oMetaViewFieldGroup == null)
				continue;
			if(!bFirst){
				buffer.append(",");
			}
			String sGroupNameDesc = "";
			int nParentId = oMetaViewFieldGroup.getParentId();
			if(nParentId>0){
				MetaViewFieldGroup oParentMetaViewFieldGroup = MetaViewFieldGroup.findById(nParentId);
				if(oParentMetaViewFieldGroup != null)
					sGroupNameDesc+= oParentMetaViewFieldGroup.getGroupName();
			}
			if(CMyString.isEmpty(sGroupNameDesc)){
				sGroupNameDesc = oMetaViewFieldGroup.getGroupName();
			}else{
				sGroupNameDesc +="-" + oMetaViewFieldGroup.getGroupName();
			}
			bFirst = false;
			buffer.append("{");
			buffer.append("GroupId:\"" + oMetaViewFieldGroup.getId() +"\",GroupName:\"" + CMyString.filterForJs(sGroupNameDesc)+"\"");
			buffer.append("}");
		}
	}
	buffer.append("];");
	out.clear();
	out.println(buffer.toString());
%>