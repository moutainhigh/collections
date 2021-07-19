<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
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
<%@ page import="java.util.HashMap" %>
<%@include file="../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%
	//获取标识参数
	int nDocStatus = currRequestHelper.getInt("DocStatus",0);
	int nDocForm = currRequestHelper.getInt("DocForm",0);
	int nDocModal = currRequestHelper.getInt("DocModal",0);
	String sTitleDesc = getDescription(nDocStatus,nDocForm,nDocModal);
	
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	//获取时间
	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];
	//获取分页参数
	int nPageSize = currRequestHelper.getInt("PageSize",20);
	int nCurrPage = currRequestHelper.getInt("CurrPage",1);
	// 获取发稿人
	String sUserName = currRequestHelper.getString("UserName");
	// 判断用户是否存在
	User crUser = User.findByName(sUserName);
	if(crUser == null)
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("doc_detail_list.user.not.found","没有找到用户[{0}]"),new String[]{sUserName}));
	//获取其他检索条件，例如：状态（1）、使用情况（2），内容类型（3）
	String sSearchItem = currRequestHelper.getString("SelectItem");
	String sSearchValue = currRequestHelper.getString("SearchValue");

	// 获取各种参数，加入到hashmap中
	HashMap paramMap = new HashMap();
	if(!CMyString.isEmpty(sSearchValue) && !CMyString.isEmpty(sSearchItem)){
		paramMap.put(sSearchItem,sSearchValue);
	}
	int m_nDocStatus = currRequestHelper.getInt("DocStatus",-1);
	int m_nDocModal = currRequestHelper.getInt("DocModal",-1);
	int m_nDocForm = currRequestHelper.getInt("DocForm",-1);
	if(m_nDocForm!=-1){
		paramMap.put("DocForm",new Integer(m_nDocForm));
	}
	if(m_nDocModal!=-1){
		paramMap.put("OnlyForCopy","1");
		paramMap.put("DocModal",new Integer(m_nDocModal));
	}
	if(m_nDocStatus!=-1){
		paramMap.put("DocStatus",new Integer(m_nDocStatus));
	}
	paramMap.put("CrUser",sUserName);
	paramMap.put("PAGESIZE",nPageSize + "");
	paramMap.put("CURRPAGE",nCurrPage + "");
	// 指定查询范围
	int nSiteId = currRequestHelper.getInt("SiteId",0);
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	if(nChannelId > 0){
		paramMap.put("ChannelIds",nChannelId + "");
	}else if(nSiteId > 0){
		paramMap.put("SiteIds",nSiteId + "");
	}else{
		paramMap.put("SiteType","-1");
	}
	paramMap.put("StartDate",sStartTime);
	paramMap.put("EndDate",sEndTime);
	paramMap.put("IsInStat", "true");
	//获取文档集合
	ViewDocuments oViewDocuments = (ViewDocuments) processor.excute("wcm61_viewdocument","query",paramMap);

	//构造分页参数
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nCurrPage);
	int nNum = oViewDocuments.size();
	currPager.setItemCount(nNum);

	String sReturnUrl = currRequestHelper.getString("ReturnUrl");
%>
<% out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="doc_detail_list.title"> 用户发稿量统计 </title>
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
  <script language="javascript" src="doc_detail_list.js" type="text/javascript"></script>
  <link href="../table-list.css" rel="stylesheet" type="text/css" />
  <link href="../../js/page_nav/page.css" rel="stylesheet" type="text/css" />
  <link href="../../js/tab/tab.css" rel="stylesheet" type="text/css" />
  <link href="../../js/search_bar/search_bar.css" rel="stylesheet" type="text/css" />
  <link href="../../css/calendar.css" rel="stylesheet" type="text/css" />

  <style type="text/css">
	.data table .first{
		width:60px;
	}
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
	<div class="return_btn" id="return_btn" url="<%=sReturnUrl%>" style="cursor:pointer;" WCMAnt:param="doc_detail_list.back">返回</div>
	<div class="content">
		<div class="data-title">
			<div class="r">
				<div class="c">
					<span WCMAnt:param="doc_detail_list.user">用户</span>【<span class="detail-hostname"><%=sUserName%></span>】<span WCMAnt:param="doc_detail_list.de">的</span><span class="detail-hostname"><%=sTitleDesc%></span><span WCMAnt:param="doc_detail_list.list">稿件列表</span>
				</div>
			</div>
		</div>
		<div class="data">
			<table cellspacing="0" cellpadding="0">
				<tbody>
					<tr class="table_title">
						<th width="40" WCMAnt:param="doc_detail_list.num">序号</th><th WCMAnt:param="doc_detail_list.docname">稿件名称</th><th width="140" WCMAnt:param="doc_detail_list.crtime">创建时间</th><th width="60" WCMAnt:param="doc_detail_list.docstate">文档状态</th><th width="60" WCMAnt:param="doc_detail_list.use">使用情况</th><th width="60" WCMAnt:param="doc_detail_list.doctype">文档性质</th><th width="100" WCMAnt:param="doc_detail_list.chnlname">栏目名称</th><th width="100" WCMAnt:param="doc_detail_list.sitename">站点名称</th>
					</tr>
<%
		
		for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++){
			try{
			ViewDocument oViewDocument = (ViewDocument)oViewDocuments.getAt(i-1);
			int nDocId = oViewDocument.getDocId();
			int nRecId = oViewDocument.getChnlDocProperty("RECID", 0);
			ChnlDoc currChnlDoc = ChnlDoc.findById(nRecId);
			String sTitle = CMyString.filterForHTMLValue(oViewDocument.getPropertyAsString("DOCTITLE"));
			String sCrUser = CMyString.filterForHTMLValue(currChnlDoc.getPropertyAsString("CrUser"));
			String sCrTime = CMyString.filterForHTMLValue(currChnlDoc.getPropertyAsString("CrTime"));
			int nDocOutUpId = oViewDocument.getChnlDocProperty("DocOutUpId", 0);
			int nModal = oViewDocument.getChnlDocProperty("MODAL", 0);
			int nCurrChnlId = oViewDocument.getChannelId();
			int nDocStatusId = oViewDocument.getStatusId();
			Channel channel = Channel.findById(nCurrChnlId);
			WebSite website = channel.getSite(); 

			int nCurrDocForm = oViewDocument.getChnlDocProperty("DOCFORM", 0);
%>
				<tr docId="<%=nDocId%>" chnlDocId="<%=nRecId%>" chnlId="<%=nCurrChnlId%>">
					<td class="first"><%=i%></td><td class="doctitle"><a href="#"><%=sTitle%></a></td><td><%=sCrTime%></td><td><%=getDOCStatusName(nDocStatusId)%></td><td><%=getModalDesc(nModal,nDocOutUpId)%></td><td><%=getDocFormDesc(nCurrDocForm)%></td><td><%=CMyString.transDisplay(channel.getDesc())%></td><td><%=CMyString.transDisplay(website.getDesc())%></td>
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
		<td colspan="8" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
	</tr>
</tbody>
<%
}
%>
			</table>
		</div>
		<div id="list-navigator" class="list-navigator"></div>
	</div>
	<div class="foot">
		<div class="tab-nav" id="tab-nav">
		</div>
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

	private String getModalDesc(int _nModal){
		return getModalDesc(_nModal,0);
	}

	private String getModalDesc(int _nModal , int _nOutUpId){
		switch(_nModal){
			case 1:
				if(_nOutUpId > 0){
					return LocaleServer.getString("doc_detail_list.copy","复制");
				}else{
					return LocaleServer.getString("doc_detail_list.origin","原稿");
				}
			case 2:
				return LocaleServer.getString("doc_detail_list.quote","引用");
			case 3:
				return LocaleServer.getString("doc_detail_list.quote","引用");
			case 4:
				return LocaleServer.getString("doc_detail_list.copy","复制");
			default:
				return LocaleServer.getString("doc_detail_list.origin","原稿");
		}
	}

	private String getDocFormDesc(int _nDocForm){
		switch(_nDocForm){
			case 1:
				return LocaleServer.getString("doc_detail_list.word","文字型");
			case 2:
				return LocaleServer.getString("doc_detail_list.image","图片型");
			case 3:
				return LocaleServer.getString("doc_detail_list.audio","音频型");
			case 4:
				return LocaleServer.getString("doc_detail_list.video","视频型");
			default:
				return LocaleServer.getString("doc_detail_list.unknow","未知");
		}
	}
	
	private String getDescription(int _nDocStatus, int _nDocForm, int _nDocModal){
		if(_nDocStatus > 0){
			return getDOCStatusName(_nDocStatus);
		}
		if(_nDocForm > 0){
			return getDocFormDesc(_nDocForm);
		}
		if(_nDocModal > 0){
			return getModalDesc(_nDocModal);
		}
		return "";
	}

%>