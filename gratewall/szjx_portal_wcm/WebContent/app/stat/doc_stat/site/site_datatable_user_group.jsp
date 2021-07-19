<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@include file="site_doc_stat_user_group_include.jsp"%>
<%
String sReturnUrl = currRequestHelper.getString("ReturnUrl");
out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title> 站点发稿量列表 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <script language="javascript" src="../../../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/extrender.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/elementmore.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/ajax.js" type="text/javascript"></script>
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
	.ext-ie6 a{color:#0000ff;}
	.site_desc{
		text-align:center !important;
		padding-left:5px;
		white-space:nowrap; text-overflow:ellipsis; overflow:hidden;
	}
	.tb{
		min-width:1000px;
	}
	.ext-ie6 .tb{
       _width:expression((document.documentElement.clientWidth||document.body.clientWidth)<1000?"1000px":"");
	}
  </style>
 </head>

 <body>
 <iframe name="ifrmDownload" id="ifrmDownload" width=0 height=0 src="" style="display:none;"></iframe>
  <div class="container">
	<div class="search_bar" id="search_bar">
	</div>
	<div class="return_btn" id="return_btn" url="<%=sReturnUrl%>" style="cursor:pointer;">返回</div>
	<div class="content">
		<div class="data-title">
			<div class="r">
				<div class="c">
					<%=sUserOrGroup%><span WCMAnt:param="user_group.fgl">站点发稿量统计列表</span>
				</div>
			</div>
		</div>
		<div class="data">
			<table cellspacing="0" cellpadding="0" class="tb">
				<tbody>
					<tr>
						<th class="first" rowspan="2" width="50">序号</th><th rowspan="2">站点名称</th><th rowspan="2" width="80">总发稿量</th><th colspan="6" width="280">文档状态统计</th><th colspan="3" width="180">使用情况统计</th><th colspan="4" width="200">文档类型统计</th>
					</tr>
					<tr>
						<th>新稿</th><th>正审</th><th>已编</th><th>已签</th><th>已发</th><th>返工</th><th>原稿</th><th>复制</th><th>引用</th><th>文字型</th><th>图片型</th><th>音频型</th><th>视频型</th>
					</tr>
<%
		
		for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++){
			try{
				String sKey = (String) SiteKeys.get(i-1);
				int nSiteId = Integer.parseInt(sKey);
				WebSite oWebSite = WebSite.findById(nSiteId);
				if(oWebSite==null)continue;
				String sSiteName = oWebSite.getName();
				String sSiteDesc = oWebSite.getDesc();
				int nDocCount = result.getResult(1, sKey);

				int nDocCountOfNew = result.getResult(2, new String[]{sKey,String.valueOf(Status.STATUS_ID_NEW)});
				int nDocCountOfVerify = result.getResult(2, new String[]{sKey,String.valueOf(Status.STATUS_ID_VERIFY)});
				int nDocCountOfEdited = result.getResult(2, new String[]{sKey,String.valueOf(Status.STATUS_ID_EDITED)});
				int nDocCountOfSign = result.getResult(2, new String[]{sKey,String.valueOf(Status.STATUS_ID_SIGN)});
				int nDocCountOfPublished = result.getResult(2, new String[]{sKey,String.valueOf(Status.STATUS_ID_PUBLISHED)});
				int nDocCountOfNo = result.getResult(2, new String[]{sKey,String.valueOf(Status.STATUS_ID_NO)});

				int nDocCountOfEntity = result.getResult(3, new String[]{sKey,String.valueOf(CMSConstants.CONTENT_MODAL_ENTITY)});
				int nDocCountOfLinkModal = result.getResult(3, new String[]{sKey,String.valueOf(CMSConstants.CONTENT_MODAL_LINK)});
				int nDocCountOfMirrorModal = result.getResult(3, new String[]{sKey,String.valueOf(CMSConstants.CONTENT_MODAL_MIRROR)});
				int nDocCountOfModal = nDocCountOfLinkModal + nDocCountOfMirrorModal;
				int nDocCountOfCopy = result.getResult(4, sKey);
				//nDocCountOfEntity = nDocCountOfEntity - nDocCountOfCopy;

				int nDocCountOfLitery = result.getResult(5, new String[]{sKey,String.valueOf(Document.DOC_FORM_LITERY)});
				int nDocCountOfPic = result.getResult(5, new String[]{sKey,String.valueOf(Document.DOC_FORM_PIC)});
				int nDocCountOfAudio = result.getResult(5, new String[]{sKey,String.valueOf(Document.DOC_FORM_AUDIO)});
				int nDocCountOfVideo = result.getResult(5, new String[]{sKey,String.valueOf(Document.DOC_FORM_VIDEO)});
%>
				<tr siteId="<%=nSiteId%>">
					<td class="first"><%=i%></td><td title="<%=CMyString.filterForHTMLValue(sSiteDesc)+"[Id="+nSiteId+"]"%>" class="site_desc"><%=CMyString.transDisplay(sSiteDesc)%></td><td><span href="#" statType="0" case="0"><%=nDocCount%></span></td><td><span href="#" statType="1" case="<%=Status.STATUS_ID_NEW%>"><%=nDocCountOfNew%></span></td><td><span href="#" statType="1" case="<%=Status.STATUS_ID_VERIFY%>"><%=nDocCountOfVerify%></span></td><td><span href="#" statType="1" case="<%=Status.STATUS_ID_EDITED%>"><%=nDocCountOfEdited%></span></td><td><span href="#" statType="1" case="<%=Status.STATUS_ID_SIGN%>"><%=nDocCountOfSign%></span></td><td><span href="#" statType="1" case="<%=Status.STATUS_ID_PUBLISHED%>"><%=nDocCountOfPublished%></span></td><td><span href="#" statType="1" case="<%=Status.STATUS_ID_NO%>"><%=nDocCountOfNo%></span></td><td><span href="#" statType="2" case="<%=CMSConstants.CONTENT_MODAL_ENTITY%>"><%=nDocCountOfEntity%></span></td><td><span href="#" statType="2" case="<%=4%>"><%=nDocCountOfCopy%></span></td><td><span href="#" statType="2" case="<%=CMSConstants.CONTENT_MODAL_LINK%>"><%=nDocCountOfModal%></span></td><td><span href="#" statType="3" case="<%=Document.DOC_FORM_LITERY%>"><%=nDocCountOfLitery%></span></td><td><span href="#" statType="3" case="<%=Document.DOC_FORM_PIC%>"><%=nDocCountOfPic%></span></td><td><span href="#" statType="3" case="<%=Document.DOC_FORM_AUDIO%>"><%=nDocCountOfAudio%></span></td><td><span href="#" statType="3" case="<%=Document.DOC_FORM_VIDEO%>"><%=nDocCountOfVideo%></span></td>
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
			<td colspan="16" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
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
		<div class="" id="tab-nav">
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
<script language="javascript" src="site_datatable_user_group.js" type="text/javascript"></script>