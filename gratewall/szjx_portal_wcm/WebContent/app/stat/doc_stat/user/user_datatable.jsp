<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@include file="user_doc_stat_include.jsp"%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title WCMAnt:param="user_datatable.userpublishdatatable"> 用户发稿量数据表 </title>
  <script language="javascript">
  <!--
		// 这里指定从当前页面进入到别的页面以后，要返回的页面的url
		var sReturnUrl = "../../doc_stat/user/user_datatable.jsp";
  //-->
  </script>
  <script language="javascript" src="../../../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script src="../../../js/source/wcmlib/WCMConstants.js"></script>
  <script language="javascript" src="../../../js/easyversion/extrender.js" type="text/javascript"></script>
  <script src="../../../js/easyversion/ajax.js"></script>
  <script src="../../../js/easyversion/basicdatahelper.js"></script>
  <script src="../../../js/easyversion/web2frameadapter.js"></script>
  <script language="javascript" src="../../../js/easyversion/elementmore.js" type="text/javascript"></script>
  <script src="../../../js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../js/easyversion/calendar_lang/$locale$.js"></script>
  <script language="javascript" src="../../../js/easyversion/calendar3.js" type="text/javascript"></script>	
  <script language="javascript" src="../../js/common.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/search_bar/search_bar.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/tab/tab.js" type="text/javascript"></script>
  <!--引入分页JS-->
  <script language="javascript" src="../../js/page_nav/page.js" type="text/javascript"></script>
  <!--自身引入JS-->
  <script language="javascript" src="user_datatable.js" type="text/javascript"></script>
  <script language="javascript" src="export_stat_toexcel.js" type="text/javascript"></script>
  <link href="../../js/page_nav/page.css" rel="stylesheet" type="text/css" />
  <link href="../../js/tab/tab.css" rel="stylesheet" type="text/css" />
  <link href="../../js/search_bar/search_bar.css" rel="stylesheet" type="text/css" />
  <link href="../../css/calendar.css" rel="stylesheet" type="text/css" />
  <link href="../table-list.css" rel="stylesheet" type="text/css" />
  <style type="text/css">
	.tb{
		min-width:1100px;
	}
	.ext-ie6 .tb{
       _width:expression((document.documentElement.clientWidth||document.body.clientWidth)<1100?"1100px":"");
	}
  </style>
</head>
 <body>
  <div class="container">
	<div class="search_bar" id="search_bar">
	</div>
	<div class="content">
		<div class="data-title">
			<div class="r">
				<div class="c" WCMAnt:param="user_datatable.chart">
					用户发稿量统计列表
				</div>
			</div>
		</div>
		<div class="data">
		<table cellspacing="0" cellpadding="0" class="tb">
			<tr>
				<th class="first" rowspan="2" WCMAnt:param="user_datatable.num">序号</th><th rowspan="2" width="160" WCMAnt:param="user_datatable.username">发稿人</th><th rowspan="2" WCMAnt:param="user_datatable.department">所属部门</th><th rowspan="2" width="80" WCMAnt:param="user_datatable.total">总发稿量</th><th colspan="6" width="280" WCMAnt:param="user_datatable.docstate">文档状态统计</th><th colspan="3" width="180" WCMAnt:param="user_datatable.use">使用情况统计</th><th colspan="4" width="200" WCMAnt:param="user_datatable.doctype">文档类型统计</th><th colspan="2" width="120" WCMAnt:param="user_datatable.docdistribute">文档分布统计</th>
			</tr>
			<tr>
				<th WCMAnt:param="user_datatable.newdoc">新稿</th><th WCMAnt:param="user_datatable.check">正审</th><th WCMAnt:param="user_datatable.edited">已编</th><th WCMAnt:param="user_datatable.signed">已签</th><th WCMAnt:param="user_datatable.sent">已发</th><th WCMAnt:param="user_datatable.back">返工</th><th WCMAnt:param="user_datatable.origin">原稿</th><th WCMAnt:param="user_datatable.copy">复制</th><th WCMAnt:param="user_datatable.quote">引用</th><th WCMAnt:param="user_datatable.word">文字型</th><th WCMAnt:param="user_datatable.image">图片型</th><th WCMAnt:param="user_datatable.audio">音频型</th><th WCMAnt:param="user_datatable.video">视频型</th><th WCMAnt:param="user_datatable.site">站点</th><th WCMAnt:param="user_datatable.chnl">栏目</th>
			</tr>
			<tbody>

<%
		//遍历生成表现
		for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {			
			try{
				String sUserName = (String) arUserNames.get(i-1);
				//获取所属组织
				User currUser = User.findByName(sUserName);
				if(currUser == null) continue;
				Groups groups = currUser.getGroups();
				String sGroupName = "无";
				Group temp = null;
				if(groups.size() > 0){
					sGroupName = "";
					for(int j=0,nsize= groups.size(); j< nsize; j++){
						temp = (Group) groups.getAt(j);
						if(temp == null) continue;
						if(j != nsize-1){
							sGroupName += temp.getName() + " , ";
						}else{
							sGroupName += temp.getName();
						}
					}
				}
				//实体型文档数目
				int nEntityNum = result.getResult(3,new String[]{sUserName,CMSConstants.CONTENT_MODAL_ENTITY + ""});
				int nCopyNum = result.getResult(4,sUserName);
				nEntityNum = nEntityNum - nCopyNum;

				int nLinkNum = result.getResult(3,new String[]{sUserName,CMSConstants.CONTENT_MODAL_LINK + ""});
				int nMirrorNum = result.getResult(3,new String[]{sUserName,CMSConstants.CONTENT_MODAL_MIRROR + ""});

%>
				<tr UserName = "<%=sUserName%>">
					<td class="first"><%=i%></td>
					<td title="<%=CMyString.filterForHTMLValue(sUserName)%>"><%=CMyString.transDisplay(sUserName)%></td>
					<td class="othertd" title="<%=CMyString.filterForHTMLValue(sGroupName)%>"><%=CMyString.transDisplay(sGroupName)%></td>
					<td all="true">
					<a href="#"><%=result.getResult(1,sUserName)%></td>	
					<!--状态-->
					<td docStatus=<%=Status.STATUS_ID_NEW%> >
					<a href="#"><%=result.getResult(2,new String[]{sUserName,Status.STATUS_ID_NEW + ""})%></a></td>	
					<td docStatus=<%=Status.STATUS_ID_VERIFY%> >
					<a href="#"><%=result.getResult(2,new String[]{sUserName,Status.STATUS_ID_VERIFY + ""})%></a></td>
					<td docStatus=<%=Status.STATUS_ID_EDITED%> >
					<a href="#"><%=result.getResult(2,new String[]{sUserName,Status.STATUS_ID_EDITED + ""})%></a></td>
					<td docStatus=<%=Status.STATUS_ID_SIGN%> >
					<a href="#"><%=result.getResult(2,new String[]{sUserName,Status.STATUS_ID_SIGN + ""})%></a></td>
					<td docStatus=<%=Status.STATUS_ID_PUBLISHED%> >
					<a href="#"><%=result.getResult(2,new String[]{sUserName,Status.STATUS_ID_PUBLISHED +""})%></a></td>
					<td docStatus=<%=Status.STATUS_ID_AGAIN%> >
					<a href="#"><%=result.getResult(2,new String[]{sUserName,Status.STATUS_ID_AGAIN + ""})%></a></td>
					<!--创作方式-->
					<td docModal=<%=CMSConstants.CONTENT_MODAL_ENTITY%> >
					<a href="#"><%=nEntityNum%></a></td>
					<td docModal=<%=CMSConstants.CONTENT_MODAL_COPY%> >
					<a href="#"><%=nCopyNum%></a></td>
					<td docModal=<%=CMSConstants.CONTENT_MODAL_QUOTE%> >
					<a href="#"><%=nLinkNum + nMirrorNum%></a></td>
					<!--文档所属-->
					<td docForm = <%=Document.DOC_FORM_LITERY%>>
					<a href="#"><%=result.getResult(5,new String[]{sUserName,Document.DOC_FORM_LITERY + ""})%></a></td>
					<td docForm = <%=Document.DOC_FORM_PIC%>>
					<a href="#"><%=result.getResult(5,new String[]{sUserName,Document.DOC_FORM_PIC + ""})%></a></td>
					<td docForm = <%=Document.DOC_FORM_AUDIO%>>
					<a href="#"><%=result.getResult(5,new String[]{sUserName,Document.DOC_FORM_AUDIO + ""})%></a></td>
					<td docForm = <%=Document.DOC_FORM_VIDEO%>>
					<a href="#"><%=result.getResult(5,new String[]{sUserName,Document.DOC_FORM_VIDEO + ""})%></a></td>
					<td forSite="<%=sUserName%>">
					<a href="#" WCMAnt:param="user_datatable.examine">查看</a></td>
					<td forChannel="<%=sUserName%>">
					<a href="#"  WCMAnt:param="user_datatable.examine">查看</a></td>
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
			<td colspan="19" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
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