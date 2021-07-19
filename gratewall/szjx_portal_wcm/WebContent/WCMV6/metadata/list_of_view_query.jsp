<%--
	@description:
	这个页面是
	http://***/wcm/center.do?methodname=jQueryViewDatas&serviceid=wcm6_MetaDataCenter
	的跳转页面，上面链接地址需带上高级检索的复制参数。如：	http://127.0.0.1:7070/wcm/center.do?methodname=jQueryViewDatas&serviceid=wcm6_MetaDataCenter&viewId=0&channelid=2228&ChannelType=0&SiteType=4&PageSize=20&isor=false&_dateStartSuffix_=_Start&_dateEndSuffix_=_End&_isAdvanceSearch_=true&name=&publish=&ContainsChildren=0
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.center.MetaViewDatas" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
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
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>

<!------- WCM IMPORTS END ------------>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
//2. 获取业务数据
	MethodContext oMethodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	MetaViewDatas oMetaViewDatas = (MetaViewDatas)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);

//3. 构造分页参数，这段逻辑应该都可以放到服务器端	TODO
	int nPageSize = -1, nPageIndex = 1;
	if (oMethodContext != null) {
		nPageSize = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_PAGESIZE, 20);
		nPageIndex = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_CURRPAGE, 1);
	}	
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	currPager.setItemCount(oMetaViewDatas.size());

	response.setHeader("Num",String.valueOf(currPager.getItemCount()));
	response.setHeader("PageSize",String.valueOf(currPager.getPageSize()));
	response.setHeader("PageCount",String.valueOf(currPager.getPageCount()));
	response.setHeader("CurrPageIndex",String.valueOf(currPager.getCurrentPageIndex()));
	out.clear();
%>
<style type="text/css">
	.row{
		border:1px solid black;
	}
	.column{
		margin:2px 5px;
		width:120px;
		overflow:visible;
		white-space:nowrap;
		border:1px solid blue;
	}
</style>
<%

//5. 遍历生成表现
	List viewFieldList = getMetaViewFields(oMetaViewDatas);
	printRowOfHead(oMetaViewDatas, viewFieldList, out);
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			MetaViewData oMetaViewData = (MetaViewData)oMetaViewDatas.getAt(i - 1);
			if (oMetaViewData == null){
				continue;
			}
			printRowOfData(oMetaViewData, viewFieldList, out);
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
%>

<%!
	/**
	*得到当前数据的结构，排序之后的视图字段信息
	*@param		MetaViewDatas	数据集合对象
	*@return	List, {oMetaViewField,...}
	*/
	private List getMetaViewFields(MetaViewDatas oMetaViewDatas)throws Exception{
		MetaView metaView = oMetaViewDatas.getMetaView();
		return getDataDefCacheMgr().getSortedMetaViewFields(metaView.getId());
	}

	/**
	*得到缓冲对象Manager
	*/
	private IMetaDataDefCacheMgr getDataDefCacheMgr()throws Exception{
		return (IMetaDataDefCacheMgr) DreamFactory.createObjectById("IMetaDataDefCacheMgr");
	}

	/**
	*输出表头标题信息
	*/
	private void printRowOfHead(MetaViewDatas oMetaViewDatas, List viewFieldList, JspWriter out)throws Exception{
		out.println("<div class='row'>");
		for (Iterator iter = viewFieldList.iterator(); iter.hasNext();) {
			MetaViewField viewField = (MetaViewField) iter.next();
			if (viewField == null || !viewField.isInOutline())
				continue;
			out.print("<span class='column'>" + viewField.getAnotherName() + "</span>");
		}
		out.println("</div>");
	}
	
	/**
	*输出数据行信息
	*/
	private void printRowOfData(MetaViewData oMetaViewData, List viewFieldList, JspWriter out)throws Exception{
		out.println("<div class='row'>");
		for (Iterator iter = viewFieldList.iterator(); iter.hasNext();) {
			MetaViewField viewField = (MetaViewField) iter.next();
			if (viewField == null || !viewField.isInOutline())
				continue;
			String fieldName = viewField.getName();
			out.print("<span class='column'>" + oMetaViewData.getPropertyAsString(fieldName) + "</span>");
		}
		//此行打印出了发布地址信息......
		out.print(getPublishUrl(oMetaViewData));
		out.println("</div>");
	}

	/**
	*得到发布的路径信息
	*/
	private String getPublishUrl(MetaViewData oMetaViewData)throws Exception{
		int nObjType = WCMTypes.OBJ_DOCUMENT;
		int nObjId = oMetaViewData.getId();
		IPublishElement element =  PublishElementFactory.lookupElement(nObjType, nObjId);
		PublishPathCompass compass = new PublishPathCompass();
		return compass.getHttpUrl((IPublishContent) element, 0);
	}
%>
