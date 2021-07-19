<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@include file="channel_hitscount_data_include.jsp"%>
<%
	String sGroupStatByChannel = ConfigServer.getServer().getSysConfigValue("GROUP_STAT_BY_CHANNEL", "false");
	boolean bGroupStatByChannel = "true".equalsIgnoreCase(sGroupStatByChannel);
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title WCMAnt:param="channel_datatable.title"> 栏目点击量 </title>
  <script language="javascript">
  <!--
	var bStatGroupByChannel = <%=bGroupStatByChannel%>
  //-->
  </script>
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
  <script language="javascript" src="channel_datatable.js" type="text/javascript"></script>
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
				<div class="c" WCMAnt:param="channel_datatable.chart">
					栏目点击量统计列表
				</div>
			</div>
		</div>
		<div class="data" id="leftdata">
			<table cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<th class="first" width="50px" WCMAnt:param="channel_datatable.num">序号</th>
						<th WCMAnt:param="channel_datatable.chnlname">栏目名称</th>
						<th width="120px" WCMAnt:param="channel_datatable.clickMount">点击量</th>
					<%
						if(nHitsTimeItem > 0){
					%>
						<th width="120px" WCMAnt:param="channel_datatable.increaseRate">增长指数</th>
					<%
						}
					%>
					<%
						if(bGroupStatByChannel){
					%>
						<th width="200px" WCMAnt:param="channel_datatable.departmentName">部门名称</th>
					<%
						}	
					%>
						<th width="200px" WCMAnt:param="channel_datatable.site">所属站点</th>
					</tr>
<%
		for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
			if(arHostIds.size() < i) 
				break;
				
			int nChannelId = Integer.parseInt((String) arHostIds.get(i-1));
			Channel channel = Channel.findById(nChannelId);
			if(channel == null)
				continue;
			String sChannelName = CMyString.transDisplay(channel.getDesc());
			String sSiteName = "";
			WebSite oSite = channel.getSite();
			if(oSite != null)
				sSiteName = oSite.getDesc();
			int nResult = result.getResult(1, String.valueOf(nChannelId));
			int nCompare = 0;
			String sClassName = "";
			String sCompareResult = "";
			if(nHitsTimeItem > 0){
				int nResultForCompare = resultForCompare.getResult(1, String.valueOf(nChannelId));
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

			IChnlDeptMgr mgr = (IChnlDeptMgr) DreamFactory.createObjectById("IChnlDeptMgr");
			String sGroupName = CMyString.transDisplay(CMyString.showNull(mgr.getDepNamesByChannel(channel, channel.getCrUser())));
%>
				<tr>
					<td class="first"><%=i%></td><td class="titletd"><%=sChannelName%></td><td><%=nResult%></td>
					<%
						if(nHitsTimeItem > 0){
					%>
					<td><span class="<%=sClassName%>"><%=sCompareResult%></span> </td>
					<%
						}
					%>
					<%
						if(bGroupStatByChannel){
					%>
					<td class="othertd" title="<%=sGroupName%>"><%=sGroupName%></td>
					<%
						}	
					%>
					<td class="othertd" title="<%=sSiteName%>"><%=sSiteName%></td>
				</tr>
<%
 }	
%>
				</tbody>
<%
		if(nNum == 0){
			int nColSpan = 5;
			if(nHitsTimeItem>0){
				if(bGroupStatByChannel){
					nColSpan = 6;
				}else{
					nColSpan = 5;
				}
			}else{
				if(bGroupStatByChannel){
					nColSpan = 5;
				}else{
					nColSpan = 4;
				}
			}
%>
	<tbody id="grid_NoObjectFound">
		<tr>
			<td colspan="<%=nColSpan%>" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
		</tr>
	</tbody>
<%
	}
%>
			</table>
		</div>
		<div class="rightdata" id="rightdata">
			<div>
				<div class="data-desc" WCMAnt:param="channel_datatable.statisticMethod">统计方式：点击时间段
				</div>
				<div class="" id="data-options">
					<LI><input type="radio" name="HitsTimeItem" value="0" id="HitsTimeItem_0" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_0" WCMAnt:param="channel_datatable.noLimit">不限</LABEL></LI>
					<LI><input type="radio" name="HitsTimeItem" value="1" id="HitsTimeItem_1" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_1" WCMAnt:param="channel_datatable.yesterday">昨天</LABEL></LI>
					<LI><input type="radio" name="HitsTimeItem" value="2" id="HitsTimeItem_2" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_2" WCMAnt:param="channel_datatable.month">本月</LABEL></LI>
					<LI><input type="radio" name="HitsTimeItem" value="3" id="HitsTimeItem_3" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_3" WCMAnt:param="channel_datatable.season">本季</LABEL></LI>
					<LI><input type="radio" name="HitsTimeItem" value="4" id="HitsTimeItem_4" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_4" WCMAnt:param="channel_datatable.year">本年</LABEL></LI>
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