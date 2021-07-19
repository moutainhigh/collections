<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@include file="site_doc_stat_include.jsp"%>
<% out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="site_datatable.sitepublishdoclist"> 站点发稿量列表 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <script language="javascript">
  <!--
  		// 这里指定从当前页面进入到别的页面以后，要返回的页面的url
		var sReturnUrl = "../site/site_datatable.jsp";
  //-->
  </script>
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
	<div class="content">
		<div class="data-title">
			<div class="r">
				<div class="c" WCMAnt:param="site_datatable.sitepublishcountlist">
					站点发稿量统计列表
				</div>
			</div>
		</div>
		<div class="data">
			<table cellspacing="0" cellpadding="0" class="tb">
				<tbody>
					<tr>
						<th class="first" rowspan="2" width="50" WCMAnt:param="site_datatable.num">序号</th><th rowspan="2" WCMAnt:param="site_datatable.sitename">站点名称</th><th rowspan="2" width="80" WCMAnt:param="site_datatable.total">总发稿量</th><th colspan="6" width="260" WCMAnt:param="site_datatable.docstate">文档状态统计</th><th colspan="3" width="180" WCMAnt:param="site_datatable.use">使用情况统计</th><th colspan="4" width="200" WCMAnt:param="site_datatable.doctype">文档类型统计</th><th colspan="2" width="120" WCMAnt:param="site_datatable.docditribute">文档分布统计</th>
					</tr>
					<tr>
						<th WCMAnt:param="site_datatable.newdoc">新稿</th><th WCMAnt:param="site_datatable.check">正审</th><th WCMAnt:param="site_datatable.edited">已编</th><th WCMAnt:param="site_datatable.signed">已签</th><th WCMAnt:param="site_datatable.sent">已发</th><th WCMAnt:param="site_datatable.back">返工</th><th WCMAnt:param="site_datatable.origin">原稿</th><th WCMAnt:param="site_datatable.copy">复制</th><th WCMAnt:param="site_datatable.quote">引用</th><th WCMAnt:param="site_datatable.word">文字型</th><th WCMAnt:param="site_datatable.image">图片型</th><th WCMAnt:param="site_datatable.audio">音频型</th><th WCMAnt:param="site_datatable.video">视频型</th><th WCMAnt:param="site_datatable.department">部门</th><th WCMAnt:param="site_datatable.person">个人</th>
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
					<td class="first"><%=i%></td><td title="<%=CMyString.filterForHTMLValue(sSiteName)+"[Id="+nSiteId+"]"%>" class="site_desc"><span><%=CMyString.transDisplay(sSiteDesc)%></span></td><td><a href="#" statType="0" case="0"><%=nDocCount%></a></td><td><a href="#" statType="1" case="<%=Status.STATUS_ID_NEW%>"><%=nDocCountOfNew%></a></td><td><a href="#" statType="1" case="<%=Status.STATUS_ID_VERIFY%>"><%=nDocCountOfVerify%></a></td><td><a href="#" statType="1" case="<%=Status.STATUS_ID_EDITED%>"><%=nDocCountOfEdited%></a></td><td><a href="#" statType="1" case="<%=Status.STATUS_ID_SIGN%>"><%=nDocCountOfSign%></a></td><td><a href="#" statType="1" case="<%=Status.STATUS_ID_PUBLISHED%>"><%=nDocCountOfPublished%></a></td><td><a href="#" statType="1" case="<%=Status.STATUS_ID_NO%>"><%=nDocCountOfNo%></a></td><td><a href="#" statType="2" case="<%=CMSConstants.CONTENT_MODAL_ENTITY%>"><%=nDocCountOfEntity%></a></td><td><a href="#" statType="2" case="<%=4%>"><%=nDocCountOfCopy%></a></td><td><a href="#" statType="2" case="<%=CMSConstants.CONTENT_MODAL_LINK%>"><%=nDocCountOfModal%></a></td><td><a href="#" statType="3" case="<%=Document.DOC_FORM_LITERY%>"><%=nDocCountOfLitery%></a></td><td><a href="#" statType="3" case="<%=Document.DOC_FORM_PIC%>"><%=nDocCountOfPic%></a></td><td><a href="#" statType="3" case="<%=Document.DOC_FORM_AUDIO%>"><%=nDocCountOfAudio%></a></td><td><a href="#" statType="3" case="<%=Document.DOC_FORM_VIDEO%>"><%=nDocCountOfVideo%></a></td><td><a href="#" byGroup="true" WCMAnt:param="site_datatable.examine">查看</a></td><td><a href="#" byUser="true" WCMAnt:param="site_datatable.examine">查看</a></td>
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
			<td colspan="18" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
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
<script language="javascript" src="site_datatable.js" type="text/javascript"></script>