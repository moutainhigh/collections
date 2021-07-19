<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>

<%@ page import="com.trs.infra.persistent.WCMFilter" %>

<%@ page import="com.trs.infra.util.CMyDateTime" %>

<%@ page import="com.trs.infra.util.CMyString"%>

<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.components.stat.UserLoginInfoServiceProvider"%>
<%@ page import="com.trs.components.stat.UserLoginInfo"%>
<%@ page import="com.trs.components.stat.UserLoginInfos"%>


<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../include/static_common.jsp"%>

<%

	//设置时间范围
	String[] sGetTimes= getDateTimeFromParame(currRequestHelper);

	// 1 获取统计数据
	JSPRequestProcessor processor = new JSPRequestProcessor(request,response);	
	UserLoginInfos results ;
	
	String sServiceId = "wcm61_userlogininfo", sMethodName = "query";

	//1.1 获取到指定参数检索的字段和值
	String sSelectItem = currRequestHelper.getString("SelectItem");
	String sSearchValue = currRequestHelper.getString("SearchValue");

	//1.2 如果sSelectItem不为空，则传值sSelectItem和sSearchValue。否则不传
	if(!CMyString.isEmpty(sSearchValue)) {
		processor.setAppendParameters(new String[] {sSelectItem,sSearchValue , "StartTime", sGetTimes[0], "EndTime", sGetTimes[1]});
	}else{
		processor.setAppendParameters(new String[] { "StartTime", sGetTimes[0], "EndTime", sGetTimes[1]});
	}
	results = (UserLoginInfos) processor.excute(sServiceId, sMethodName);

	// 构造分页参数
	int nPageSize = currRequestHelper.getInt("PageSize",15);
	int nPageIndex = currRequestHelper.getInt("CurrPage",1);
	
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	int nNum = 0;
	if(results != null){
		nNum = results.size();
		currPager.setItemCount(nNum);
	}
	//7.结束
	out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title WCMAnt:param="userlogininfo_stat.title"> 用户登录信息 </title>
		<script language="javascript" src="../../js/easyversion/lightbase.js" type="text/javascript"></script>
		<script language="javascript" src="../../js/easyversion/extrender.js" type="text/javascript"></script>
		<script language="javascript" src="../../js/easyversion/elementmore.js" type="text/javascript"></script>
		<script language="javascript" src="../../js/easyversion/ajax.js" type="text/javascript"></script>
		<script src="../../js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../../js/easyversion/calendar_lang/$locale$.js"></script>
		<script language="javascript" src="../../js/easyversion/calendar3.js" type="text/javascript"></script>

		<script language="javascript" src="../js/common.js" type="text/javascript"></script>
		<script language="javascript" src="../js/search_bar/search_bar.js" type="text/javascript"></script>
		<script language="javascript" src="../js/tab/tab.js" type="text/javascript"></script>

		<!--引入分页JS-->
		<script language="javascript" src="../js/page_nav/page.js" type="text/javascript"></script>
		<link href="../js/page_nav/page.css" rel="stylesheet" type="text/css" />
		<link href="../js/tab/tab.css" rel="stylesheet" type="text/css" />
		<link href="../js/search_bar/search_bar.css" rel="stylesheet" type="text/css" />
		<link href="../css/calendar.css" rel="stylesheet" type="text/css" />
		<link href="table-list.css" rel="stylesheet" type="text/css" />
	</head>

	<body>
	<div class="container">
		<div class="search_bar" id="search_bar">
		</div>
		<div class="content">
			<div class="data-title">
				<div class="r">
					<div class="c" WCMAnt:param="userlogininfo_stat.logging.statistic.chart">
						用户登录信息统计列表
					</div>
				</div>
			</div>
			<div class="data" id="leftdata">

				<table cellspacing="0" cellpadding="0">
					<tbody>
						<tr>
							<th class="first" WCMAnt:param="userlogininfo_stat.num">序号</th><th WCMAnt:param="userlogininfo_stat.userName">发稿人</th><th WCMAnt:param="userlogininfo_stat.dep.name">部门名称</th><th WCMAnt:param="userlogininfo_stat.logging.time">登录时间</th><th WCMAnt:param="userlogininfo_stat.ip.address">IP地址</th>
						</tr>
<%
	//获取每一条搜索结果，进行输出显示
	UserLoginInfo userLogininfo;
	//for(int i=0; i<results.size(); i++) {
	for(int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		userLogininfo = (UserLoginInfo) results.getAt(i-1);
		
		//获取指定Id的对象
		User user = User.findById(userLogininfo.getUserId());
		Groups groups = user.getGroups();
		String sGroupName = LocaleServer.getString("userlogininfo_stat.null","无");
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
%>
	<tr>
		<td class="first"><%= i%></td>
		<td><%= user.getName() %></td>
		<td><%= sGroupName %></td>
		<td><%= userLogininfo.getLoginTime() %></td>
		<td><%= userLogininfo.getIpAddress() %></td>
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

		<div class="foot">
			<div class="" id="tab-nav">
			</div>
		</div>
	</div>
	</body>
</html>

<script language="javascript">
<!--
window.onload = function(){
	var DATE_VALUE = {
		NO_LIMIT:"0",
		TODAY:"1",
		MONTH:"2",
		SEASON:"3",
		YEAR:"4",
		SPECIFY:"-1"
	}
	Ext.apply(SEARCH_CONFIG.USER_DEFAULT,{
		select_item:[{
			text:"发稿人",
			value:"userName"
		},{
			text:"部门名称",
			value:"groupName"
		},{
			text:"IP地址",
			value:"IpAddress"
		}],
		select_time:[{
			text:"今日",
			value:DATE_VALUE.TODAY
		},{
			text:"本月",
			value:DATE_VALUE.MONTH
		},{
			text:"本季",
			value:DATE_VALUE.SEASON
		},{
			text:"本年",
			value:DATE_VALUE.YEAR
		},{
			text:"指定",
			value:DATE_VALUE.SPECIFY
		}],
		export_cmd:function(){
			var sSearch = window.location.search;
			var sUrl = "export_userlogininfo_stat.jsp"+sSearch;

			new Ajax.Request(sUrl,{
				contentType : 'application/x-www-form-urlencoded',
				method : 'post',
				parameters : null,
				onSuccess : function(_transport){
					result = _transport.responseText;
					if(result && result.indexOf("<excelfile>") != -1){
						var ix = result.indexOf("<excelfile>") + 11;
						var ixx = result.indexOf("</excelfile>");
						result = result.substring(ix,ixx);
						var frm = document.getElementById("ifrmDownload");
						if(frm == null) {
							frm = document.createElement('iframe');
							frm.style.height = 0;
							frm.style.width = 0;
							document.body.appendChild(frm);
						}
						var sUrl = "../../file/read_file.jsp?FileName="+result;
						frm.src = sUrl;
					}else{
						$("导出Excel失败！");
					} 
				},
				onFailure : function(_transport){
					 $("导出Excel失败！");
				}
			});
		}
	});
	Stat.SearchBar.UI.init(SEARCH_CONFIG.USER_DEFAULT);
	wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	
}
Ext.apply(TABS_CONFIG.USER_DEFAULT, {
	items : [{
		key:'table',
		url:'userlogininfo_stat.jsp',
		desc:'数据表'
	}]
});
new Stat.Tab().init(TABS_CONFIG.USER_DEFAULT);

drawNavigator({
	PageIndex :<%=nPageIndex%>,
	PageSize : <%=nPageSize%>,
	PageCount : Math.ceil(<%=(float)nNum/nPageSize%>),
	Num : <%=nNum%>
});
//-->
</script>