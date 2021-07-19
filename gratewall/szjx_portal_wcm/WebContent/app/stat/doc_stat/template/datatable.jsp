<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@include file="#PREFIX#_stat_include.jsp"%>
<% out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title> #PREFIX_DESC#发稿量列表 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <script language="javascript">
  <!--
  		// 这里指定从当前页面进入到别的页面以后，要返回的页面的url
		var sReturnUrl = "#PREFIX#_datatable.jsp";
  //-->
  </script>
  <script language="javascript" src="../../../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/extrender.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/elementmore.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/ajax.js" type="text/javascript"></script>
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
				<div class="c">
					#PREFIX_DESC#发稿量统计列表
				</div>
			</div>
		</div>
		<div class="data">
			<table cellspacing="0" cellpadding="0" class="tb">
				<tbody>
					<tr>
						<th class="first" width="50" rowspan="2">序号</th><th rowspan="2" width="40%">#PREFIX_DESC#名称</th><th  colspan="2">总发稿量</th>
					</tr>
					<tr>
						<th>本期</th>
						<th>累计</th>
					</tr>
<%
		int colIndex=1;
		for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++){
			try{
				String sKey = (String) StatKeys.get(i-1);
				if(sKey == null) continue;
%>
				<tr siteId="<%=sKey%>">
					<td class="first"><%=colIndex++%></td>
					<td><%=sKey%></td>
					<td><%=result.getResult(1, sKey)%></td>
					<td><%=result.getResult(2, sKey)%></td>
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
  <script src="#PREFIX#_stat_common.js"></script>
  <script language="javascript">
  <!--
	drawNavigator({
		PageIndex :<%=nCurrPage%>,
		PageSize : <%=nPageSize%>,
		PageCount : Math.ceil(<%=(float)nNum/nPageSize%>),
		Num : <%=nNum%>
	});

	Event.observe(window,'load',function(){
		Stat.SearchBar.UI.init(Ext.apply(SEARCH_CONF,{
			export_cmd:function(){
				var oSearch = window.location.search;
				var url = "#PREFIX#_stat_export.jsp"+oSearch;
				new Ajax.Request(url,{
					contentType : 'application/x-www-form-urlencoded',
					method : 'post',
					parameters : null,
					onSuccess : function(_transport){
						var sResult = _transport.responseText;
						if(sResult && sResult.indexOf("<excelfile>") != -1){
							var ix = sResult.indexOf("<excelfile>") + 11;
							var ixx = sResult.indexOf("</excelfile>");
							sResult = sResult.substring(ix,ixx);		
							var frm = $("ifrmDownload");
							var sUrl = "../../../file/read_file.jsp?FileName="+sResult; 	
							frm.src = sUrl	
						}else{
							CTRSAction_alert("导出统计结果到Excel失败！");
						}
					}
				});
			}
		}));
	});
  //-->
  </script>
 </body>
</html>