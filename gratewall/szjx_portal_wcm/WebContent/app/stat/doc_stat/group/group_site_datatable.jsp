<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@include file="group_site_doc_stat_include.jsp"%>
<%
String sReturnUrl = currRequestHelper.getString("ReturnUrl");
out.clear();%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title WCMAnt:param="site_datatable.titel"> 部门发稿量统计 </title>
  <script language="javascript">
  <!--
  		// 这里指定从当前页面进入到别的页面以后，要返回的页面的url
		var sReturnUrl = "<%=sReturnUrl%>";
  //-->
  </script>
  <script language="javascript" src="../../../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script src="../../../js/source/wcmlib/WCMConstants.js"></script>
  <script language="javascript" src="../../../js/easyversion/extrender.js" type="text/javascript"></script>
  <script src="../../../js/easyversion/ajax.js"></script>
  <script src="../../../js/easyversion/basicdatahelper.js"></script>
  <script src="../../../js/easyversion/web2frameadapter.js"></script>
  <script language="javascript" src="../../../js/easyversion/elementmore.js" type="text/javascript"></script>
  <script src="../../../js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../../../js/easyversion/calendar_lang/$locale$.js"></script>
  <script language="javascript" src="../../../js/easyversion/calendar3.js" type="text/javascript"></script>
	
  <script language="javascript" src="../../js/common.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/search_bar/search_bar.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/tab/tab.js" type="text/javascript"></script>
  <!--引入分页JS-->
  <script language="javascript" src="../../js/page_nav/page.js" type="text/javascript"></script>
  <!--自身引入JS-->
  <script language="javascript" src="group_datatable.js" type="text/javascript"></script>
  <script language="javascript" src="../user/export_stat_toexcel.js" type="text/javascript"></script>
  <link href="../../js/page_nav/page.css" rel="stylesheet" type="text/css" />
  <link href="../../js/tab/tab.css" rel="stylesheet" type="text/css" />
  <link href="../../js/search_bar/search_bar.css" rel="stylesheet" type="text/css" />
  <link href="../../css/calendar.css" rel="stylesheet" type="text/css" />
  <link href="../table-list.css" rel="stylesheet" type="text/css" />
  <style type="text/css">
	.hostname{
		color:red;
		padding:0 2px;
	}
	.tb{
		min-width:920px;
	}
	.ext-ie6 .tb{
       _width:expression((document.documentElement.clientWidth||document.body.clientWidth)<920?"920px":"");
	}
  </style>
 </head>

 <body>
  <div class="container">
	<div class="search_bar" id="search_bar">
	</div>
	<div class="return_btn" id="return_btn" url="<%=sReturnUrl%>" style="cursor:pointer;" WCMAnt:param="site_datatable.back">返回</div>
	<div class="content">
		<div class="data-title">
			<div class="r">
				<div class="c">
					<span WCMAnt:param="site_datatable.site">站点</span>【<span class="detail-hostname"><%=sSiteDesc%></span>】<span WCMAnt:param="site_datatable.fgl">发稿量统计列表</span>
				</div>
			</div>
		</div>
		<div class="data">
			<table cellspacing="0" cellpadding="0" class="tb">
				<tbody>
					<tr>
						<th class="first" rowspan="2"  WCMAnt:param="site_datatable.num">序号</th><th rowspan="2"  WCMAnt:param="site_datatable.department">所属部门</th><th rowspan="2" width="80"  WCMAnt:param="site_datatable.total">总发稿量</th><th colspan="6" width="280"  WCMAnt:param="site_datatable.docstate">文档状态统计</th><th colspan="3" width="180"  WCMAnt:param="site_datatable.use">使用情况统计</th><th colspan="4" width="200"  WCMAnt:param="site_datatable.doctype">文档类型统计</th>
					</tr>
					<tr>
						<th  WCMAnt:param="site_datatable.new">新稿</th><th WCMAnt:param="site_datatable.check">正审</th><th  WCMAnt:param="site_datatable.edited">已编</th><th  WCMAnt:param="site_datatable.signed">已签</th><th  WCMAnt:param="site_datatable.sent">已发</th><th  WCMAnt:param="site_datatable.back">返工</th><th  WCMAnt:param="site_datatable.origin">原稿</th><th  WCMAnt:param="site_datatable.copy">复制</th><th  WCMAnt:param="site_datatable.quote">引用</th><th  WCMAnt:param="site_datatable.word">文字型</th><th  WCMAnt:param="site_datatable.image">图片型</th><th  WCMAnt:param="site_datatable.audio">音频型</th><th  WCMAnt:param="site_datatable.video">视频型</th>
					</tr>
<%
				//遍历生成表现
				for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {			
					try{
						//反解出构造的key数组
						DocStatHandler4UserDeptSite temp = new DocStatHandler4UserDeptSite(nSiteId);
						String[] sKeyGroup = temp.makeFields((String)arGroupKeys.get(i-1));	
						String sGroupKey = sKeyGroup[0];

						int nGroupId = Integer.parseInt(sGroupKey);
						Group group = Group.findById(nGroupId);
						String sGroupName = group.getName();

						//实体型文档数目
						int nEntityNum = result.getResult(3,new String[]{sGroupKey,CMSConstants.CONTENT_MODAL_ENTITY + ""});
						int nCopyNum = result.getResult(4, nGroupId + "");
						nEntityNum = nEntityNum - nCopyNum;

						int nLinkNum = result.getResult(3,new String[]{sGroupKey,CMSConstants.CONTENT_MODAL_LINK + ""});
						int nMirrorNum = result.getResult(3,new String[]{sGroupKey,CMSConstants.CONTENT_MODAL_MIRROR + ""});
%>
					<tr GroupId=<%=nGroupId%>>
						<td class="first"><%=i%></td>
						<td title="<%=CMyString.filterForHTMLValue(sGroupName)%>"><%=CMyString.transDisplay(sGroupName)%></td>
						<td all="true">
						<a href="#"><%=result.getResult(1,sGroupKey)%></td>	
						<!--状态-->
						<td docStatus=<%=Status.STATUS_ID_NEW%> >
						<a href="#"><%=result.getResult(2,new String[]{sGroupKey,Status.STATUS_ID_NEW + ""})%></a></td>
						<td docStatus=<%=Status.STATUS_ID_VERIFY%> >
						<a href="#"><%=result.getResult(2,new String[]{sGroupKey,Status.STATUS_ID_VERIFY +""})%></a></td>
						<td docStatus=<%=Status.STATUS_ID_EDITED%> >
						<a href="#"><%=result.getResult(2,new String[]{sGroupKey,Status.STATUS_ID_EDITED +""})%></a></td>
						<td docStatus=<%=Status.STATUS_ID_SIGN%> >
						<a href="#"><%=result.getResult(2,new String[]{sGroupKey,Status.STATUS_ID_SIGN +""})%></a></td>
						<td docStatus=<%=Status.STATUS_ID_PUBLISHED%> >
						<a href="#"><%=result.getResult(2,new String[]{sGroupKey,Status.STATUS_ID_PUBLISHED +""})%></a></td>					
						<td docStatus=<%=Status.STATUS_ID_AGAIN%> >
						<a href="#"><%=result.getResult(2,new String[]{sGroupKey,Status.STATUS_ID_AGAIN +""})%></a></td>
						<!--创作方式-->
						<td docModal=<%=CMSConstants.CONTENT_MODAL_ENTITY%> >
						<a href="#"><%=nEntityNum%></a></td>
						<td docModal=<%=CMSConstants.CONTENT_MODAL_COPY%> >
						<a href="#"><%=nCopyNum%></a></td>
						<td docModal=<%=CMSConstants.CONTENT_MODAL_QUOTE%> >
						<a href="#"><%=nLinkNum + nMirrorNum%></a></td>
						<!--文档所属-->
						<td docForm = <%=Document.DOC_FORM_LITERY%>>
						<a href="#"><%=result.getResult(5,new String[]{sGroupKey,Document.DOC_FORM_LITERY + ""})%></a></td>
						<td docForm = <%=Document.DOC_FORM_PIC%>>
						<a href="#"><%=result.getResult(5,new String[]{sGroupKey,Document.DOC_FORM_PIC + ""})%></a></td>
						<td docForm = <%=Document.DOC_FORM_AUDIO%>>
						<a href="#"><%=result.getResult(5,new String[]{sGroupKey,Document.DOC_FORM_AUDIO + ""})%></a></td>
						<td docForm = <%=Document.DOC_FORM_VIDEO%>>
						<a href="#"><%=result.getResult(5,new String[]{sGroupKey,Document.DOC_FORM_VIDEO + ""})%></a></td>
					</tr>
<%
						}catch(Exception ex){
							ex.printStackTrace();
						}
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
		<div id="tab-nav">
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