<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.center.MetaViewDatas" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.components.metadata.definition.MetaViewField"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@include file="../include/public_processor.jsp"%>
<%
	//参数的获取
	int nRelatingViewId = processor.getParam("RelatingViewId", 0); 
	int nRelatedViewId = processor.getParam("RelatedViewId", 0); 
	int nRelatedDocId = processor.getParam("RelatedDocId", 0);
	//参数的校验
	if(nRelatedDocId == 0){
		throw new WCMException("必须的参数RelatedDocId没有传入！");
	}
	if(nRelatingViewId == 0){
		throw new WCMException("必须的参数RelatingViewId没有传入！");
	}
	if(nRelatedViewId == 0){
		throw new WCMException("必须的参数RelatedViewId没有传入！");
	}
	//获取数据
	Map oResultMap = (HashMap) processor.excute("wcm61_metaviewdata", "queryRelatingViewDatas");
	//组织数据
	//每条数据需要的参数：docid,doctitle.
	//另外需要获取存储相关文档id的字段信息fieldid，fieldname
	MetaViewDatas datas = (MetaViewDatas)oResultMap.get("MetaViewDatas");
	MetaViewField relationField = (MetaViewField)oResultMap.get("RelationField");
	StringBuffer strBuffer = new StringBuffer();
	strBuffer.append("{RELATIONS:{RELATION:[");
	for(int i=0;i<datas.size();i++){
		MetaViewData data = (MetaViewData)datas.getAt(i);
		int nDocId = data.getDocumentId();
		int nChannelId = data.getChannelId();
		String sTitle = Document.findById(nDocId).getTitle();
		strBuffer.append("{RELDOC : {ID:");
		strBuffer.append(nDocId + ",");
		strBuffer.append("CHANNELID:" + nChannelId + ",");
		strBuffer.append("TITLE:'" + CMyString.filterForJs(sTitle) + "'");
		strBuffer.append("}}");
		if(i < (datas.size() - 1)){
			strBuffer.append(",");
		}
	}
	strBuffer.append("]");
	strBuffer.append(",RELATIONFIELD:'" + CMyString.filterForJs(relationField.getName()) + "'");
	strBuffer.append("}}");
	//输出结果信息
	out.clear();out.print(strBuffer.toString());%>