<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ include file="../../../../include/public_processor.jsp"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%
	
	//by CC 20120423 获取所有的引用栏目集合
	processor.reset();
	int docId = processor.getParam("ObjectId", 0);
	processor.setAppendParameters(new String[]{
		"ObjectId", String.valueOf(docId)
	});
	Channels quoteChannels = (Channels) processor.excute("document", "getQuotedChannels");
	out.clear();
%>
{
	QUOTECHNLS : {
		QUOTECHNL : [
<%
	Channel chnl = null;
	ChnlDoc oChnlDoc = null;
	for(int index = 0, length = quoteChannels.size(); index < length; index++){
		try{
			chnl = (Channel) quoteChannels.getAt(index);
			if(chnl == null) continue;
			oChnlDoc = ChnlDoc.findByDocAndChnl(docId,chnl.getId());
%>
			{
				CHNL : {
					ID : <%=chnl.getId()%>,
					CHNLNAME : '<%=CMyString.filterForJs(CMyString.showNull(chnl.getName()))%>',
					DOCMODAL : '<%=(oChnlDoc.getModal())%>'
				}
			},
<%
		}catch(Throwable tx){
			String sTip = LocaleServer.getString("metaviewdata.label.quoteChnlNotExist","指定的引用栏目不存在");
%>
			{
				CHNL : {
					ID : <%=chnl.getId()%>,
					CHNLNAME : '<%=CMyString.filterForJs(sTip)%>[ID=]<%=chnl.getId()%>',
					DOCMODAL : '<%=(oChnlDoc.getModal()-2)%>'
				}
			},
<%
		}
	}
%>
		]
	}
}