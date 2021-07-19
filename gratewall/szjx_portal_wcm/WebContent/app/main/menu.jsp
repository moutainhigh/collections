<%--@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" --%>
<%-- /* wenyh@2007-07-30 使用@include 指令的文件不需要再指定contentType */ --%>
<%@ page import="com.trs.service.IUserService" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<div class="wcmlogo">&nbsp;</div>
<div class="menuBar" id="menuBar"></div>
<DIV class="user_welcome"><div class="user_welcome_box">
	<span class="online_user" title="<%=LocaleServer.getString("main.label.loginuser", "当前登录用户")%>"></span>
	<span class="corner_span"><a href="#" onclick="PageContext.skipToUserInfo(); return false;" class="lnk_main_corner"><%=(loginUser.getNickName() == null) ? loginUser.getName() : CMyString.transDisplay(loginUser.getNickName())%></a></span>
	<span class="corner_delt">|</span>
	<span class="corner_span">
		<span id="spAlertionIcon" title="<%=LocaleServer.getString("main.label.receivemsg", "点击收取短消息")%>" class="alertion_deactive_icon" style="" onclick="PageContext.notifyAlertions()"></span><span id="spAlertionNum" class="AlertionNum" ></span>
	</span>	
	<span class="corner_delt">|</span>
	<span class="corner_span">
		<a href="#" onclick="PageContext.showOnliners(); return false;" class="lnk_main_corner"><%=LocaleServer.getString("main.label.online", "当前在线")%></a>
	</span>
	<span class="corner_delt">|</span>
	<span class="corner_span">
		<a href="./logout.jsp" class="lnk_main_corner"><%=LocaleServer.getString("main.label.logout", "注销")%></a>
	</span></div>
</DIV>

<%
	int nUserId			= loginUser.getId();
	String sUserName	= loginUser.getName();
	String sUserNick	= loginUser.getNickName();
	if(sUserNick == null) {
		sUserNick = sUserName;
	}
%>
<script language="javascript">
<!--
	var m_sUserId		= userId = <%=nUserId%>;
	var m_sUserName		= '<%=CMyString.filterForJs(sUserName)%>';
	var m_sUserNickName = '<%=CMyString.filterForJs(sUserNick)%>';
	var userObjType = 204;
//-->
</script>
<a href="" target="blank" style="display:none;" id="mailToA"></a>


<%
// 根据用户权限,控制菜单的显示
IUserService aUserService = (IUserService)DreamFactory.createObjectById("IUserService");
%>
<script language="javascript">

<!--
	var tabArray = new Array();
	tabArray.push('bSiteTabDisable', false);
	tabArray.push('bDocTabDisable', false);
	tabArray.push('bPubTabDisable', false);
	tabArray.push('bTeamTabDisable', false);
	tabArray.push('bPersonTabDisable', false);
	tabArray.push('bStatTabDisable',<%=!(loginUser.isAdministrator() || AuthServer.hasStatisticRight(loginUser))%>);
	tabArray.push('bUserTabDisable', <%=!(loginUser.isAdministrator()|| aUserService.isAdminOfGroup(loginUser)|| aUserService.isManagerOfRole(loginUser))%>);
	tabArray.push('bSysTabDisable4Source', <%=!AuthServer.hasMgrDocSourceRight(loginUser)%>);
	//系统配置和计划调度均为系统属性，因该权限没有设置的地方，所以只有系统管理员才能有两个属性的权限 WCMVS-39 by lbm 2013-03-08
	tabArray.push('bSysTabDisable', <%=!loginUser.isAdministrator()%>);
	tabArray.push('bExtTabDisable', <%=!AuthServer.hasExtendedRight(loginUser)%>);
	tabArray.push('bSearchConfigDisable',<%=!(loginUser.isAdministrator())%>);
	// 热词管理
	tabArray.push('bHotWordDisable', <%=!loginUser.isAdministrator()%>);
																	
	// ge gfc add@2008-4-10 20:45 对各个选件进行条件判断
	//评论
	tabArray.push('bExtTabDisable_Comment'	, <%=!AuthServer.hasExtendedRight(loginUser, WCMRightTypes.EXT_COMMENT)%>);
	//调查
	tabArray.push('bExtTabDisable_Poll'		, <%=!AuthServer.hasExtendedRight(loginUser, WCMRightTypes.EXT_INVESTMENT)%>);
	//广告
	tabArray.push('bExtTabDisable_Addintrs'	, <%=!AuthServer.hasExtendedRight(loginUser, WCMRightTypes.EXT_ADMANAGE)%>);
	//智能信息处理
	tabArray.push('bExtTabDisable_Aptitude'	, <%=!AuthServer.hasExtendedRight(loginUser, WCMRightTypes.EXT_APTITUDE)%>);
	//表单
	tabArray.push('bExtTabDisable_Infoview'	, <%=!AuthServer.hasExtendedRight(loginUser, WCMRightTypes.EXT_INFOVIEW_MGR)%>);
	//场景式服务
	tabArray.push('bExtTabDisable_scene'	, <%=!loginUser.isAdministrator()%>);
	//数据网关
	tabArray.push('bExtTabDisable_Infogate' , <%=!AuthServer.hasRightOnSelf(loginUser, 1, 2, WCMRightTypes.EXT_INFOGATE)%>);
	//邮件订阅
	tabArray.push('bExtTabDisable_Subscribe', <%=!AuthServer.hasRightOnSelf(loginUser, 1, 2, WCMRightTypes.EXT_SUBSCRIBE)%>);
	//嘉宾访谈
	tabArray.push('bExtTabDisable_Interview', <%=!AuthServer.hasRightOnSelf(loginUser, 1, 2, WCMRightTypes.EXT_INTERVIEW)%>);
	
	var globalTabDisabled = new Array();
	var index = 1;
	for(var i = 0; i < tabArray.length; i+=2){
		globalTabDisabled[tabArray[i]] = tabArray[i+1];
		globalTabDisabled[index++] = tabArray[i+1];
	}
	delete tabArray;

	//mapping
	var v6To52 = {
		calendar		: 'bTeamTabDisable',
		contact			: 'bPersonTabDisable',
		favorite		: 'bPersonTabDisable',
		myInformation	: 'bPersonTabDisable',
		publicMonitor	: 'bPubTabDisable',
		statHome		: 'bStatTabDisable',
		actionLog		: 'bStatTabDisable',
		userControl		: 'bUserTabDisable',
		wcmTools		: 'bStatTabDisable',
		otherconfig		: 'bSysTabDisable4Source',
		systemconfig	: 'bSysTabDisable',
		sechedual		: 'bSysTabDisable',
		questionnaire	: 'bExtTabDisable_Poll',
		commentonLine	: 'bExtTabDisable_Comment',
		adPluin			: 'bExtTabDisable_Addintrs',
		autoInfor		: 'bExtTabDisable_Aptitude',
		"9"				: "bExtTabDisable",
		infoview_list	: 'bExtTabDisable_Infoview',
		scene           : 'bExtTabDisable_scene',
		infogate		: 'bExtTabDisable_Infogate',
		subscribe		: 'bExtTabDisable_Subscribe',
		interview		: 'bExtTabDisable_Interview',
		hotWord			: 'bHotWordDisable',
		searchconfig	:	'bSearchConfigDisable'
	};
//-->
</script>