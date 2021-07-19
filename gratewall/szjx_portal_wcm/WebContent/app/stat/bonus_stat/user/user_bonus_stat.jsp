<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>

<%@ page import="java.util.List" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.stat.BonusRule" %>
<%@ page import="com.trs.components.stat.StatBonusDataForUser" %>
<%@ page import="com.trs.components.stat.StatBonusResultForUser" %>

<%@include file="../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%
	// TODO权限校验

	// 构造用户或组织名称的查询条件
	String sSelectItem = currRequestHelper.getString("SelectItem");
	String sSearchValue = currRequestHelper.getString("SearchValue");

	String sQuerySQL = "";
	if(!CMyString.isEmpty(sSearchValue)){
		if(sSelectItem.equalsIgnoreCase("userName")){
			sQuerySQL = " and CrUser like '%" + CMyString.filterForSQL(sSearchValue) + "%'";
		}else{
			// 获取组织下的发稿人
			String sUserNames = getUserNames(sSearchValue, loginUser);
			// 如果符合检索条件的用户为空，则附件一个逻辑值为真的条件
			if(CMyString.isEmpty(sUserNames)){
				sQuerySQL = " and 1=0";
			}else{
				sQuerySQL = " and CrUser in (" 
					+ getUserNames(sSearchValue, loginUser)
					+")";
			}
		}
	}

	// 获取当前页面用户的统计数据
	// 构造统计的SQL语句
	String[] pStatSQL = new String[] {
	// 总的发稿量
	"Select count(*) DataCount, CrUser from WCMChnlDoc "
			// 统计指定时间段的数据
			+ " Where CrTime>=${StartTime}" + " And CrTime<=${EndTime}"
			+ " and DocStatus=10 and ChnlId>0" + sQuerySQL + " Group by CrUser",
	"Select sum(HITSCOUNT) TotalCount,DocId from XWCMDOCUMENTHITSCOUNT "
			// 统计指定时间段的数据
			+ " Where HitsTime>=${StartTime}"
			+ " And HitsTime<=${EndTime}" + sQuerySQL + " Group by DocId"};

	StatBonusDataForUser state = new StatBonusDataForUser(pStatSQL);
	
	CMyDateTime dtQueryStart = new CMyDateTime(),dtQueryEnd=new CMyDateTime();
	//设置时间范围
	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];
	
	dtQueryStart.setDateTimeWithString(sStartTime);
	dtQueryEnd.setDateTimeWithString(sEndTime);

	// 获取统计数据
	StatBonusResultForUser statResult = state.stat(dtQueryStart, dtQueryEnd);
	List arUserNames = null;
	if (statResult != null) {
		// 按照发稿量奖金的降序排列
		arUserNames = statResult.sort(BonusRule.ISSUEDAMOUNT_BONUS, true);
	}
	// 构造分页参数
	int nPageSize = currRequestHelper.getInt("PageSize",20);
	int nPageIndex = currRequestHelper.getInt("CurrPage",1);
	
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	int nNum = 0;
	if(arUserNames != null){
		nNum = arUserNames.size();
		currPager.setItemCount(nNum);
	}
	//7.结束
	out.clear();
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="user_bonus_stat.title"> 用户奖励统计表 </title>
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
  <script language="javascript" src="../../js/tab/tab.js" type="text/javascript"></script>

  <script language="javascript" src="export_stat_toexcel.js" type="text/javascript"></script>
  <!--引入分页JS-->
  <script language="javascript" src="../../js/page_nav/page.js" type="text/javascript"></script>
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
				<div class="c" WCMAnt:param="user_bonus_stat.user">
					用户绩效奖励统计列表
				</div>
			</div>
		</div>
		<div class="data">
			<table cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<th class="first" WCMAnt:param="user_bonus_stat.num">序号</th><th WCMAnt:param="user_bonus_stat.user.name">发稿人</th><th WCMAnt:param="user_bonus_stat.department">所属部门</th><th WCMAnt:param="user_bonus_stat.sent">已发文档所获奖金(￥)</th><th WCMAnt:param="user_bonus_stat.get">文档点击量所获奖金(￥)</th><th WCMAnt:param="user_bonus_stat.total">合计(￥)</th>
					</tr>
 <%
	if (statResult == null) {%>
		<tbody id="grid_NoObjectFound">
			<tr>
				<td colspan="6" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
			</tr>
		</tbody>
	<%
	}else{
		float fIssuedAmountBonus = 0f,fHitsBonus = 0f,fTotal = 0f;
		for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
			String sName = (String) arUserNames.get(i-1);
			User currUser = User.findByName(sName);
			int ncurrUserId = currUser.getId();

			// 获取用户所属组织
			Groups groups = currUser.getGroups();
			String sGroupName = LocaleServer.getString("user_bonus_stat.null","无");
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
			// 获取奖金数据
			fIssuedAmountBonus = statResult.getBonus(sName, BonusRule.ISSUEDAMOUNT_BONUS);
			fHitsBonus = statResult.getBonus(sName, BonusRule.HITS_BONUS);
			fTotal = fIssuedAmountBonus + fHitsBonus;
%>
				<tr userid="<%=ncurrUserId%>">
					<td class="first"><%=i%></td>
					<td><%=CMyString.transDisplay(sName)%></td>
					<td><%=sGroupName%></td>
					<td><a href="issuedamount_bonus_detail.jsp?UserName=<%=CMyString.filterForUrl(sName)%>&StartTime=<%=dtQueryStart%>&EndTime=<%=dtQueryEnd%>"><%=fIssuedAmountBonus%></a></td>
					<td><a href="hits_bonus_detail.jsp?UserName=<%=CMyString.filterForUrl(sName)%>&StartTime=<%=dtQueryStart%>&EndTime=<%=dtQueryEnd%>"><%=fHitsBonus%></a></td>
					<td><%=fTotal%></td>
				</tr>
<%
		}
	}

 %>

				</tbody>
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
		Stat.SearchBar.UI.init({
			select_item:[{
				text:"发稿人",
				value:"username"
			},{
				text:"所属部门",
				value:"gname"
			}],
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
				var sRequestUrl = 'export_bonus_stat.jsp';
				exportExcel(sRequestUrl);
			}
		});
		wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
		wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
		
	}
	Ext.apply(TABS_CONFIG.USER_DEFAULT, {
		items : [{
			key:'table',
			url:'user_bonus_stat.jsp',
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

  <%!
	public String getUserNames(String _sSearchValue, User _loginUser) throws Exception{
		// 获取所有组织
		String sWhere = "GName like '%"+ CMyString.filterForSQL(_sSearchValue)+"%'";
		WCMFilter filter = new WCMFilter("", sWhere, "");
		Groups groups = Groups.openWCMObjs(_loginUser, filter);

		// 获取这些组织中的所有用户
		Users users = Users.createNewInstance(_loginUser);
		if(groups == null){
			return "";
		}

		for(int i= 0; i< groups.size(); i++){
			Group currGroup = (Group) groups.getAt(i);
			if(currGroup == null)
				continue;
			users.addElements(currGroup.getUsers(_loginUser));
		}
		
		// 得到发稿人
		String sUserNames = "";
		for(int i= 0; i < users.size(); i++){
			User currUser = (User)users.getAt(i);
			if(currUser == null)
				continue;
			if(CMyString.isEmpty(sUserNames)){
				sUserNames = "'"+currUser.getName()+"'";
			}else{
				sUserNames += ",'"+currUser.getName()+"'";
			}
		}
		return sUserNames;
	}
  %>