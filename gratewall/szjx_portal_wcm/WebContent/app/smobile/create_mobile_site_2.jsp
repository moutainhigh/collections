<%@ page contentType="text/html;charset=utf-8" %>

<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>

<%@ page import="com.trs.infra.I18NMessage" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.persistent.db.DBConnectionConfig" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.infra.util.database.ConnectionPool" %>

<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>

<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>

<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.mobile.MobileCreator"%>





<%@include file="../include/public_server.jsp"%>
<%	
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_UNKNOWN, "您不是管理员，不能执行此操作！");
	}
%>
<%@include file="MyDBMgr.jsp"%>
<%
	String sURL = currRequestHelper.getString("URL"),
	sUser = currRequestHelper.getString("User"), 
	sPassword = currRequestHelper.getString("Password");

	MyDBManager myDBMgr = null;
	StringBuffer sbSiteInfo = new StringBuffer(100*20);
	try {
		myDBMgr = new MyDBManager();
		myDBMgr.connect(sURL,  sUser,  sPassword);

		final String SQL = "select SiteId,SiteName from WCMWebSite order by SiteId desc";
		Connection oConn = null;
		PreparedStatement oPreStmt = null;
		ResultSet result = null;

		try {
			oConn = myDBMgr.getConnection();
			oPreStmt = oConn.prepareStatement(SQL);
			result = oPreStmt.executeQuery();
			while (result.next()) {
				sbSiteInfo.append("<option value="+result.getInt("SiteId")+">"+CMyString.transDisplay(result.getString("SiteName"))+"</option>");
			}
		} catch (Exception ex) {
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,
					"读取数据出现异常!", ex);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (Exception e) {
					logger.error("Failed to close result", e);
				}
			}
			if (oPreStmt != null) {
				try {
					oPreStmt.close();
				} catch (Exception ex) {
					logger.error("Failed to close prepared statement", ex);
				}
			}
			if (oConn != null) {
				myDBMgr.freeConnection(oConn);
			}
		}
	} finally {
		if (myDBMgr != null) {
			myDBMgr.close();
		}
	}
%><!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<title> 创建移动门站点 </title>
	<style type="text/css">
		#progress{
			position:absolute;
			right:10px;
			top:10px;
			width:300px;
			overflow:hidden;
		}
		#progress-loading{
			background:orangered;
			color:white;
		}		
	</style>
	<script src="../../app/js/easyversion/lightbase.js"></script>
	<script src="../js/easyversion/lightbase.js"></script>
	<script src="../js/easyversion/extrender.js"></script>
	<script src="../js/easyversion/elementmore.js"></script>
	<script src="../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../js/source/wcmlib/core/CMSObj.js"></script>
	<!--AJAX-->
	<script src="../js/easyversion/ajax.js"></script>
	<script src="../js/easyversion/basicdatahelper.js"></script>
	<script src="../js/easyversion/web2frameadapter.js"></script>

	<script language="javascript">
	<!--
		(function(){
			var gHandler = null;
			var gCount = 0;

			//开始启动创建移动门户时的消息提示
			function startLoading(){
				$('btnOk').disabled = true;
				Element.show('progress-loading');
				Element.update('progress-current', "开始生成移动门户");
				Element.show('progress-current');
				gHandler = setInterval(function(){
					if(gCount < 100){
						gCount++;
						$('progress-loading').innerHTML = $('progress-loading').innerHTML + ".";
					}else{
						$('progress-loading').innerHTML = "";
					}
				}, 1000);
			}

			//移动门户创建完成时的消息提示
			function endLoading(){
				$('btnOk').disabled = false;
				Element.hide('progress-loading');
				Element.update('progress-current', "<b style='font-size:2em;'>移动门户生成已经完成，并提交发布任务至后台</b>");
				if(gHandler){
					clearInterval(gHandler);
					gHandler = null;
					gCount = 0;
				}
				setTimeout(function(){//fix ie8:先弹出窗口，再画出“移动门户生成完成”
					if(confirm("移动门户创建已完成，点击确定将关闭当前页面，进入ＷＣＭ主页面．")){
						window.close();
					}
				}, 100);
			}

			//与后台服务器通信，检测当前移动门户创建进度
			function detectCurrentProgress(){
				new Ajax.Request(WCMConstants.WCM6_PATH + "smobile/detecting.jsp", {
					onSuccess : function(transport, json){
						var msg = transport.responseText;
						if(msg.trim() == "-1"){//移动门户生成完成
							endLoading();
						}else{
							Element.update('progress-current', msg);
							setTimeout(detectCurrentProgress, 2000);
						}
					}
				});
			}

			//与后台服务器通信，开始创建移动门户
			function createMobileProtal(){
				if(!confirm("确认要生成移动门户？")){
					return;
				}

				startLoading();
				
				var data = {
					URL : $('URL').value,
					User : $('User').value,
					Password : $('Password').value,
					SiteId : $('SiteId').value
				};

				new Ajax.Request(WCMConstants.WCM6_PATH + "smobile/start_create.jsp", {
					method:'post', 
					parameters:$toQueryStr(data), 
					onSuccess : function(transport, json){
						detectCurrentProgress();
					},
					contentType:'application/x-www-form-urlencoded'
				});
			}

			Event.observe(window, 'load', function(){
				Event.observe('btnOk', 'click', function(event){
					Event.stop(event);
					createMobileProtal();
				});
			});
			
		})();		
	//-->
	</script>
</head>

<body>
<div id="progress">
	<div id="progress-loading" style="display:none;"></div>
	<div id="progress-current" style="display:none;"></div>
</div>

<h1>2.创建移动站点</h1>

<input type="hidden" id="URL" name="URL" value="<%=sURL%>">
<input type="hidden" id="User" name="User" value="<%=sUser%>">
<input type="hidden" id="Password" name="Password" value="<%=sPassword%>">


选择站点：产生站点<select name="SiteId" id="SiteId">
	<%=sbSiteInfo%>
</select>
的移动门户<BR/><BR/>

<%
	MobileCreator creator = MobileCreator.getInstance();
	if(creator.isProcessing()){
%>
<b style="color:red;">当前有人正在创建移动站点，请稍后再进行尝试</b>
<%
	}else{
%>
<input type="submit" value="开始创建" style="padding:2px 40px;margin-left:20px;" id="btnOk"> 
<%
	}
%>
</body>
</html>