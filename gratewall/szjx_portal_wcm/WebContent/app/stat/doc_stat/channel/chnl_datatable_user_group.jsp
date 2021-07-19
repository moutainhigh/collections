<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@include file="chnl_doc_stat_user_group_include.jsp"%>
<%
String sReturnUrl = currRequestHelper.getString("ReturnUrl");
out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title WCMAnt:param="user_group.title"> 栏目发稿量统计 </title>
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
	.chnlorsite_desc{
		text-align:left!important;
		padding-left:5px;
		white-space:nowrap; text-overflow:ellipsis; overflow:hidden;
	}
	.tb{
		min-width:1000px;
	}
	.ext-ie6 .tb{    _width:expression((document.documentElement.clientWidth||document.body.clientWidth)<1000?"1000px":"");
	}
  </style>
 </head>

 <body>
 <iframe name="ifrmDownload" id="ifrmDownload" width=0 height=0 src="" style="display:none;"></iframe>
  <div class="container">
	<div class="search_bar" id="search_bar">
	</div>
	<div class="return_btn" id="return_btn" url="<%=sReturnUrl%>" style="cursor:pointer;"  WCMAnt:param="user_group.back">返回</div>
	<div class="content">
		<div class="data-title">
			<div class="r">
				<div class="c"  WCMAnt:param="user_group.list">
					<%=sUserOrGroup%>栏目发稿量统计列表
				</div>
			</div>
		</div>
		<div class="data">
			<table cellspacing="0" cellpadding="0" class="tb">
				<tbody>
					<tr>
						<th class="first" rowspan="2" width="50"  WCMAnt:param="user_group.num">序号</th><th rowspan="2"  WCMAnt:param="user_group.chnl">栏目名称</th><th rowspan="2"  WCMAnt:param="user_group.site">所属站点</th><th rowspan="2" width="80"  WCMAnt:param="user_group.total">总发稿量</th><th colspan="6" width="280"  WCMAnt:param="user_group.doc.count">文档状态统计</th><th colspan="3" width="180"  WCMAnt:param="user_group.count">使用情况统计</th><th colspan="4" width="200"  WCMAnt:param="user_group.doc.type">文档类型统计</th>
					</tr>
					<tr>
						<th  WCMAnt:param="user_group.new">新稿</th><th  WCMAnt:param="user_group.check">正审</th><th  WCMAnt:param="user_group.editor">已编</th><th  WCMAnt:param="user_group.signed">已签</th><th  WCMAnt:param="user_group.sent">已发</th><th  WCMAnt:param="user_group.back">返工</th><th  WCMAnt:param="user_group.src">原稿</th><th  WCMAnt:param="user_group.copt">复制</th><th  WCMAnt:param="user_group.quote">引用</th><th  WCMAnt:param="user_group.word">文字型</th><th  WCMAnt:param="user_group.pic">图片型</th><th  WCMAnt:param="user_group.audio">音频型</th><th  WCMAnt:param="user_group.cideo">视频型</th>
					</tr>
<%
		
		for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++){
			try{
				String sKey = (String) ChnlKeys.get(i-1);
				int nChnlId = Integer.parseInt(sKey);
				Channel oChannel = Channel.findById(nChnlId);
				if(oChannel==null)continue;
				String sChnlDesc = oChannel.getDesc();
				String sChnlName = oChannel.getName();
				String sSiteDesc = oChannel.getSite().getDesc();
				int nSiteId = oChannel.getSite().getId();
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
				<tr channelId="<%=nChnlId%>">
					<td class="first"><%=i%></td><td title="<%=CMyString.filterForHTMLValue(sChnlDesc)+"[Id="+nChnlId+"]"%>"><%=CMyString.transDisplay(sChnlDesc)%></td><td title="<%=CMyString.filterForHTMLValue(sSiteDesc)+"[Id="+nSiteId+"]"%>"><%=CMyString.transDisplay(sSiteDesc)%></td><td><span href="#" statType="0" case="0"><%=nDocCount%></span></td><td><span href="#" statType="1" case="<%=Status.STATUS_ID_NEW%>"><%=nDocCountOfNew%></span></td><td><span href="#" statType="1" case="<%=Status.STATUS_ID_VERIFY%>"><%=nDocCountOfVerify%></span></td><td><span href="#" statType="1" case="<%=Status.STATUS_ID_EDITED%>"><%=nDocCountOfEdited%></span></td><td><span href="#" statType="1" case="<%=Status.STATUS_ID_SIGN%>"><%=nDocCountOfSign%></span></td><td><span href="#" statType="1" case="<%=Status.STATUS_ID_PUBLISHED%>"><%=nDocCountOfPublished%></span></td><td><span href="#" statType="1" case="<%=Status.STATUS_ID_NO%>"><%=nDocCountOfNo%></span></td><td><span href="#" statType="2" case="<%=CMSConstants.CONTENT_MODAL_ENTITY%>"><%=nDocCountOfEntity%></span></td><td><span href="#" statType="2" case="<%=4%>"><%=nDocCountOfCopy%></span></td><td><span href="#" statType="2" case="<%=CMSConstants.CONTENT_MODAL_LINK%>"><%=nDocCountOfModal%></span></td><td><span href="#" statType="3" case="<%=Document.DOC_FORM_LITERY%>"><%=nDocCountOfLitery%></span></td><td><span href="#" statType="3" case="<%=Document.DOC_FORM_PIC%>"><%=nDocCountOfPic%></span></td><td><span href="#" statType="3" case="<%=Document.DOC_FORM_AUDIO%>"><%=nDocCountOfAudio%></span></td><td><span href="#" statType="3" case="<%=Document.DOC_FORM_VIDEO%>"><%=nDocCountOfVideo%></span></td>
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
			<td colspan="17" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
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
<script language="javascript" src="chnl_datatable_user_group.js" type="text/javascript"></script>