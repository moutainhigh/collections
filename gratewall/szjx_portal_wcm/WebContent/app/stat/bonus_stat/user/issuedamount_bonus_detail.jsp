<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>

<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.stat.StatBonusDataForUser" %>
<%@ page import="com.trs.components.stat.StatBonusResultForUser" %>
<%@ page import="com.trs.components.stat.BonusRule" %>
<%@ page import="com.trs.components.stat.BonusRules" %>

<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>

<%@include file="../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%
	// TODO权限校验

	// 获得当前页面参数
	String sUserName = currRequestHelper.getString("UserName");
	CMyDateTime startTime = new CMyDateTime(),endTime=new CMyDateTime();
	//设置时间范围
	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];
	
	startTime.setDateTimeWithString(sStartTime);
	endTime.setDateTimeWithString(sEndTime);
	
	// 获取当前页面用户的统计数据
	
	//7.结束
	out.clear();
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="bonus_detail.title"> 点击量文档列表 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <script language="javascript" src="../../../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/ajax.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/extrender.js" type="text/javascript"></script>
  <script language="javascript" src="../../../js/easyversion/elementmore.js" type="text/javascript"></script>
  <script src="../../../js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../js/easyversion/calendar_lang/$locale$.js"></script>
  <script language="javascript" src="../../../js/easyversion/calendar3.js" type="text/javascript"></script>
	
  <script language="javascript" src="../../js/common.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/search_bar/search_bar.js" type="text/javascript"></script>

  <script language="javascript" src="export_stat_toexcel.js" type="text/javascript"></script>
  <link href="../../js/search_bar/search_bar.css" rel="stylesheet" type="text/css" />
  <link href="../../css/calendar.css" rel="stylesheet" type="text/css" />
  <link href="../table-list.css" rel="stylesheet" type="text/css" />
  <style type="text/css">
	.bonusrule{
		padding: 20px;
		text-align:center;
		color:#528B3C;
	}
	.rule_title{
		align:center;
		font-weight:bold;
		font-size:12px;
	}
	.select_item{
		display:none;
	}
	#search_value_{
		display:none;
	}
  </style>
 </head>

 <body>
  <div class="container">
	<div class="search_bar" id="search_bar">
	</div>
	<div class="return_btn" id="return_btn" url="user_bonus_stat.jsp" style="cursor:pointer;" WCMAnt:param="bonus_detail.back">返回</div>
	<div class="content">
		<div class="data-title">
			<div class="r">
				<div class="c" WCMAnt:param="bonus_detail.bonues.detail">
					用户【 <span class="detail-hostname"><%=CMyString.transDisplay(sUserName)%></span> 】发稿量奖励详细情况列表
				</div>
			</div>
		</div>
		<div class="data">
			<table cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<th class="first" WCMAnt:param="bonus_detail.month">月份</th><th WCMAnt:param="bonus_detail.sent">已发文档数量(篇)</th><th WCMAnt:param="bonus_detail.get">已发文档所获奖金(￥)</th>
					</tr>
 <%
 // 记录总发稿量
 int nIssuedAmount = 0;
 float fTotalBonus = 0f;
 try{
	// 构造统计的SQL语句
	String[] pStatSQL = new String[] {
	// 总的发稿量
	"Select count(*) DataCount, CrUser from WCMChnlDoc "
			// 统计指定时间段的数据
			+ " Where CrTime>=${StartTime}" + " And CrTime<=${EndTime}"
			+ " and DocStatus=10 and CHNLID>0" + " Group by CrUser"};

	StatBonusDataForUser state = new StatBonusDataForUser(pStatSQL);

	// 获取统计数据
    StatBonusResultForUser statResult = state.statIssuedAmountBonusByMonth(startTime, endTime, true);
	if (statResult == null) {	%>
		<tbody id="grid_NoObjectFound">
			<tr>
				<td colspan="3" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
			</tr>
		</tbody>
		
	<%
	}else{
		int nCurrAmount = 0;
		float fCurrBonus = 0f;
		startTime.dateAdd(CMyDateTime.MONTH, -1);
		while (startTime.compareTo(endTime) < 0) {
			startTime.dateAdd(CMyDateTime.MONTH, 1);
			nCurrAmount = statResult.getIssuedAmountOfMonth(sUserName, startTime);
			fCurrBonus = statResult.getIssuesAmountBonusOfMonth(sUserName, startTime);
			nIssuedAmount += nCurrAmount;
			fTotalBonus += fCurrBonus;
			if(nCurrAmount == 0)
				continue;
%>
				<tr>
					<td  class="first"><%=startTime.toString("yyyy年MM月")%></td>
					<td><%=nCurrAmount%></td>
					<td><%=fCurrBonus%></td>
				</tr>
<%
		}
	}
 }catch(Exception e){
	e.printStackTrace();
 }
	if (nIssuedAmount > 0) {
 %>

				<tr>
					<th class="first">合计</th><th><%=nIssuedAmount%></th><th><%=fTotalBonus%></th>
				</tr>
<%}%>
				</tbody>
			</table>
			<%
	// 获取奖金计算规则
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm61_bonusrule", sMethodName = "query";
	HashMap parameters = new HashMap();
	parameters.put("BONUSTYPE", Integer.toString(BonusRule.ISSUEDAMOUNT_BONUS));
	BonusRules bonusRules = (BonusRules) processor.excute(sServiceId,
			sMethodName, parameters);
%>
		<div class="bonusrule">
			<p class="rule_title" WCMAnt:param="bonus_detail.bonus.rule">
				注：以上奖金均按照下面的规则计算[范围：(单篇奖金)]
			<%if(loginUser.isAdministrator()){%>
				<span style="display:inline-block; width:20px;"></span><a href="../../../../bonusrule/bonusrule_list.jsp" target="_blank" WCMAnt:param="bonus_detail.in">进入奖金维护页面</a>
			<%}%>
			</p>
			
	<%
		// 获取奖金计算规则
		BonusRule bonusRule = null;
		int nBonusType = 0;
		String sRange = "";
		float fPricePerUnit = 0f;
		for(int i=1; i<= bonusRules.size(); i++){
			bonusRule = (BonusRule)bonusRules.getAt(i-1);
			if(bonusRule == null)
				continue;
			nBonusType = bonusRule.getBonusType();
			sRange = bonusRule.getRange();
			fPricePerUnit = bonusRule.getPricePerUnit();
	%><%=sRange%>：(<%=fPricePerUnit%>￥)&nbsp;&nbsp;&nbsp;
	<%}%>
		</div>
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
		
		Stat.SearchBar.UI.init({
			select_time:[{
				text:"不限",
				value:DATE_VALUE.NO_LIMIT
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
				var sRequestUrl = "export_issuedamount_bonus_stat.jsp";
				exportExcel(sRequestUrl);
			}
		});
		wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
		wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
		
	}
//-->
</script>