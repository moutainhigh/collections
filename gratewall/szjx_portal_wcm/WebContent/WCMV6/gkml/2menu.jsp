<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../console/include/error.jsp"%>
<%@ page import="com.trs.cms.process.engine.FlowDocs" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.service.IUserService" %>
<%@ page import="com.trs.components.govinfo.GovInfoViewFinder" %>
<%@page import="com.trs.infra.support.config.ConfigServer"%>
<%@page import="com.trs.components.metadata.center.MetaViewHelper" %>
<%@page import="com.trs.components.metadata.definition.MetaView" %>
<%@page import="com.trs.components.metadata.definition.MetaViews" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	boolean isAdmin = loginUser.isAdministrator();
	IUserService aUserService = (IUserService)DreamFactory.createObjectById("IUserService");
	String sDisableCls = " disabled style='cursor:default;'";
	String sUserMgrExtraAttr = (isAdmin || aUserService.isAdminOfGroup(loginUser)) ? "" : sDisableCls;
	String extraAttr = isAdmin ? "" : sDisableCls;
	long documentObjType = 605;
	long applyFormObjType = 961799218l;
	int nNumOfRecord = getNumOfFlow(loginUser, documentObjType);
	int nNumOfSQGK = getNumOfFlow(loginUser, applyFormObjType);

	//获取政府信息公开视图的Id
	MetaView oMetaView = GovInfoViewFinder.findMetaViewOfGovInfo(loginUser);
	int nMetaViewId = 39;
	if(oMetaView != null){
		nMetaViewId = oMetaView.getId();
	}

	String sPubSiteURL = "http://127.0.0.1:"+request.getServerPort()+"/pub/root6/";
	
	// 实施人员需要将注释去掉，按照以下规则，根据信息公开发布后的站点域名进行修改	
	// sPubSiteURL = "http://gkml.demo.trs.cn/";	
	
	String sGovPublishSiteIds = ConfigServer.getServer().getSysConfigValue("GOV_PUBLISH_SITEIDS", "6");
	//String sGovSiteURL = ConfigServer.getServer().getSysConfigValue("GOV_SITE_URL", "http://gkml.demo.trs.cn/");
	String sGovSiteURL = ConfigServer.getServer().getSysConfigValue("GOV_SITE_URL", sPubSiteURL);
	String sGovMainSiteId = ConfigServer.getServer().getSysConfigValue("GOV_MAIN_SITEID", "6");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<title>TRS政府信息公开服务系统</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="css.css" rel="stylesheet" type="text/css">
<script src="../js/com.trs.util/Common.js"></script>
<script label="TagParser">
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.web2frame.TempEvaler');
	$import('com.trs.wcm.Web2frameDefault');
	$import('com.trs.dialog.Dialog');
</script>
<style type="text/css">
<!--
#apDiv1 {
	position:absolute;
	width:140px;
	visibility: hidden;
}
#apDiv2 {
	position:absolute;
	width:140px;
	z-index:1;
	visibility: hidden;
}
#apDiv3 {
	position:absolute;
	width:140px;
	visibility: hidden;
}
#apDiv4 {
	position:absolute;
	width:126px;
	z-index:1;
	visibility: hidden;
}
-->
</style>
<script>
	var iWidth = window.screen.width - 12;
	var iHeight = window.screen.height - 60;
	var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=no,status=no,titlebar=no,toolbar=no,top=0,left=0,width='+iWidth+',height='+iHeight;

	/**
	*Options: {nodeType:'r/s/c',objId:'2/4/',tabType:'viewinfo/workflow',itemKey:'classinfo/nav_tree/adv_search'}
	*/
	function openWin(oOptions){
		var oParams = Object.extend({ itemKey : 'nav_tree'}, oOptions || {});
		var sUrl = "../../app/main.jsp?" + $toQueryStr(oParams);
		return openMaxWinWithUrl(sUrl);
	}
	function openMaxWinWithUrl(sUrl){
		var sName = "WCM61INDEX_"+location.hostname.replace(/\./ig,'_');
		return openWinWithUrl(sUrl, sFeature, sName);
	}
	function openWinWithUrl(sUrl, sFeature, sName){
		if(!checkValid()) return false;
		sFeature = sFeature || "";
		var w = window.open(sUrl, sName||"", sFeature,true);
		if(w) {
			w.focus();
			return;
		}
		var msg = "窗口可能被拦截工具拦截！\n";
		msg +=    "请先关闭可能的拦截工具，如：Windows IE窗口拦截设置、google bar、\n";
		msg +=    "网易助手等。然后尝试再次操作！\n";
		msg +=    "为此给你带来不便，我们深表歉意！\n"
		alert(msg);
		return false;
	}

	/**
	*nSiteId	设置为政府信息公开站点的Id
	*/
	function publishWebSite(nSiteId){
		if(!checkValid()) return false;
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.call(
			'wcm6_publish', 
			'increasingpublish',
			{objectids:nSiteId,objecttype:103},
			true,
			function(){
				$timeAlert('已经将您的发布操作提交到后台了...', 3, null, null, 2);
			}
		);
		return false;
	}

	/*
	*判断是否可以执行相应的操作
	*/
	function checkValid(){
		var event = Event.findEvent();
		var srcElement = Event.element(event);
		if(srcElement.getAttribute("disabled")){
			return false;
		}
		return true;
	}

	window.onload = function(){
		document.oncontextmenu = function(){return false;};
	};
</script>
</head>
<body>
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="bj">
  <tr>
    <td height="535" align="center" valign="top"><table width="879" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="109" align="right" valign="top" background="images/banner_bj.jpg"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="2" align="right" valign="top"><img src="images/mlyl.jpg" width="94" height="40" border="0" usemap="#Map"></td>
              </tr>
              <tr>
                <td width="241" height="60" align="right" valign="bottom"><span class="q_huang_12hover"><%=loginUser.getName()%>，欢迎您！</span> <span class="q_huang_12hover">[</span><a href="" class="q_huang_12hover" onclick="window.close();">关闭</a><span class="q_huang_12hover">]</span></td>
                <td width="16" align="right" valign="bottom">&nbsp;</td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table width="879" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="44" background="images/dh_bj.jpg">&nbsp;</td>
        </tr>
      </table>
      <table width="879" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="14" background="images/bian_l.gif">&nbsp;</td>
          <td width="851" height="293" align="center" valign="top" background="images/menu_bj2.jpg"><table width="699" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="left" valign="top"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="images/nrgl.jpg" width="163" height="109" class="disableCls"></td>
                    </tr>
                    <tr>
                      <td align="center" valign="top"><table width="128" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="128" height="28" align="left" valign="middle">
							<%
								MetaViews oViews = MetaViewHelper.findViews(loginUser);
								MetaView oView = null;
								if(oViews != null){
									if(oViews.size() == 1){
										oView = (MetaView) oViews.getAt(0);
							%>
							<a href="#" class="hui_12hover_c" onclick="return openMaxWinWithUrl('../../app/application/<%=oView.getId()%>/metaviewdata_addedit.jsp?objectId=0&viewId=<%=oView.getId()%>');">信息采集</a>							
							<%
									}else if(oViews.size() > 1){
							%>
							<script language="javascript">
							<!--
								function showBox(){
									var box = document.getElementById("caijibox");
									box.style.display = '';
									//不兼容火狐、谷歌等
									if(window.event && window.event.cancelBubble) {
										window.event.cancelBubble = true;
									}
								}
								function hideBox(event){
									event = window.event || event;
									var dom = event.srcElement || event.target;
									if(dom.innerHTML != "信息采集"){
										var box = document.getElementById("caijibox");
										box.style.display = 'none';
									}
								}
								document.body.onclick = hideBox;
							//-->
							</script>
							<style>
								.box{
									position:absolute;
									left:360px;
									top:270px;
									width:200px;
									border:1px solid silver;
									background:#ffffef;
								}
							</style>
							<a href="#" class="hui_12hover_c" onclick="showBox();return false;">信息采集</a>
									<div id="caijibox" class="box" style="display:none">
							<%
										for (int i = 0, nSize = oViews.size(); i < nSize; i++) {
											oView = (MetaView) oViews.getAt(i);
											if (oView == null)
												continue;
							%>
										<a href="#" class="hui_12hover_c" onclick="return openMaxWinWithUrl('../../app/application/<%=oView.getId()%>/metaviewdata_addedit.jsp?objectId=0&viewId=<%=oView.getId()%>');"><%=oView.getDesc()%></a>
							<%	
										}
							%>
									</div>	
							<%
									}
								}
							%>
							</td>
                          </tr>
                          <tr>
                            <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" onclick="return openWin({navType : 0,objType:'v',objId:'<%=nMetaViewId%>'});">信息管理</a></td>
                          </tr>
                          <tr>
                            <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" onclick="return openWinWithUrl('../../app/flowdoc/iflowcontent_classic_list.html?ViewType=1&HostType=myFlowDocList&SiteType=100&TabHostType=myFlowDocList&RightValue=&user=<%=loginUser.getName()%>&nick=<%=loginUser.getNickName()%>&objType=<%=documentObjType%>');">信息审核<span style="color:<%=nNumOfRecord>0?"red":"gray"%>;">(<%=nNumOfRecord%>)<span></a></td>
                          </tr>
                          <tr>
                            <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" onclick="return publishWebSite(<%=sGovPublishSiteIds%>);"<%=extraAttr%>>信息发布</a></td>
                          </tr>
                          <tr>
                            <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c"<%=extraAttr%> onclick="return openWinWithUrl('ebook/export_word.jsp');">纸质目录生成</a></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
                <td align="center" valign="top"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="images/ywgl.jpg" width="163" height="109" disabled></td>
                    </tr>
                    <tr>
                      <td align="center" valign="top"><table width="128" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="128" height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" onclick="return openWin({objType:'r',objId:'4',tabType:'metaview'});"<%=extraAttr%>>元数据结构</a></td>
                          </tr>
                          <tr>
                            <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" onclick="return openWin({objType:'r',objId:'4',tabType:'classinfo'});"<%=extraAttr%>>分类体系与机构代码</a></td>
                          </tr>
                          <tr>
                            <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" onclick="return openWin({objType:'r',objId:'4',tabType:'flow'});"<%=extraAttr%>>业务流程定义</a></td>
                          </tr>
                          <tr>
                            <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" onclick="return openWinWithUrl('../../app/flowdoc/iflowcontent_classic_list.html?ViewType=1&HostType=myFlowDocList&SiteType=100&TabHostType=myFlowDocList&RightValue=&user=<%=loginUser.getName()%>&nick=<%=loginUser.getNickName()%>&objType=<%=applyFormObjType%>');">依申请公开审核<span style="color:<%=nNumOfSQGK>0?"red":"gray"%>;">(<%=nNumOfSQGK%>)<span></a></td>
                          </tr>
                          <tr>
                            <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" onclick="return openWinWithUrl('sqgk/applyform_list.jsp');"<%=extraAttr%>>依申请公开管理</a></td>
                          </tr>
                          <tr>
                            <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c"<%=extraAttr%> onclick="return openMaxWinWithUrl('../../console/index/index.jsp?Path=statHome,0');">工作量统计</a></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
                <td align="right" valign="top"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="images/xtgl.jpg" width="163" height="109"></td>
                    </tr>
                    <tr>
                      <td align="center" valign="top"><table width="128" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td width="128" height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" onclick="return openWinWithUrl('../../app/installdeployer/govinfo_deployer_index.jsp');"<%=extraAttr%>>工程初始化创建</a></td>
                        </tr>
                        <tr>
                          <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" onclick="return openMaxWinWithUrl('../../console/index/index.jsp?Path=userControl,0');"<%=sUserMgrExtraAttr%>>用户管理</a></td>
                        </tr>
                        <tr>
                          <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" onclick="return openWin({objType:'s',objId:'<%=sGovMainSiteId %>',tabType:'right'});"<%=extraAttr%>>权限管理</a></td>
                        </tr>
                        <tr style="display:none;">
                          <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" <%=extraAttr%>>数据交换服务配置</a></td>
                        </tr>
                        <tr>
                          <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" onclick="return openMaxWinWithUrl('../../console/index/index.jsp?Path=myInformation,0');">个人设置</a></td>
                        </tr>
                        <tr>
                          <td height="28" align="left" valign="middle"><a href="#" class="hui_12hover_c" onclick="return openMaxWinWithUrl('../../console/index/index.jsp?Path=actionLog,0');"<%=extraAttr%>>系统日志</a></td>
                        </tr>
                      </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
          <td width="14" background="images/bian_r.gif">&nbsp;</td>
        </tr>
      </table>
      <table width="855" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><img src="images/bian_d.jpg" width="855" height="40"></td>
        </tr>
        <tr>
          <td height="31" align="center" valign="bottom"><span class="s_huang_12">版权所有 &copy; 2002 - 2013 北京拓尔思（TRS）信息技术股份有限公司</span></td>
        </tr>
      </table></td>
  </tr>
</table>
<map name="Map">
  <area shape="rect" coords="9,11,87,33" href="<%=sGovSiteURL %>" target="_blank">
</map>
<script language="javascript">
<!--
	if(window.screen.width <= 1024 && document.getElementById("caijibox")){
		document.getElementById("caijibox").style.left = 150;
	}
//-->
</script>
</body>
</html>
<%!
	
	/**
	*获取需要处理审核的记录数
	*/
	private int getNumOfFlow(User _currUser, long _nObjType)throws WCMException{
		StringBuffer sbWhere = new StringBuffer();
		sbWhere.append("ToUserId=? and Worked=0 and WCMFlowDoc.objType=?");
		if(_nObjType == 605){
			sbWhere.append(" and exists(select 1 from WCMChnlDoc a where a.docStatus >= 0 and a.docId = WCMFLOWDOC.objId)");
		}else{
			sbWhere.append(" and exists(select 1 from xwcmapplyform a where a.applyformid = WCMFLOWDOC.objId)");
		}
		WCMFilter filter = new WCMFilter("", sbWhere.toString(), "");
        filter.addSearchValues(_currUser.getId());
        filter.addSearchValues(_nObjType);
        filter.setMaxRowNumber(1000);
        FlowDocs flowDocs = FlowDocs.openWCMObjs(null, filter);
		if(flowDocs != null){
			return flowDocs.size();
		}
		return 0;
	}
%>