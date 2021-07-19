<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.ViewDocuments" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.List" %>
<%@include file="../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%
	//增量式编程，写完业务逻辑后，要对代码进行整理和重构

	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	//获取时间
	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];

	//获取栏目id
	int nSiteId = currRequestHelper.getInt("siteIds",0);
	WebSite currSite = WebSite.findById(nSiteId);
	if(currSite==null)throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("doc_statofsite_list.id.zero","没有找到栏目对象[Id={0}]"),new int[]{nSiteId}));
	String sSiteName = currSite.getName();
	int nType = currRequestHelper.getInt("statType",0);
	int nCase = currRequestHelper.getInt("case",0);
	//获取其他检索条件，例如：状态（1）、使用情况（2），内容类型（3）
	String sSearchItem = currRequestHelper.getString("SelectItem");
	String sSearchValue = currRequestHelper.getString("SearchValue");
	String[] params = {};
	if(!CMyString.isEmpty(sSearchValue)){
		String[] searchParams = {sSearchItem,sSearchValue};
		params = searchParams;
		processor.setAppendParameters(params);
	}
	
	String[] sParm = getParamsByType(nType,nCase,sStartTime,sEndTime);
	String []aAppendParam = {"IsInStat","true"};
	String[] aTemp = combineArray(sParm, params);
	processor.setAppendParameters(combineArray(aTemp, aAppendParam));

	//获取文档集合
	ViewDocuments oViewDocuments = (ViewDocuments) processor.excute("wcm61_viewdocument", "query");

	//构造分页参数
	int nPageSize = currRequestHelper.getInt("PageSize",10);
	int nCurrPage = currRequestHelper.getInt("CurrPage",1);
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nCurrPage);
	int nNum = oViewDocuments.size();
	currPager.setItemCount(nNum);

	// 设置分页信息到 header
	response.setHeader("CurrPage",String.valueOf(nCurrPage));
	response.setHeader("PageSize",String.valueOf(nPageSize));
	response.setHeader("Num",String.valueOf(nNum));
	String sReturnUrl = currRequestHelper.getString("ReturnUrl");
%>
<% out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="doc_statofsite_list.title"> 站点发稿量列表 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <script language="javascript" src="../../../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/extrender.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/elementmore.js" type="text/javascript"></script>
  <script src="../../../js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../js/easyversion/calendar_lang/$locale$.js"></script>
  <script language="javascript" src="../../../js/easyversion/calendar3.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/common.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/search_bar/search_bar.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/tab/tab.js" type="text/javascript"></script>
<!--引入分页JS-->
  <script language="javascript" src="../../js/page_nav/page.js" type="text/javascript"></script>
  <link href="../../js/page_nav/page.css" rel="stylesheet" type="text/css" />
  <link href="../../js/tab/tab.css" rel="stylesheet" type="text/css" />
  <link href="../../js/search_bar/search_bar.css" rel="stylesheet" type="text/css" />
  <link href="../../css/calendar.css" rel="stylesheet" type="text/css" />
  <link href="../table-list.css" rel="stylesheet" type="text/css" />
  <style type="text/css">
	.data table .table_title{
		height:23px;
		color:#033494;
		background-color:#ced7e0;
	}
	.data .doctitle{
		padding-left:5px;
		text-align:left;
	}
	.export_btn{
		display:none;
	}
  </style>
 </head>

 <body>
  <div class="container">
	<div class="search_bar" id="search_bar">
	</div>
	<div class="return_btn" id="return_btn" url="<%=sReturnUrl%>" style="cursor:pointer;" WCMAnt:param="doc_statofsite_list.back">返回</div>
	<div class="content">
		<div class="data-title">
			<div class="r">
				<div class="c">
					<span WCMAnt:param="doc_statofsite_list.site">站点</span>【<span class="detail-hostname"><%=sSiteName%></span>】<span WCMAnt:param="doc_statofsite_list.de">的</span><span class="detail-doctype"><%=getDocTypeDesc(nType,nCase)%></span><span WCMAnt:param="doc_statofsite_list.doclist">稿件列表</span>
				</div>
			</div>
		</div>
		<div class="data">
			<table cellspacing="0" cellpadding="0">
				<tbody>
					<tr class="table_title">
						<th class="first" width="40" WCMAnt:param="doc_statofsite_list.num">序号</th><th WCMAnt:param="doc_statofsite_list.docname">稿件名称</th><th width="60" WCMAnt:param="doc_statofsite_list.usename">发稿人</th><th width="60" WCMAnt:param="doc_statofsite_list.use">使用情况</th><th width="60" WCMAnt:param="doc_statofsite_list.doctype">文档性质</th>
					</tr>
<%
		
		for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++){
			try{
			ViewDocument oViewDocument = (ViewDocument)oViewDocuments.getAt(i-1);
			int nDocId = oViewDocument.getDocId();
			String sTitle = CMyString.filterForHTMLValue(oViewDocument.getPropertyAsString("DOCTITLE"));
			String sCrUser = CMyString.filterForHTMLValue(oViewDocument.getPropertyAsString("CrUser"));
			int nRecId = oViewDocument.getChnlDocProperty("RECID", 0);
			int nCurrChnlId = oViewDocument.getChannelId();
			int nDocOutUpId = oViewDocument.getPropertyAsInt("DocOutUpId", 0);
			int nModal = oViewDocument.getChnlDocProperty("MODAL", 0);
			if(nDocOutUpId>0){
				nModal = 4;
			}
			int nDocForm = oViewDocument.getChnlDocProperty("DOCFORM", 0);
%>
				<tr docId="<%=nDocId%>" chnlDocId="<%=nRecId%>" chnlId="<%=nCurrChnlId%>">
					<td class="first"><%=i%></td><td class="doctitle"><a href="#"><%=sTitle%></a></td><td><%=sCrUser%></td><td><%=getModalDesc(nModal)%></td><td><%=getDocFormDesc(nDocForm)%></td>
				</tr>
<%
			}catch(Exception ex){}
		}
%>
				</tbody>
<%
	if(nNum == 0){
%>
<tbody id="grid_NoObjectFound">
	<tr>
		<td colspan="5" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
	</tr>
</tbody>
<%
}
%>
			</table>
		</div>
		<div id="list-navigator" class="list-navigator"></div>
	</div>
	
  </div>
  <script language="javascript">
  <!--
	drawNavigator({
		PageIndex :<%=nCurrPage%>,
		PageSize : <%=nPageSize%>,
		PageCount : Math.ceil(<%=(float)nNum/nPageSize%>),
		Num : <%=nNum%>
	});
  //-->
  </script>
 </body>
</html>
<script language="javascript" src="doc_statofsite_list.js" type="text/javascript"></script>
<%!
	private String[] getParamsByType(int _nType, int _nCase,String sStartTime,String sEndTime){
		switch(_nType){
			case 1:
				return new String[]{"docStatus",_nCase+"","ContainsChildren","true","OnlyForCopy","true","StartDate",sStartTime,"EndDate",sEndTime};
			case 2:
				return new String[]{"docModal",_nCase+"","ContainsChildren","true","OnlyForCopy","true","StartDate",sStartTime,"EndDate",sEndTime};
			case 3:
				return new String[]{"docForm",_nCase+"","ContainsChildren","true","OnlyForCopy","true","StartDate",sStartTime,"EndDate",sEndTime};
			default:
				return new String[]{"ContainsChildren","true","StartDate",sStartTime,"EndDate",sEndTime};
		}
	}

	private String getDocTypeDesc (int _nType, int _nCase){
		switch(_nType){
			case 0:
				return "";
			case 1:
				return getDocStatusDesc(_nCase);
			case 2:
				return getModalDesc(_nCase);
			case 3:
				return getDocFormDesc(_nCase);
			default:
				return LocaleServer.getString("doc_statofsite_list.unknow","未知");
		}
	}

	private String getModalDesc(int _nModal){
		switch(_nModal){
			case 1:
				return LocaleServer.getString("doc_statofsite_list.origin","原稿");
			case 2:
				return LocaleServer.getString("doc_statofsite_list.quote","引用");
			case 3:
				return LocaleServer.getString("doc_statofsite_list.quote","引用");
			case 4:
				return LocaleServer.getString("doc_statofsite_list.copy","复制");
			default:
				return LocaleServer.getString("doc_statofsite_list.origin","原稿");
		}
	}

	private String getDocFormDesc(int _nDocForm){
		switch(_nDocForm){
			case 1:
				return LocaleServer.getString("doc_statofsite_list.word","文字型");
			case 2:
				return LocaleServer.getString("doc_statofsite_list.image","图片型");
			case 3:
				return LocaleServer.getString("doc_statofsite_list.audio","音频型");
			case 4:
				return LocaleServer.getString("doc_statofsite_list.video","视频型");
			default:
				return LocaleServer.getString("doc_statofsite_list.word","文字型");
		}
	}

	private String getDocStatusDesc(int _nStatus){
		switch(_nStatus){
			case 1:
				return LocaleServer.getString("doc_statofsite_list.newdoc","新稿");
			case 2:
				return LocaleServer.getString("doc_statofsite_list.edited","已编");
			case 18:
				return LocaleServer.getString("doc_statofsite_list.check","正审");
			case 16:
				return LocaleServer.getString("doc_statofsite_list.signed","已签");
			case 10:
				return LocaleServer.getString("doc_statofsite_list.sent","已发");
			case 15:
				return LocaleServer.getString("doc_statofsite_list.back","返工");
			default:
				return LocaleServer.getString("doc_statofsite_list.unknow","未知");
		}
	}

	
	private String[] combineArray(String [] sArray1, String []sArray2){
		int nLength1 = sArray1.length;
		int nLength2 = sArray2.length;
		String [] sArray = new String[nLength1 +nLength2];
		System.arraycopy(sArray1, 0, sArray, 0, nLength1);
		System.arraycopy(sArray2, 0, sArray, nLength1, nLength2);
		return sArray;
	}

%>