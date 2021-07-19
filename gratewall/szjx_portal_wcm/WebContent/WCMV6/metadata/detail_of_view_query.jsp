<%--
	@description:
	这个页面接受参数为：
	objectId	必传，数据记录的ID，也为文档的ID；
	viewId		可选，如果传入了这个参数，则要校验当前用户是否具有管理员的权限；
	channelId	可选，如果传入了这个参数，则要检验当前用户是否在这个栏目上有查看文档的权限；
	viewId和channelId两者必须至少传入其中之一
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.webframework.xmlserver.ConvertException" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="java.util.*" %>

<!------- WCM IMPORTS END ------------>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	out.clear();
%>
<style type="text/css">
	.row{
	}
	.label{
		margin:2px 5px;
		width:120px;
		overflow:visible;
		white-space:nowrap;
		border:1px solid blue;
	}
	.value{
		margin:2px 5px;
		width:120px;
		overflow:visible;
		white-space:nowrap;
		border:1px solid blue;
	}

</style>
<%		
		//1.获取视图数据对象
		String sObjectId = request.getParameter("objectId");
		if(sObjectId == null){
			throw new Exception("没有传入合法的对象的objectId");
		}
		int nObjectId = Integer.parseInt(sObjectId);
		MetaViewData oMetaViewData = MetaViewData.findById(nObjectId);
		if(oMetaViewData == null){
			throw new Exception("指定的视图数据的对象没有找到！[ID=" + nObjectId + "]");
		}

		//校验权限
		String sViewId = request.getParameter("viewId");
		String sChannelId = request.getParameter("channelId");
		if(sViewId != null && Integer.parseInt(sViewId) > 0){
			if (!loginUser.isAdministrator()) {
				throw new Exception("你不是管理员，没有权限查看数据[" + nObjectId + "]");
			}
		}else if(sChannelId != null){
			int nChannelId = Integer.parseInt(sChannelId);
			Channel channel = Channel.findById(nChannelId);
			if (!loginUser.getName().equalsIgnoreCase(oMetaViewData.getCrUserName()) 
					&& !AuthServer.hasRight(loginUser, channel, WCMRightTypes.DOC_BROWSE)) {
				throw new Exception("没有权限在当前栏目[" + channel.getName() + "][ID="
								+ channel.getId() + "]下查看数据！");
			}
		}else{
			throw new Exception("没有传入合法的viewId或channelId");
		}

		//2.获取视图下的字段信息
		List viewFieldList = getMetaViewFields(oMetaViewData);
		for (Iterator iter = viewFieldList.iterator(); iter.hasNext();) {
			MetaViewField viewField = (MetaViewField) iter.next();
			if (viewField == null)
				continue;
			String fieldName = viewField.getName();
			out.println("<div class='row'>");
			out.print("<span class='label'>" + viewField.getAnotherName() + ":</span>");
			out.print("<span class='value'>" + oMetaViewData.getPropertyAsString(fieldName) + "</span>");
			out.println("</div>");
		}	
%>

<%!
	/**
	*得到当前数据的结构，视图字段信息
	*@param		MetaViewDatas	数据集合对象
	*@return	HashMap, {[sFieldName, oMetaViewField],...}
	*/
	private List getMetaViewFields(MetaViewData oMetaViewData)throws Exception{
		MetaView metaView = oMetaViewData.getMetaView();
		return getDataDefCacheMgr().getSortedMetaViewFields(metaView.getId());
	}

	/**
	*得到缓冲对象Manager
	*/
	private IMetaDataDefCacheMgr getDataDefCacheMgr()throws Exception{
		return (IMetaDataDefCacheMgr) DreamFactory.createObjectById("IMetaDataDefCacheMgr");
	}
%>
