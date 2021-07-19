<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@include file="user_hitscount_data_include.jsp"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="user_datatable.individualclicknum"> 个人点击量 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
  <script language="javascript" src="user_datatable.js" type="text/javascript"></script>
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
	<%if(nGroupId > 0){%>
	<div class="return_btn" id="return_btn" url="" style="cursor:pointer;" WCMAnt:param="user_datatable.return">返回</div>
	<%
		}
	%>
	<div class="content">
		<div class="data-title">
			<div class="r">
				<div class="c">
					
					<%if(nGroupId > 0){%><span WCMAnt:param="user_datatable.department">部门</span>【<span class="detail-hostname"><%=sCurrGroupName%></span>】<span WCMAnt:param="user_datatable.hitscount">点击量统计列表</span>
					<%}else{%>
						<span WCMAnt:param="user_datatable.personal.hitscount">个人点击量统计列表</span>
					<%}%>
				</div>
			</div>
		</div>
		<div class="data" id="leftdata">
		 <div>
			<table cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<th class="first" width="100px" WCMAnt:param="user_datatable.num">序号</th><th WCMAnt:param="user_datatable.user">发稿人</th>
						 <th WCMAnt:param="user_datatable.departmentName">部门名称</th><th WCMAnt:param="user_datatable.total.hitmount">总点击量</th>
					</tr>
<%
		for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
			if(arUserNames.size() < i) 
				break;
				
			String sUserName = (String) arUserNames.get(i-1);
			User oUser = User.findByName(sUserName);
			if("".equals(sUserName))
				continue;
			 int nCount = result.getResult(1, sUserName);
			 if(nCount == 0)
				 continue;
			 String sGroupName = LocaleServer.getString("user_datatable.total.null","无");
			 Groups groups = oUser.getGroups();
			 Group temp = null;
			 if(groups.size() > 0){
				sGroupName = "";
				for(int j=0,nsize= groups.size(); j< nsize; j++){
					temp = (Group) groups.getAt(j);
					if(temp == null) continue;
					if(j != nsize-1){
						sGroupName += temp.getName() + ",";
					}else{
						sGroupName += temp.getName();
					}
				}
			 }
%>
				<tr userId="<%=oUser.getId()%>">
					<td class="first"><%=i%></td><td class="titletd"><%=CMyString.transDisplay(sUserName)%></td><td><%=sGroupName%></td><td><a href="#"><%=nCount%></a></td>
				</tr>
<%
 }	
%>
				</tbody>
<%if(nNum == 0){%>
	<tbody id="grid_NoObjectFound">
		<tr>
			<td colspan="4" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
		</tr>
	</tbody>
<%}%>
			</table>
			</div>
		</div>
		<div class="rightdata" id="rightdata">
			<div>
				<div class="data-desc" WCMAnt:param="user_datatable.method">统计方式：点击时间段
				</div>
				<div class="" id="data-options">
					<LI><input type="radio" name="HitsTimeItem" value="0" id="HitsTimeItem_0" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_0" WCMAnt:param="user_datatable.nolimit">不限</LABEL></LI>
					<LI><input type="radio" name="HitsTimeItem" value="1" id="HitsTimeItem_1" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_1" WCMAnt:param="user_datatable.yesterday">昨天</LABEL></LI>
					<LI><input type="radio" name="HitsTimeItem" value="2" id="HitsTimeItem_2" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_2" WCMAnt:param="user_datatable.thisMonth">本月</LABEL></LI>
					<LI><input type="radio" name="HitsTimeItem" value="3" id="HitsTimeItem_3" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_3" WCMAnt:param="user_datatable.thisSeason">本季</LABEL></LI>
					<LI><input type="radio" name="HitsTimeItem" value="4" id="HitsTimeItem_4" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_4" WCMAnt:param="user_datatable.thisYear">本年</LABEL></LI>
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