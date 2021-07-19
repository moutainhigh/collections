<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.domain.DocumentMgr"%>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@include file="../include/public_server.jsp"%>

<%
	String fieldVal = currRequestHelper.getString("FILEDVAL");
	String channelId = currRequestHelper.getString("CHANNELID");
	String documentId = currRequestHelper.getString("DOCUMENTID");
	
	if(CMyString.isEmpty(channelId) || channelId == "0"){
		out.println("获取当前栏目失败！");
		return;
	}
	if(CMyString.isEmpty(fieldVal)){
		return;
	}
	Channel currChannel = Channel.findById(Integer.valueOf(channelId));	
	if(currChannel==null){
		out.println("根据栏目ID获取栏目失败！");
		return;
	}
	//当前栏目下的所有文档（包括废稿箱）
	Documents channeldocs = new DocumentMgr().getAllDocuments(currChannel,new WCMFilter());
	if(channeldocs==null || channeldocs.size()==0){
		return;
	}
	for(int i=0;i<channeldocs.size();i++){
		Document tempDoc = (Document)channeldocs.getAt(i);
		String DOCID =  tempDoc.getPropertyAsString("DOCID");
		String DOCTITLE =  tempDoc.getPropertyAsString("DOCTITLE");
		if(documentId.equals(DOCID)){
			continue;
		}
		//获取扩展字段的值
		String extendFiledVal = tempDoc.getPropertyAsString("PUBLISHFILENAME");
		if(!CMyString.isEmpty(extendFiledVal)){
			if(fieldVal.equalsIgnoreCase(extendFiledVal)){
				out.println("该栏目已存在此URL名称,文档["+DOCTITLE+"(DOCID="+DOCID+")]");
				return;
			}
		}
	}
%>