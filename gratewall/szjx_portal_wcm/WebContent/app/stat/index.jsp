<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@include file="../include/public_server.jsp"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="index.jsp.title"> 绩效考核系统 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <link href="index.css" rel="stylesheet" type="text/css" />
  <script language="javascript" src="../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script language="javascript" src="../js/easyversion/extrender.js" type="text/javascript"></script>
  <script language="javascript" src="../js/easyversion/elementmore.js" type="text/javascript"></script>
  <script language="javascript" src="../js/easyversion/calendar3.js" type="text/javascript"></script>

  <script language="javascript" src="js/common.js" type="text/javascript"></script>
  <script language="javascript" src="index.js" type="text/javascript"></script>
  <style type="text/css">
	.homepage{
		font-size:12px;
		color:blue;
		position:absolute;
		top:40px;
		left:280px;
		cursor:pointer;
	}
	.homepage:hover{
		color:red;
	}
  </style>
 </head>

 <body>
  <div class="container">
	<div class="head">
		<div class="logo" >
		</div>
		<span class="homepage" onclick="window.location.reload(true)"  WCMAnt:param="index.jsp.index">首页</span>
		<div class="logo_r_bg">
		</div>
		<div class="user_info">
			<span id="username"><%=loginUser.getName()%></span  WCMAnt:param="index.jsp.greeting">您好！今天是：<span id="datetime"></span> |<span class="exit" onclick="window.close()"   WCMAnt:param="index.jsp.close">关闭</span>
		</div>
	</div>
	<div class="main">
		<div class="left" id="left_nav">
			<div class="stat_items" id="doc_stat">
				<div class="r">
					<div class="c"  WCMAnt:param="index.jsp.fgl.statistic">
						发稿量统计
					</div>
				</div>
			</div>
			<ul class="doc_stat_items" id="doc_stat_items">
				<li url="doc_stat/user/user_datatable.jsp?TimeItem=1"><div  WCMAnt:param="index.jsp.personal.fgl">个人发稿量统计</div></li>
				<li url="doc_stat/group/group_datatable.jsp?TimeItem=1"><div  WCMAnt:param="index.jsp.department.fgl">部门发稿量统计</div></li>
				<li url="doc_stat/channel/chnl_datatable.jsp?TimeItem=1"><div  WCMAnt:param="index.jsp.chnl.fgl">栏目发稿量统计</div></li>
				<li url="doc_stat/site/site_datatable.jsp?TimeItem=1"><div  WCMAnt:param="index.jsp.site.fgl">站点发稿量统计</div></li>
			</ul>
			<div class="stat_items" id="click_stat">
				<div class="r">
					<div class="c"  WCMAnt:param="index.jsp.click.amount"">
						点击量统计
					</div>
				</div>
			</div>
			<ul class="click_stat_items display-none" id="click_stat_items">
				<li url="hitscount_stat/document/document_hitscount_table.jsp?TimeItem=2&HitsTimeItem=2"><div  WCMAnt:param="index.jsp.doc.clicked.amount">稿件点击量统计</div></li>
				<li url="hitscount_stat/group/user_datatable.jsp?TimeItem=2&HitsTimeItem=2"><div  WCMAnt:param="index.jsp.personal.clicked.amount">个人点击量统计</div></li>
				<li url="hitscount_stat/group/group_datatable.jsp?TimeItem=2&HitsTimeItem=2"  WCMAnt:param="index.jsp.department.clicked.amount"><div>部门点击量统计</div></li>
				<li url="hitscount_stat/special/special_datatable.jsp?TimeItem=2&HitsTimeItem=2"><div  WCMAnt:param="index.jsp.subject.clicked.amount">专题点击量统计</div></li>
				<li url="hitscount_stat/channel/channel_datatable.jsp?TimeItem=2&HitsTimeItem=2"><div WCMAnt:param="index.jsp.chnl.clicked.amount">栏目点击量统计</div></li>
				<li url="hitscount_stat/website/site_datatable.jsp?TimeItem=2&HitsTimeItem=2"><div WCMAnt:param="index.jsp.site.clicked.amount">站点点击量统计</div></li>
			</ul>
			<div class="stat_items" id="login_stat">
				<div class="r">
					<div class="c" WCMAnt:param="index.jsp.logging.info">
						登录信息统计
					</div>
				</div>
			</div>
			<ul class="login_stat_items display-none" id="login_stat_items">
					<li url="login_stat/userlogininfo_stat.jsp?TimeItem=1"><div WCMAnt:param="index.jsp.user.logging.info">用户登录信息统计</div></li>
			</ul>
			<div class="stat_items" id="award_stat">
				<div class="r">
					<div class="c" WCMAnt:param="index.jsp.award.count">
						奖励统计
					</div>
				</div>
			</div>
			<ul class="award_stat_items display-none" id="award_stat_items">
				<li url="bonus_stat/user/user_bonus_stat.jsp"><div WCMAnt:param="index.jsp.award.count">奖励统计</div></li>
			</ul>
			<%
				if(loginUser.isAdministrator()){
			%>
			<div class="stat_items" id="system_config">
				<div class="r">
					<div class="c" WCMAnt:param="index.jsp.sys.info">
						系统配置
					</div>
				</div>
			</div>
			<ul class="system_config_items display-none" id="system_config_items">
				<li url="config/config_list.jsp"><div WCMAnt:param="index.jsp.sys.info">系统配置</div></li>
			</ul>
			<% }%>
		</div>
		<div class="right">
			<iframe id="right_iframe" src="main.jsp" style="width:100%;height:100%;" frameborder=0></iframe>
		</div>
	</div>
	<div class="foot">
	</div>
  </div>
 </body>
</html>