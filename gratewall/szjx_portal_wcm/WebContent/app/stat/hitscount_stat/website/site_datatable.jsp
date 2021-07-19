<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@include file="website_hitscount_data_include.jsp"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title WCMAnt:param="site_datatable.title">站点点击量</title>
  <script language="javascript" src="../../../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/ajax.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/extrender.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/elementmore.js" type="text/javascript"></script>
  <script src="../../../js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../js/easyversion/calendar_lang/$locale$.js"></script>
  <script language="javascript" src="../../../js/easyversion/calendar3.js" type="text/javascript"></script>

  <script language="javascript" src="../../js/common.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/search_bar/search_bar.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/tab/tab.js" type="text/javascript"></script>
  <!--引入分页JS-->
  <script language="javascript" src="../../js/page_nav/page.js" type="text/javascript"></script>
  <script language="javascript" src="../export_stat_toexcel.js" type="text/javascript"></script>
  <script language="javascript" src="site_datatable.js" type="text/javascript"></script>
  <link href="../../js/page_nav/page.css" rel="stylesheet" type="text/css" />
  <link href="../../js/tab/tab.css" rel="stylesheet" type="text/css" />
  <link href="../../js/search_bar/search_bar.css" rel="stylesheet" type="text/css" />
  <link href="../../css/calendar.css" rel="stylesheet" type="text/css" />
  <link href="../table-list.css" rel="stylesheet" type="text/css" />
 </head>

 <body>
  <div class="container">
	<div class="search_bar" id="search_bar">
	</div>
	<div class="content">
		<div class="data-title">
			<div class="r">
				<div class="c" WCMAnt:param="site_datatable.siteclickcountlist">
					站点点击量统计列表
				</div>
			</div>
		</div>
		<div class="data" id="leftdata">
			<table cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<th class="first" width="50px" WCMAnt:param="site_datatable.num">序号</th>
						<th WCMAnt:param="site_datatable.site">站点名称</th>
						<th width="200px" WCMAnt:param="site_datatable.hitAmount">点击量</th>
					<%
						if(nHitsTimeItem > 0){
					%>
						<th width="200px" WCMAnt:param="site_datatable.increaseRate">增长指数</th>
					<%
						}	
					%>
					</tr>
<%
		for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
			if(arHostIds.size() < i) 
				break;
				
			int nSiteId = Integer.parseInt((String) arHostIds.get(i-1));
			WebSite site = WebSite.findById(nSiteId);
			if(site == null)
				continue;
			String sSiteName = CMyString.transDisplay(site.getDesc());
			int nResult = result.getResult(1, String.valueOf(nSiteId));
			int nCompare = 0;
			String sClassName = "";
			String sCompareResult = "";
			if(nHitsTimeItem > 0){
				int nResultForCompare = resultForCompare.getResult(1, String.valueOf(nSiteId));
				if(nResultForCompare == 0)
					sCompareResult = nResult + "";
				else{
					float fComparedCount = (float)nResultForCompare;
					nCompare = (int)(((nResult - fComparedCount) / fComparedCount) * 100);
					if(nCompare > 0){
						sClassName = "redClass";
						sCompareResult = "+" + nCompare + "%";
					}else{
						sClassName = "greenClass";
						sCompareResult = nCompare + "%";
					}
				}

			}
%>
				<tr>
					<td class="first"><%=i%></td><td class="titletd"><%=sSiteName%></td><td><%=nResult%></td>
					<%
						if(nHitsTimeItem > 0){
					%>
					<td><span class="<%=sClassName%>"><%=sCompareResult%></span> </td>
					<%
						}
					%>
				</tr>
<%
 }	
%>
				</tbody>

<%
		if(nNum == 0){
%>
	<tbody id="grid_NoObjectFound">
		<tr>
		<%if(nHitsTimeItem > 0){%>
		<td colspan="4" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
		<%
			}else{
		%>
			<td colspan="3" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
		<%
				}	
		%>
		</tr>
	</tbody>
<%
	}
%>
			</table>
		</div>
		<div class="rightdata" id="rightdata">
			<div>
				<div class="data-desc" WCMAnt:param="site_datatable.info">统计方式：点击时间段
				</div>
				<div class="" id="data-options">
					<LI><input type="radio" name="HitsTimeItem" value="0" id="HitsTimeItem_0" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_0" WCMAnt:param="site_datatable.nolimit">不限</LABEL></LI>
					<LI><input type="radio" name="HitsTimeItem" value="1" id="HitsTimeItem_1" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_1" WCMAnt:param="site_datatable.yesterday">昨天</LABEL></LI>
					<LI><input type="radio" name="HitsTimeItem" value="2" id="HitsTimeItem_2" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_2" WCMAnt:param="site_datatable.thisMonth">本月</LABEL></LI>
					<LI><input type="radio" name="HitsTimeItem" value="3" id="HitsTimeItem_3" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_3" WCMAnt:param="site_datatable.thisSeason">本季</LABEL></LI>
					<LI><input type="radio" name="HitsTimeItem" value="4" id="HitsTimeItem_4" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_4" WCMAnt:param="site_datatable.thisYear">本年</LABEL></LI>
				</div>
			</div>
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
		PageIndex :<%=nPageIndex%>,
		PageSize : <%=nPageSize%>,
		PageCount : Math.ceil(<%=(float)nNum/nPageSize%>),
		Num : <%=nNum%>
	});
  //-->
  </script>
 </body>
</html>