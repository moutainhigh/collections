<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.components.stat.BonusRule" %>
<%@ page import="com.trs.components.stat.BonusRules" %>
<%@ page import="com.trs.components.stat.StatBonusDataForUser" %>
<%@ page import="com.trs.components.stat.StatBonusResultForUser" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>

<%@include file="../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%
	// TODO权限校验

	// 获得当前页面的参数
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
  <title WCMAnt:param="hits_bonus_detail.title"> 点击量文档列表 </title>
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
	/*自己写样式，让统计时间段不显示*/
	.select_time_desc,.select_time{
		display:none;
	}
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
  </style>
 </head>

<body>
<div class="container">
	<div class="search_bar" id="search_bar">
		
	</div>
	<div class="return_btn" id="return_btn" url="user_bonus_stat.jsp" style="cursor:pointer;" WCMAnt:param="hits_bonus_detail.back">返回</div>
	<div class="content">
		
		<div class="data-title">
			<div class="r">
				<div class="c" WCMAnt:param="hits_bonus_detail.info">
					用户【<span class="detail-hostname"><%=CMyString.transDisplay(sUserName)%></span>】点击量奖励详细情况列表
				</div>
			</div>
		</div>
		<div class="data">
			<table cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<th class="first" WCMAnt:param="hits_bonus_detail.num">序号</th><th width="150px" WCMAnt:param="hits_bonus_detail.month">月份</th><th WCMAnt:param="hits_bonus_detail.doctitle">文档标题</th><th width="100px" WCMAnt:param="hits_bonus_detail.channel">所属栏目</th><th width="100px" WCMAnt:param="hits_bonus_detail.clicknum">点击量</th><th width="100px" WCMAnt:param="hits_bonus_detail.bonus">所获奖金(￥)</th>
					</tr>
 <%
 try{
	// 获取文档标题的查询条件
	String sSelectItem = currRequestHelper.getString("SelectItem");
	String sSearchValue = currRequestHelper.getString("SearchValue");
	String sQuerySQL = "";
	if(!CMyString.isEmpty(sSearchValue)){
		sQuerySQL = " and DocTitle like '%" + CMyString.filterForSQL(sSearchValue)+ "%'";
	}
	// 构造统计的SQL语句
	String[] pStatSQL = new String[] {
	// 总的点击量
	"Select sum(HITSCOUNT) TotalCount,DocId from XWCMDOCUMENTHITSCOUNT "
			// 统计指定时间段的数据
			+ " Where HitsTime>=${StartTime}"
			+ " And HitsTime<=${EndTime}" + sQuerySQL + " Group by DocId" };

	StatBonusDataForUser state = new StatBonusDataForUser(pStatSQL);

	// 获取统计数据
    StatBonusResultForUser statResult = state.statHitsBonusByMonth(startTime, endTime, true);

	if (statResult == null) {
	%>
		<tbody id="grid_NoObjectFound">
			<tr>
				<td colspan="6" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
			</tr>
		</tbody>
		
	<%
	}else{
		// 获取文档ID的List
		List docIds = statResult.getDocIds();
		if(docIds == null){
	%>
		<tbody id="grid_NoObjectFound">
			<tr>
				<td colspan="6" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
			</tr>
		</tbody>
	<%
		}else{
			int nCurrAmount = 0;
			int nDocId = 0;
			int nRowNo = 0;
			startTime.dateAdd(CMyDateTime.MONTH, -1);
			while (startTime.compareTo(endTime) < 0) {
				startTime.dateAdd(CMyDateTime.MONTH, 1);
				// 获取每月的点击量奖金统计数据
				%>
				<%// 根据文档Id获取该月该文档的点击量及所获奖金
				int nHitsCount=0;
				for (int i = 0; i < docIds.size(); i++) {
					int nCurrDocId = Integer.parseInt((String) docIds.get(i));
					
					// 获取文档
					Document document = Document.findById(nCurrDocId);
					if(document == null)
						continue;
					String sDocTitle = document.getTitle();
					Channel channel = document.getChannel();
					ChnlDoc chnlDoc = ChnlDoc.findByDocument(document);
					// 获取文档点击量
					nHitsCount = statResult.getHitsCountOfMonth(sUserName, nCurrDocId, startTime);
					if(nHitsCount == 0)
						continue;
					nRowNo++;


			%>	
				<tr>
					<td class="first"><%=nRowNo%></td>
					<td width="150px"><%=startTime.toString("yyyy年MM月")%></td>
					<td style="padding:6px 0 6px 2px;text-align:left;"><a href="../../../document/document_show.jsp?DocumentId=<%=nCurrDocId%>&ChannelId=<%=channel.getId()%>&ChnlDocId=<%=chnlDoc.getId()%>&ReadOnly=1" target="_blank" title="<%=CMyString.filterForHTMLValue(sDocTitle)%>"><%=sDocTitle%></a></td>
					<td width="100px"><%=CMyString.transDisplay(channel.getName())%></td>
					<td width="100px"><%=nHitsCount%></td>
					<td width="100px"><%=statResult.getHitsBonusOfMonth(sUserName, nCurrDocId, startTime)%></td>
				</tr>
				
			<%}
			}
		}
	}
 }catch(Exception e){
	e.printStackTrace();
 }

 %>

				</tbody>
			</table>
<%
	// 获取奖金计算规则
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm61_bonusrule", sMethodName = "query";
	HashMap parameters = new HashMap();
	parameters.put("BONUSTYPE", Integer.toString(BonusRule.HITS_BONUS));
	BonusRules bonusRules = (BonusRules) processor.excute(sServiceId,
			sMethodName, parameters);
%>
		<div class="bonusrule">
			<p class="rule_title" WCMAnt:param="hits_bonus_detail.bonus.rule">注：以上奖金均按照下面的规则计算[范围：(单篇奖金)]
			<%if(loginUser.isAdministrator()){%>
				<span style="display:inline-block; width:20px;"></span><a href="../../../../bonusrule/bonusrule_list.jsp" target="_blank" WCMAnt:param="hits_bonus_detail.in">进入奖金维护页面</a>
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
		Ext.apply(SEARCH_CONFIG.USER_DEFAULT,{
			select_item:[{
				text:"文档标题",
				value:"docTitle"
			}],
			ext_url_param : "UserName="+"<%=CMyString.filterForUrl(sUserName)%>"+"&StartTime="+"<%=sStartTime%>"+"&EndTime="+"<%=sEndTime%>" ,
			export_cmd:function(){
				var sRequestUrl = "export_hits_bonus_stat.jsp";
				exportExcel(sRequestUrl);
			}
		});
		Stat.SearchBar.UI.init(SEARCH_CONFIG.USER_DEFAULT);
	}
	
//-->
</script>