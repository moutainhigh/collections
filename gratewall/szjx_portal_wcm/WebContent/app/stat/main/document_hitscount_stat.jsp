<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.stat.StatAnalysisTool"%>
<%@ page import="com.trs.components.stat.HitsCountHelper"%>
<%@ page import="com.trs.components.stat.HitsCountConstant"%>
<%@ page import="com.trs.components.stat.IStatResult"%>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../include/static_common.jsp"%>
<%
	try{
%>
<%	
	//1 构造sql
	// 根据文档的点击时间段来统计的sql
	String[] pDocHitsTimeStatSQL = new String[]{
		"Select sum(HITSCOUNT) TOTALCOUNT,DOCID from XWCMDOCUMENTHITSCOUNT "
					// 统计指定点击时间段的数据
					+ " Where HITSTIME>=${STARTTIME}"
					+ " And HITSTIME<=${ENDTIME}" 
					+ " Group by DOCID"
		};

	//2 构造本月的时间段
	String[] sHitsTimeRands = HitsCountHelper.makeQueryHitsTimeRands(HitsCountConstant.TYPE_1MONTH);
	
	//3 获取本月的点击量情况
	StatAnalysisTool tool = new StatAnalysisTool(pDocHitsTimeStatSQL);
    IStatResult result = tool.stat(sHitsTimeRands[0], sHitsTimeRands[1]);
	 
	int nNum = 0;
	List arDocIds = result.sort(true);
	if(arDocIds != null && (arDocIds.size() > 0)){
		nNum= arDocIds.size();
	}
	//7.结束
	out.clear();
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="document_hitscount_stat.title"> 文档点击量 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <script language="javascript" src="../../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/easyversion/extrender.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/easyversion/elementmore.js" type="text/javascript"></script>
  <script language="javascript" src="../js/common.js" type="text/javascript"></script>
  <script language="javascript" src="document_hitscount_stat.js" type="text/javascript"></script>
  <link href="document_hitscount_stat.css" rel="stylesheet" type="text/css" />
 </head>

 <body>
	<div class="data" id="leftdata">
		<table cellspacing="0" cellpadding="0">
				<tbody>
<%		//只取TOP 15篇文档
		for (int i = 0; i < 15; i++) {
			if(arDocIds.size() < i + 1) 
				break;
				
			int nDocId = Integer.parseInt((String) arDocIds.get(i));
			Document doc = Document.findById(nDocId);
			if(doc == null)
				continue;
			String sDocTitle = CMyString.transDisplay(doc.getTitle());
			String sCrUserName = "";
			User crUser = doc.getCrUser();
			if(crUser != null){
				sCrUserName = crUser.getName();
			}
			int nCount = result.getResult(1, String.valueOf(nDocId));
			Channel oChannel = doc.getChannel();
			String sChannelName = CMyString.transDisplay(oChannel.getName());
			ChnlDoc oChnlDoc = ChnlDoc.findByDocument(doc);
%>
				<tr docId="<%=nDocId%>" channelId="<%=oChannel.getId()%>" ChnlDocId="<%=oChnlDoc.getId()%>">
					<td class="num_list" style="background:url('images/n<%=i+1%>.gif') no-repeat 10px 7px"></td><td class="doctitle"><a href="#"><%=sDocTitle%></a></td><td class="count"><%=nCount%></td><td class="username"><%=sCrUserName%></td>
				</tr>
<%
 }	
%>
				</tbody>
			</table>
		</div>
 </body>
</html>
 <%
  }catch(Exception ex){
		ex.printStackTrace();
  }	  
 %>